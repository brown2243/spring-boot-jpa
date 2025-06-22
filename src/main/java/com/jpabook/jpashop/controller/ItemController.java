package com.jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.dto.BookForm;
import com.jpabook.jpashop.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

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

  @GetMapping("/items/{itemId}/edit")
  public String updateForm(@PathVariable Long itemId, Model model) {

    Book item = (Book) itemService.findOne(itemId);
    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());
    model.addAttribute("form", form);

    return "items/updateItemForm";
  }

  // @PostMapping("/items/{itemId}/edit")
  // public String updateItem(
  // @PathVariable Long itemId,
  // @ModelAttribute("form") BookForm form,
  // BindingResult result) {
  // // form -> book

  // Book item = new Book();
  // item.setId(form.getId());
  // item.setName(form.getName());
  // item.setPrice(form.getPrice());
  // item.setStockQuantity(form.getStockQuantity());
  // item.setAuthor(form.getAuthor());
  // item.setIsbn(form.getIsbn());

  // itemService.saveItem(item);
  // return "redirect:/";
  // }

  /**
   * 상품 수정, 권장 코드
   */
  @PostMapping(value = "/items/{itemId}/edit")
  public String updateItem(
      @PathVariable Long itemId,
      @ModelAttribute("form") BookForm form) {

    itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
    return "redirect:/items";
  }
}
