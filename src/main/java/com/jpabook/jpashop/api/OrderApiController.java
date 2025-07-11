package com.jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.dto.OrderQueryDto;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.repository.query.OrderQueryRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
  private final OrderRepository orderRepository;
  private final OrderQueryRepository orderQueryRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
    for (Order order : orders) {
      order.getMember().getName();
      order.getDelivery().getAddress();
      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.stream().forEach(item -> item.getItem().getName());
    }

    return orders;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2() {
    return orderRepository.findAllByCriteria(new OrderSearch())
        .stream()
        .map(o -> new OrderDto(o))
        .toList();
  }

  @GetMapping("/api/v3/orders")
  public List<OrderDto> ordersV3() {
    return orderRepository.findAllWithItem()
        .stream()
        .map(o -> new OrderDto(o))
        .toList();
  }

  @GetMapping("/api/v3.1/orders")
  public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,
        limit);
    List<OrderDto> result = orders.stream()
        .map(o -> new OrderDto(o))
        .toList();
    return result;
  }

  @GetMapping("/api/v4/orders")
  public List<OrderQueryDto> ordersV4() {
    return orderQueryRepository.findOrderQueryDto();
  }

  @GetMapping("/api/v5/orders")
  public List<OrderQueryDto> ordersV5() {
    return orderQueryRepository.findAllByDto();
  }

  @GetMapping("/api/v6/orders")
  public List<OrderQueryDto> ordersV6() {
    return orderQueryRepository.findAllByDtoFlat();
  }

  @Data
  static class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();

      orderStatus = order.getStatus();
      address = order.getDelivery().getAddress();
      orderItems = order.getOrderItems().stream()
          .map(orderItem -> new OrderItemDto(orderItem))
          .collect(Collectors.toList());
    }
  }

  @Data
  static class OrderItemDto {
    private String itemName;// 상품 명
    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    public OrderItemDto(OrderItem orderItem) {
      itemName = orderItem.getItem().getName();
      orderPrice = orderItem.getOrderPrice();
      count = orderItem.getCount();
    }
  }
}
