package com.jpabook.jpashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpabook.jpashop.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByName(String name);

}
