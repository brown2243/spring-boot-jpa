package com.jpabook.jpashop.repository;

import java.util.List;

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

  @Transactional(readOnly = true)
  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  @Transactional(readOnly = true)
  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where name = :name", Member.class).setParameter("name", name)
        .getResultList();
  }
}
