package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
	
	// MemberService 클래스를 DI로 의존객체화 해야함 
	@Autowired
	MemberService memberservice;
	
	// 회원가입 양식 폼
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("MemberController signup()");
		String nextPage = "member/signup_form";
		return nextPage;
	}
	
	// 회원가입 확인
	@PostMapping("/member/signup_confirm")
	public String signipConfirm(MemberDTO mdto, Model model) {
		System.out.println("MemberController signipConfirm() -> member.js에서 다 입력했는지 확인하고 문제없으면 submit(전송)함");
		String nextPage = "/member/signup_result";
		// 회원가입이 제대로 되었는지, 혹은 실패했는지 예외처리
		int result = memberservice.signupConfirm(mdto); // service에서 확인하는 메서드로 감(여기서 만들면 service에 자동 create됨)
		System.out.println("result의 결과"+result);
		// 회원가입이 성공하였을 경우 => 회원 목록으로 redirect(새로운 주소로 곧바로 이동) !!!!!
		System.out.println("성공"+memberservice.user_id_success);
		if(result == memberservice.user_id_success) {
			System.out.println("성공2"+memberservice.user_id_success);
			return "redirect:/member/list";
		}
		// 회원가입이 실패한 경우
		else {
			model.addAttribute("result", result); // service에서 확인하고 가져온 값을 result라는 변수에 넣어서 model에 담음
			return nextPage;
		}
	}
	
	// 회원 전체 목록화면 호출
	@GetMapping("/member/list")
	public String memberList(Model model) {
		// MemberService의 allListMember()
		List<MemberDTO> memberList = memberservice.allListMember(); // list로 들고온 값을 list에 다시 담음
		model.addAttribute("list",memberList);
		
		String nextPage = "member/memberList";
		return nextPage;
	}
}
