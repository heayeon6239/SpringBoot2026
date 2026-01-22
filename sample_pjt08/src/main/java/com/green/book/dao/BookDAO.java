package com.green.book.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.green.book.dto.BookDTO;


// 데이터 저장소
@Repository
public class BookDAO {
	
	// DB 역할의 HashMap<> - 전체 도서
	private HashMap<String,BookDTO> bookDB = new HashMap<>();
	// DB 역할의 HashMap<> - 대여 도서
	private HashMap<String,BookDTO> rentBookDB = new HashMap<>();
	// DB 역할의 HashMap<> - 해당 대여자의 대여 내역
	private HashMap<String,BookDTO> renterDB = new HashMap<>();
	
	
	// 01. insertBook() 메서드
	public void insertBook(BookDTO bdto) {
		System.out.println("도서 추가");
		bookDB.put(bdto.getBookName(), bdto);
	}
	
	// 02. selectBook() 메서드
	public BookDTO selectBook(BookDTO bdto) {
		System.out.println("대여 도서 확인");
		
		// 책 제목이랑 비교해서 대여 도서 DB에 해당 책 제목이 있으면 checkBook에 담기
		BookDTO checkBook = rentBookDB.get(bdto.getBookName());
		
		return checkBook;
	}
	
	// 03. selectName() 메서드
//	public BookDTO selectName(BookDTO bdto) {
//		System.out.println("해당 대여자의 내역");
//		
//	}
	
	// 04. insertRentBook() 메서드
	public void insertRentBook(BookDTO bdto) {
		System.out.println("대여 도서 추가");
		rentBookDB.put(bdto.getBookName(), bdto);
	}
}
