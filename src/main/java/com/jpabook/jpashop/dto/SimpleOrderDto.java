package com.jpabook.jpashop.dto;

import java.time.LocalDateTime;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleOrderDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus status;
  private Address address;

  public SimpleOrderDto(Order order) {
    this.orderId = order.getId();
    this.name = order.getMember().getName();
    this.orderDate = order.getOrderDate();
    this.status = order.getStatus();
    this.address = order.getDelivery().getAddress();
  }
}
