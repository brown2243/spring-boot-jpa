package com.jpabook.jpashop.repository.query;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.dto.OrderFlatDto;
import com.jpabook.jpashop.dto.OrderItemQueryDto;
import com.jpabook.jpashop.dto.OrderQueryDto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;

  public List<OrderQueryDto> findAllByDto() {

    List<OrderQueryDto> orders = findOrders();
    List<Long> orderIds = orders.stream()
        .map(OrderQueryDto::getOrderId)
        .collect(toList());

    List<OrderItemQueryDto> result = em.createQuery(
        "select new com.jpabook.jpashop.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi" +
            " join oi.item i" +
            " where oi.order.id in :orderIds",
        OrderItemQueryDto.class)
        .setParameter("orderIds", orderIds)
        .getResultList();

    Map<Long, List<OrderItemQueryDto>> orderItemMap = result.stream().collect(
        groupingBy(oi -> oi.getOrderId()));

    orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
    return orders;
  }

  public List<OrderQueryDto> findOrderQueryDto() {
    List<OrderQueryDto> orders = findOrders();
    orders.forEach(o -> {
      o.setOrderItems(findOrderItems(o.getOrderId()));
    });

    return orders;
  }

  public List<OrderQueryDto> findAllByDtoFlat() {
    List<OrderFlatDto> orders = em.createQuery(
        """
            select new com.jpabook.jpashop.dto.OrderFlatDto(o.id, m.name, o.orderDate,d.address, o.status, i.name, oi.orderPrice, oi.count)
            from Order o
            join o.member m
            join o.delivery d
            join o.orderItems oi
            join oi.item i
            """,
        OrderFlatDto.class).getResultList();

    return orders.stream()
        .collect(
            groupingBy(
                o -> new OrderQueryDto(
                    o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                    o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
            //
            ))
        .entrySet()
        .stream()
        .map(
            e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(),
                e.getKey().getOrderDate(), e.getKey().getStatus(),
                e.getKey().getAddress(), e.getValue()))
        .collect(toList());

  }

  private List<OrderItemQueryDto> findOrderItems(Long orderId) {
    return em.createQuery(
        "select new com.jpabook.jpashop.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi" +
            " join oi.item i" +
            " where oi.order.id = :orderId",
        OrderItemQueryDto.class)
        .setParameter("orderId", orderId)
        .getResultList();
  }

  private List<OrderQueryDto> findOrders() {
    return em.createQuery(
        "select new com.jpabook.jpashop.dto.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o"
            +
            " join o.member m" +
            " join o.delivery d",
        OrderQueryDto.class)
        .getResultList();
  }

}