package com.jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MemberServiceTest {
	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	EntityManager em;

	@Test
	// @Rollback(false)
	public void 회원가입() {
		// given
		Member member = new Member();
		member.setName("TEST NAME!!!");

		// when
		Long id = memberService.join(member);
		em.flush();
		// then
		assertEquals(member, memberService.findOne(id));

		// Long id = memberService.join(member);
		// Member mem = memberService.findOne(id);

		// assertEquals(id, mem.getId());
	}

	@Test
	public void 중복_회원_예외() {
		// given
		Member member1 = new Member();
		member1.setName("KIM");

		Member member2 = new Member();
		member2.setName("KIM");

		memberService.join(member1);
		// when
		// then
		assertThrows(IllegalStateException.class, () -> memberService.join(member2));

		// when
		// try {
		// memberService.join(member2);
		// } catch (IllegalStateException e) {
		// return;
		// }

		// // then
		// fail("예외 발생 해야 함");

	}
}
