package com.jpabook.jpashop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

@SpringBootTest
@Transactional
// @RunWith(SpringRunner.class) junit 4
// @ExtendWith(SpringExtension.class) 위와 동일 - 불필요
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Test
	// @Rollback(false)
	public void save() {
		Member member = new Member();
		Long id = memberRepository.save(member);
		Member savedMember = memberRepository.find(id);

		assertEquals(id, savedMember.getId());
	}
}
