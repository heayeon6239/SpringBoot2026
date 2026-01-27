package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		if(result == memberservice.user_id_success) {
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
	
	// ----------------------- 2026-01-27 서비스로직 작성 부분 ---------------------------
	
	// 한 개인의 정보를 상세보기하는 핸들러
	@GetMapping("/member/memberInfo")
	public String memberInfo(Model model, @RequestParam("id") String id) { // MemberDTO 형식으로 id만 담아서 들고옴
		System.out.println("MemberController memberInfo()"+id);
		MemberDTO onememberInfo = memberservice.oneSelect(id); // public MemberDTO oneSelect(String id) -> String id = mdto.getId()
		// onememberInfo에 service에서의 oneSelect() 호출해서 return한 값을 담음
		
		model.addAttribute("onelist",onememberInfo); // model을 통해서 값을 html에 공유(Controller에서 값을 html에 공유해야할땐 model을 사용)
		String nextPage = "member/memberInfo";
		return nextPage;
	}
	
	// 한 개인의 정보를 수정하는 화면으로 이동하는 핸들러
	@GetMapping("/member/modify")
	public String modifyForm(MemberDTO mdto, Model model) {
		System.out.println("MemberController modifyForm()");
		MemberDTO oneModify = memberservice.oneSelect(mdto.getId()); // DB에서 해당 id의 정보(pw,mail,phone...) 값을 받아와 저장
		model.addAttribute("member", oneModify); // 받아온 값으로 해당 id의 정보를 수정할 수 있도록 model로 공유
		String nextPage = "member/member_modify";
		return nextPage;
	}
	
//	@GetMapping("/member/modify")
//	public String modifyForm(MemberDTO mdto, Model model) {
//		System.out.println("MemberController modifyForm()");
//		Boolean change = memberservice.modifyMember(mdto);
//		if(change == true) {
//			
//		}else {
//			
//		}
//		String nextPage = "member/memberInfo";
//		return nextPage;
//		
//	}
	
	// 한 개인의 정보가 수정을 처리하는 핸들러(비밀번호 일치하는지 확인, redirect사용)
	@PostMapping("/member/modify")
	public String modifySubmit(MemberDTO mdto, RedirectAttributes re) {
		System.out.println("MemberController modifySubmit()");
		Boolean result = memberservice.modifyMember(mdto);
		System.out.println("boolean="+result);
		// true(수정완료)
		if(result) {
			// redirect는 Model사용 X / 대신 사용하는 RedirectAttributes는 단, 한번만 데이터를 넘길 수 있음 ★★★★★
			re.addFlashAttribute("msg", "회원정보가 수정되었습니다.");
			// 수정이 완료되면 list로 곧바로 이동
			return "redirect:/member/list";
			
		}
		// false(비밀번호 틀림)
		else {
			re.addFlashAttribute("msg", "비밀번호가 틀렸습니다.");
			// http://localhost:8090/member/modify?id=kkk
			// 비밀번호가 틀릴 경우 위 주소로 돌아가야함
			return "redirect:/member/modify?id="+mdto.getId();
		}
	}
	
	// 개인 1명의 정보를 삭제하는 핸들러
	@GetMapping("/member/delete")
	public String deleteMember(@RequestParam("id") String id, RedirectAttributes re) {
		System.out.println("MemberController deleteMember()");
		boolean result = memberservice.oneDelete(id);
		// true(삭제 O)
		if(result) {
			// 입력된 id가 존재해서 삭제된 경우
			re.addFlashAttribute("msg", "회원이 삭제되었습니다.");
			// 삭제된 경우 List url => /member/list
			return "redirect:/member/list";
		}
		// false(삭제 X)
		else {
			re.addFlashAttribute("msg", "삭제 실패");
			return "redirect:/member/memberInfo?id="+id;
		}
	}
}
