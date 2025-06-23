package com.jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.service.ItemService;
import com.jpabook.jpashop.service.MemberService;
import com.jpabook.jpashop.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final ItemService itemService;
  private final MemberService memberService;

  @GetMapping("/order")
  public String createForm(Model model) {
    model.addAttribute("members", memberService.findMembers());
    model.addAttribute("items", itemService.findItems());
    return "orders/orderForm";
  }

  @PostMapping("/order")
  public String order(
      @RequestParam("memberId") Long memberId,
      @RequestParam("itemId") Long itemId,
      @RequestParam("count") int count) {

    orderService.order(memberId, itemId, count);
    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String orderList(
      @ModelAttribute("orderSearch") OrderSearch orderSearch,
      Model model) {
    model.addAttribute("orders", orderService.findOrders(orderSearch));
    return "orders/orderList";
  }

  @PostMapping("/orders/{orderId}/cancel")
  public String cancelOrder(@PathVariable("orderId") Long orderId) {
    orderService.cancelOrder(orderId);
    return "redirect:/orders";
  }
}
