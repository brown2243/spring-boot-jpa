package com.jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class OrderServiceTest {
	@Autowired
	private EntityManager em;
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void 상품주문() {
		Member member = getMember();
		em.persist(member);

		int price = 12400;
		int stockQuantity = 10;

		Item item = getItem(price, stockQuantity);
		em.persist(item);

		int orderCount = 5;

		// when
		Long id = orderService.order(member.getId(), item.getId(), orderCount);

		// then
		Order order = orderRepository.find(id);
		assertEquals(OrderStatus.ORDER, order.getStatus(), "주문 시 상태 확인");
		assertEquals(1, order.getOrderItems().size(), "주문 상품 종류 확인");
		assertEquals(price * orderCount, order.getTotalPrice(), "주문 금액 확인");
		assertEquals(stockQuantity, item.getStockQuantity() + orderCount, "주문 수량 만큼 재고 감소 확인");
	}

	@Test
	public void 상품주문_재고수량초과() {
		Member member = getMember();
		em.persist(member);

		int price = 12400;
		int stockQuantity = 10;

		Item item = getItem(price, stockQuantity);
		em.persist(item);

		int orderCount = 15;

		assertThrows(NotEnoughStockException.class,
				() -> orderService.order(member.getId(), item.getId(), orderCount), "재고 수량이 부족합니다.");
	}

	@Test
	public void 주문취소() {
		Member member = getMember();
		em.persist(member);

		int price = 12400;
		int stockQuantity = 10;

		Item item = getItem(price, stockQuantity);
		em.persist(item);

		int orderCount = 5;
		Long id = orderService.order(member.getId(), item.getId(), orderCount);
		// when
		orderService.cancelOrder(id);

		// then
		Order order = orderRepository.find(id);
		assertEquals(OrderStatus.CANCEL, order.getStatus(), "취소 시 상태 확인");
		assertEquals(stockQuantity, item.getStockQuantity(), "취소 후 재고 확인");

	}

	private Member getMember() {
		Member member = new Member();
		member.setName("구매자");
		member.setAddress(new Address("test", "test", "test"));
		return member;
	}

	private Item getItem(int price, int stockQuantity) {
		Item item = new Book();
		item.setName("JPA");
		item.setPrice(price);
		item.setStockQuantity(stockQuantity);
		return item;
	}

}
