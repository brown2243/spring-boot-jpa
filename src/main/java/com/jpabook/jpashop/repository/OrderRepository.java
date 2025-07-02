package com.jpabook.jpashop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.QMember;
import com.jpabook.jpashop.domain.QOrder;
import com.jpabook.jpashop.dto.SimpleOrderDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  // 일관성 있는 사용
  private final EntityManager em;
  private final JPAQueryFactory queryFactory;

  public Long save(Order order) {
    em.persist(order);
    return order.getId();
  }

  public Order find(Long id) {
    return em.find(Order.class, id);
  }

  public List<Order> findAllByCriteria(OrderSearch orderSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Order> cq = cb.createQuery(Order.class);
    Root<Order> o = cq.from(Order.class);
    Join<Order, Member> m = o.join("member", JoinType.INNER); // 회원과 조인
    List<Predicate> criteria = new ArrayList<>();
    // 주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      Predicate status = cb.equal(o.get("status"),
          orderSearch.getOrderStatus());
      criteria.add(status);
    }
    // 회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      Predicate name = cb.like(m.<String>get("name"), "%" +
          orderSearch.getMemberName() + "%");
      criteria.add(name);
    }
    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
    // 최대 1000건
    TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
    return query.getResultList();
  }

  public List<Order> findAllByQueryDsl(OrderSearch orderSearch) {
    QOrder order = QOrder.order;
    QMember member = QMember.member;

    List<Order> orders = queryFactory.select(order)
        .from(order)
        .join(order.member, member)
        .where(statusEq(orderSearch.getOrderStatus()), member.name.like(orderSearch.getMemberName()))
        .limit(1000)
        .fetch();

    return orders;
  }

  public List<Order> findAllWithMemberDelivery() {
    return em.createQuery("""
          select o from Order o
          join fetch o.member m
          join fetch o.delivery d
        """, Order.class).getResultList();
  }

  public List<Order> findAllWithMemberDelivery(int offset, int limit) {
    return em.createQuery(
        "select o from Order o" +
            " join fetch o.member m" +
            " join fetch o.delivery d",
        Order.class)
        .setFirstResult(offset)
        .setMaxResults(limit)
        .getResultList();
  }

  public List<Order> findAllWithItem() {
    return em.createQuery("""
          select DISTINCT o from Order o
          join fetch o.member m
          join fetch o.delivery d
          join fetch o.orderItems oi
          join fetch oi.item i
        """, Order.class).getResultList();
  }

  public List<SimpleOrderDto> findAllByDto() {
    return em.createQuery("""
          select new com.jpabook.jpashop.dto.SimpleOrderDto(o.id, m.name, o.orderDate,o.status, d.address)
          from Order o
          join o.member m
          join o.delivery d
        """, SimpleOrderDto.class).getResultList();
  }

  private BooleanExpression statusEq(OrderStatus status) {
    if (status == null) {
      return null;
    }
    return QOrder.order.status.eq(status);
  }
  // public List<Order> findAll(OrderSearch orderSearch) {
  // return em.createQuery(
  // """
  // select o from Order o join o.member m
  // where o.status = :status
  // and m.name like :name
  // """,
  // Order.class)
  // .setParameter("status", orderSearch.getOrderStatus())
  // .setParameter("name", orderSearch.getMemberName())
  // .setMaxResults(1000)
  // .getResultList();
  // }

}