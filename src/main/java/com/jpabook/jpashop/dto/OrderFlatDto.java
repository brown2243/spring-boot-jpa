
package com.jpabook.jpashop.dto;

import java.time.LocalDateTime;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderFlatDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private Address address;
  private OrderStatus orderStatus;
  private String itemName;
  private int orderPrice;
  private int count;
}