package com.green;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuizController {
	
	// 정답 확인 중간 페이지(UI는 존재하지 않음)
	@PostMapping("/check-quiz")
	public String checkPage(
		@RequestParam("pass") String pass,
		RedirectAttributes r
			) {
		if(pass.equals("1234")) {
			return "redirect:/main"; 
		}else{
			r.addFlashAttribute("msg", "다시 입력하세요."); // 한번밖에 못씀
			return "redirect:/quiz"; // 바로 이동할 주소
		}
	}
	
	@GetMapping("/main")
	public String mainPage() {
		return "main-view";
	}
	
	
	@GetMapping("/quiz")
	public String quizPage() {
		return "quiz-view";
	}
}
