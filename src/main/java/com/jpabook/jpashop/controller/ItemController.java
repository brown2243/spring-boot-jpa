package com.jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.dto.BookForm;
import com.jpabook.jpashop.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(@Valid BookForm form, BindingResult result) {
    if (result.hasErrors()) {
      return "items/createItemForm";
    }

    Book item = new Book();
    item.setName(form.getName());
    item.setPrice(form.getPrice());
    item.setStockQuantity(form.getStockQuantity());
    item.setAuthor(form.getAuthor());
    item.setIsbn(form.getIsbn());

    itemService.saveItem(item);
    return "redirect:/";
  }

  @GetMapping("/items")
  public String list(Model model) {
    model.addAttribute("items", itemService.findItems());
    return "items/itemList";
  }

}
