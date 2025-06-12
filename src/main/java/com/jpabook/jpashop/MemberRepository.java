package com.jpabook.jpashop;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MemberRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public Long save(Member member) {
    em.persist(member);
    return member.getId();
  }

  @Transactional(readOnly = true)
  public Member find(Long id) {
    return em.find(Member.class, id);
  }
}
