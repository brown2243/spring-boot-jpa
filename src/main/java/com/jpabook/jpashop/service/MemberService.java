package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  // 회원 가입
  @Transactional
  public Long join(Member member) {
    validateDuplicatedMember(member);
    return memberRepository.save(member).getId();
  }

  // 회원 전체 조회
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Member findOne(Long id) {
    return memberRepository.findById(id).orElseThrow();
  }

  private void validateDuplicatedMember(Member member) {
    List<Member> members = memberRepository.findByName(member.getName());
    if (!members.isEmpty()) {
      throw new IllegalStateException("존재하는 회원 입니다.");
    }
  }

  @Transactional
  public Long update(Long id, String name) {
    memberRepository.findById(id).orElseThrow().setName(name);
    return id;
  }

}
