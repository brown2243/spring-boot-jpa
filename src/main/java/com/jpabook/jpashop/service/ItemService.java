package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  // 회원 가입
  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);

  }

  // 회원 전체 조회
  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  public Item findOne(Long id) {
    return itemRepository.find(id);
  }

  @Transactional
  public void updateItem(Long id, String name, int price, int stockQuantity) {
    Item item = itemRepository.find(id);
    item.setName(name);
    item.setPrice(price);
    item.setStockQuantity(stockQuantity);
  }
}
