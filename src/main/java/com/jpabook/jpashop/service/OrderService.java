package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepositoryOld;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepositoryOld memberRepository;
  private final ItemRepository itemRepository;

  // 주문
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    Member member = memberRepository.find(memberId);
    Item item = itemRepository.find(itemId);

    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    Order order = Order.createOrder(member, delivery, orderItem);

    return orderRepository.save(order);
  }

  // 취소
  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.find(orderId);
    order.cancel();
  }

  // 검색
  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAllByCriteria(orderSearch);
  }

}
