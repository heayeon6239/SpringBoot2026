package com.green.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.book.dao.BookDAO;
import com.green.book.dto.BookDTO;
import com.green.book.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	BookService bService;
	@Autowired
	BookDAO bdao;
	
	// 01. 도서 입력 페이지
	@GetMapping("/book/bookAdd") // /book/bookAdd 이 주소랑 연결
	public String bookadd(BookDTO bdto) { 
		System.out.println("bookadd()");
		if(bdto != null) { // 들어온 값이 null이 아니면
			bService.addBook(bdto); // bdto를 데이터 타입으로 한 값을 DAO에 있는 "전체 도서 HashMap<>"에 저장 (DB 대용)
		}
		return "BookAdd"; // 화면 뿌려질 해당 페이지의 html
	}
	
	// 02. 도서 대여 페이지
	@GetMapping("/book/bookRent") // /book/bookRent 이 주소랑 연결
	public String bookrent(BookDTO bdto) {
		System.out.println("bookrent()");
		bService.canRent(bdto); // bdto를 데이터 타입으로 한 값을 DAO에 있는 "대여 도서 HashMap<>"에 저장 (DB 대용)

		return "bookRent"; // 화면 뿌려질 해당 페이지의 html
	}
	
	// 03. 대여 리스트 페이지
	@GetMapping("/book/bookList") // /book/bookList 이 주소랑 연결
	public String booklist(Model model) {
		System.out.println("booklist()");
		
//		bService.rentList(bdto);	
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("rentList", bdao.rentBookDB);
//		mav.setViewName("bookList"); 원래는 ModelAndView 로 화면(html)과 rentList(대여 리스트)를 return 하려 했음
		
		model.addAttribute("rentList", bdao.rentBookDB); // model로 DAO의 rentBookDB라는 대여 도서 HashMap<>을 rentList(변수)에 담아서 공유

		return "bookList"; // 화면 뿌려질 해당 페이지의 html
	}
	
}
