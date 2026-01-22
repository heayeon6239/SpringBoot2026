package com.green.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.SamplePjt03Application;

@Controller // 컨트롤러 어노테이션
public class BookController {
	
	// 대여 페이지
	@GetMapping("/book/rental")
	
	public String rental() {
		System.out.println("rental()"); // 오류 확인용
		return "Book";
		
	}
	
	// 대여 확인 페이지
	@GetMapping("/book/rentalProc")
	
	public ModelAndView rentalProc(
			@RequestParam(value="bookName") ArrayList<String> bookName,
			@RequestParam(value="author") ArrayList<String> author,
			@RequestParam(value="ISBN") ArrayList<String> ISBN,
			@RequestParam(value="renterName") ArrayList<String> renterName
			) {
		
		System.out.println("rentalProc()"); // 오류 확인용
		
		ModelAndView modelview = new ModelAndView();
		
		modelview.addObject("bookName", bookName);
		modelview.addObject("author", author);
		modelview.addObject("ISBN", ISBN);
		modelview.addObject("renterName", renterName);
		modelview.setViewName("BookResult"); // .html 은 자동으로 붙음
		
//		model.addAtrribute("names",List.of("홍길동","개나리"))
		
		return modelview;
	}
}
