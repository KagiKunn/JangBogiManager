package com.kkh.jangbogimanager.member;

import com.kkh.jangbogimanager.member.Service.MemberService;
import com.kkh.jangbogimanager.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/login")
	public String login() {
		return "/member/login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "/member/signup";
	}

	@PostMapping("/signup")
	public String signup(MemberDto memberDto) {
		memberService.signup(memberDto);
		return "/member/login";
	}
}
