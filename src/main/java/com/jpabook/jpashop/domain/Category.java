package com.jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import com.jpabook.jpashop.domain.item.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(
      //
      name = "category_item", //
      joinColumns = @JoinColumn(name = "category_id"), //
      inverseJoinColumns = @JoinColumn(name = "item_id") //
  )
  private List<Item> items = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> children;

  // 연관관계 편의 메서드
  public void addCategory(Category child) {
    this.children.add(child);
    child.setParent(this);
  }
}
