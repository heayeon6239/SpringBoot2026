package com.green.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.book.dto.BookDTO;
import com.green.book.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	BookService bService;
	
	// 도서 입력 페이지
	@GetMapping("/book/bookAdd") // /book/bookAdd 이 주소랑 연결
	public String bookadd() {
		System.out.println("bookadd()");
		return "BookAdd"; // 해당 페이지의 html
	}
	
	// 도서 대여 페이지
	@GetMapping("/book/bookRent")
	public String bookrent(BookDTO bdto) {
		System.out.println("bookrent()");
		
		bService.canRent(bdto);
		
//		ModelAndView mav = new ModelAndView();
//		
//		mav.addObject("bookName", bdto.getBookName());
//		mav.addObject("author", bdto.getAuthor());
//		mav.addObject("ISBN", bdto.getISBN());
//		mav.addObject("rentDate", bdto.getRentDate());
//		mav.addObject("returnDate", bdto.getReturnDate());
//		
//		mav.setViewName("bookRent");
		
		return "bookRent";
	}
	
	// 대여 도서 검색 페이지
//	@GetMapping("/book/find")
//	public String find(BookDTO bdto) {
//		System.out.println("find()");
//		
//		bService.rentList(bdto);
//		
//		return "bookFind";
//	}
	
	// 대여 리스트 페이지
	@GetMapping("/book/bookList")
	public ModelAndView booklist(BookDTO bdto) { // 매개변수
		System.out.println("booklist()");
		
//		bService.rentList(bdto);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("bookName", bdto.getBookName());
		mav.addObject("author", bdto.getAuthor());
		mav.addObject("ISBN", bdto.getISBN());
		mav.addObject("renterName", bdto.getRenterName());
		mav.addObject("rentDate", bdto.getRentDate());
		mav.addObject("returnDate", bdto.getReturnDate());
		
		mav.setViewName("bookList");
		
		return mav;
	}
	
}
