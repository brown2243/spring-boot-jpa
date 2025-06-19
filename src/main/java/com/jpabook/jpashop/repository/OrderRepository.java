package com.jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  // 일관성 있는 사용
  private final EntityManager em;

  public Long save(Order order) {
    em.persist(order);
    return order.getId();
  }

  public Order find(Long id) {
    return em.find(Order.class, id);
  }

  // public List<Order> findAll() {
  // return em.createQuery("select m from Order m", Order.class).getResultList();
  // }

}
