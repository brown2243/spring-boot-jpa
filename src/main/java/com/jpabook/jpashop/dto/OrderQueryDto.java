package com.jpabook.jpashop.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderQueryDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus status;
  private Address address;
  private List<OrderItemQueryDto> orderItems = new ArrayList<>();

  public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.status = status;
    this.address = address;
  }

  // public OrderQueryDto(Order order) {
  // this.orderId = order.getId();
  // this.name = order.getMember().getName();
  // this.orderDate = order.getOrderDate();
  // this.status = order.getStatus();
  // this.address = order.getDelivery().getAddress();
  // }
}
