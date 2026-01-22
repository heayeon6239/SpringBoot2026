package com.green.book.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.book.dao.BookDAO;
import com.green.book.dto.BookDTO;

@Service
public class BookService {
	
	@Autowired
	BookDAO bdao;
	BookDTO bdto;
	
	// 대여 가능 확인 메서드
	public void canRent(BookDTO bdto) {
		
		// DAO의 selectBook(bdto)메서드로 대여 도서 내역에 해당 책이 존재하는지 확인
		// 존재 O -> 대여 불가능, 존재 X -> 대여 가능
		BookDTO checkBook = bdao.selectBook(bdto);
		
		if(checkBook != null) {
			System.out.println("대여 불가능");
		}else {
			System.out.println("대여 가능");
			
			// 대여일자 DTO에 수정해서 추가
			Date now = new Date(); 
			int date = now.getDate();
			int returndate = date+7;
//			LocalDate today = LocalDate(now);
			
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd(E) HH:mm:SS");
			
			bdto.setRentDate(s.format(now));
			bdto.setReturnDate(s.format(now));
			System.out.println(returndate);
			// 대여 도서 내역에 추가
			bdao.insertRentBook(bdto);
		}
	}
	
	// 해당 이름의 대여 내역 메서드
//	public String rentList(BookDTO bdto) {
//		
//		// 대여 도서 DB에 해당 renterName 이 존재하는지
//		BookDTO checkBook = bdao.selectBook(bdto);
//		
//		// 대여 도서 있음
//		if(checkBook != null) {
//			System.out.println("대여한 도서 있음");
//			System.out.println();
//		}else {
//			System.out.println("대여내역 없음");
//		}
//		
//		return "checkBook";
//	}
	
}
