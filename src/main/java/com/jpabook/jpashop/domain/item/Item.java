package com.jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import com.jpabook.jpashop.exception.NotEnoughStockException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  private List<Category> categories = new ArrayList<>();

  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  public void removeStock(int quantity) {
    int rest = this.stockQuantity - quantity;
    if (rest < 0) {
      throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = rest;
  }

}
