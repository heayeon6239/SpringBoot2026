package com.green.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

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
		if(result) {
			System.out.println("회원가입 성공");
			return "redirect:/";
		}else {
			System.out.println("회원가입 실패");
			return "member/signup";
		}
	}
	
	// 로그인 화면 출력 컨트롤러
	@GetMapping("/member/login")
	public String loginForm() {
		System.out.println("MemberController loginForm()");
		return "/member/login";
	}
	
	// 로그인 메서드
	@PostMapping("/member/loginPro")
	public String loginPro(MemberDTO mdto, HttpSession session) {
		System.out.println("MemberController loginPro()");
		MemberDTO result = memberservice.login(mdto);
		
		// 로그인 성공
		if(result != null) {
			session.setAttribute("user", result); // 세션에 해당 로그인 정보 저장
			return "redirect:/";
		}
		// 로그인 실패
		else {
			return "/member/login";
		}
	}
	
	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		System.out.println("MemberController logout()");
		session.invalidate(); // 세션에서 삭제
		return "redirect:/";
	}
	
	
}
