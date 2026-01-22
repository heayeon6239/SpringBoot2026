package com.green;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class MemberController {
    
    // DI(의존성 객체 주입) ★★★★★
	// @Autowired -> MemberController가 직접 MemberService를 생성하지 않고,
	//               스프링 IOC 컨테이너가 만든 MemberService를 주입시켜라
	@Autowired
    MemberService memberService;
	
	// 회원가입 양식
	@GetMapping("/member/signup") 
	public String signUpForm() {
		System.out.println("signUpForm()");
		return "signUpForm"; 
	}
	
	// 로그인 양식                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
	@GetMapping("/member/signin")
	public String signInForm() {
		System.out.println("signInForm");
		return "signinform"; 
	}
	
	// 숨겨서 가는 @PostMapping() 사용
	@PostMapping("/member/signUp_confirm")
	public String signupconfrim(MemberDTO mdto, Model model) {
		System.out.println("signupconfirm");
		
		// MemberService 비즈니스 로직을 담당하는 클래스
		// new 키워드 이용하여 객체 생성
//		MemberService memberservice = new MemberService();
		memberService.signUpConfirm(mdto); // 먼저 메서드 선언하면 자동으로 만들어짐
		
		// 현재 가입한 시간을 출력하는 로직 작성
		Date now = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		// model로 담기
		model.addAttribute("now", s.format(now));
		model.addAttribute("new_id", mdto.getId());
		model.addAttribute("new_pw", mdto.getPw());
		model.addAttribute("new_email", mdto.getEmail());
		
		
		
		return "signUpResult";
	}

	// MemberDTO 를 데이터타입으로 매개변수 지정
	@PostMapping("member/signIn_confirm")
	
	public ModelAndView signinconfirm(MemberDTO mdto) {
		System.out.println("signinconfirm");
		
//		MemberService memberservice = new MemberService();
		memberService.signInConfirm(mdto);
		
		ModelAndView modelView = new ModelAndView();
		
		modelView.addObject("id", mdto.getId());
		modelView.addObject("pw", mdto.getPw());
		
		modelView.setViewName("signInResult");

		
		return modelView;
	}
}

