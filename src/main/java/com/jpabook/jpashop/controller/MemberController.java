package com.jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.dto.MemberForm;
import com.jpabook.jpashop.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";

  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm form, BindingResult result) {
    if (result.hasErrors()) {
      return "members/createMemberForm";
    }

    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(new Address(form.getCity(), form.getStreet(), form.getZipcode()));
    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    model.addAttribute("members", memberService.findMembers());
    return "members/memberList";
  }

}
