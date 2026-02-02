package com.green.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberservice;
	
	// 회원가입 화면 출력 컨트롤러
	@GetMapping("/member/signup")
	public String signupForm() {
		System.out.println("MemberController signupForm()");
		return "/member/signup";
	}
	
	// 회원가입 메서드
	@PostMapping("/member/signupPro")
	public String signupPro(MemberDTO mdto) {
		System.out.println("MemberController signupForm()");
		boolean result = memberservice.signup(mdto);
	}
	
	
}
