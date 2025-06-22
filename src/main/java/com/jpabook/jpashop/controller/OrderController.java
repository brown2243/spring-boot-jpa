package com.jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.dto.BookForm;
import com.jpabook.jpashop.service.ItemService;
import com.jpabook.jpashop.service.MemberService;
import com.jpabook.jpashop.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

  // Book order = new Book();
  // order.setName(form.getName());
  // order.setPrice(form.getPrice());
  // order.setStockQuantity(form.getStockQuantity());
  // order.setAuthor(form.getAuthor());
  // order.setIsbn(form.getIsbn());

  // orderService.saveOrder(order);
  // return "redirect:/";
  // }

  // @GetMapping("/orders")
  // public String list(Model model) {
  // model.addAttribute("orders", orderService.findOrders());
  // return "orders/orderList";
  // }

  // @GetMapping("/orders/{orderId}/edit")
  // public String updateForm(@PathVariable Long orderId, Model model) {

  // Book order = (Book) orderService.findOne(orderId);
  // BookForm form = new BookForm();
  // form.setId(order.getId());
  // form.setName(order.getName());
  // form.setPrice(order.getPrice());
  // form.setStockQuantity(order.getStockQuantity());
  // form.setAuthor(order.getAuthor());
  // form.setIsbn(order.getIsbn());
  // model.addAttribute("form", form);

  // return "orders/updateOrderForm";
  // }

  // @PostMapping(value = "/orders/{orderId}/edit")
  // public String updateOrder(
  // @PathVariable Long orderId,
  // @ModelAttribute("form") BookForm form) {

  // orderService.updateOrder(orderId, form.getName(), form.getPrice(),
  // form.getStockQuantity());
  // return "redirect:/orders";
  // }
}
