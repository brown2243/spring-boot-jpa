
package com.jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemQueryDto {
  private Long orderId;
  private String itemName;
  private int orderPrice;
  private int count;

}