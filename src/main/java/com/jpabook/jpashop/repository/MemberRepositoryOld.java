package com.jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

  // 일관성 있는 사용
  private final EntityManager em;

  public Long save(Member member) {
    em.persist(member);
    return member.getId();
  }

  public Member find(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where name = :name", Member.class).setParameter("name", name)
        .getResultList();
  }
}
