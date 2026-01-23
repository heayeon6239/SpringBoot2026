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
	public HashMap<String,BookDTO> rentBookDB = new HashMap<>(); // 원래 private 였으나, 다른 패키지에서 사용해야함으로 public으로 변경했음
	// DB 역할의 HashMap<> - 해당 대여자의 대여 내역
	private HashMap<String,BookDTO> renterDB = new HashMap<>();
	
	
	// 01. insertBook() 메서드
	public void insertBook(BookDTO bdto) {
		System.out.println("도서 추가");
		bookDB.put(bdto.getBookName(), bdto);
		System.out.println("현재 저장된 도서 수: " + bookDB.size());
	}
	
	// 02. selectBook() 메서드
//	public BookDTO selectBook(BookDTO bdto) {
//		System.out.println("대여 도서 확인");
//		
//		// 책 제목이랑 비교해서 대여 도서 DB에 해당 책 제목이 있으면 checkBook에 담기인데 일단 사용하지 않겠음 기본적으로 되는지 확인해야해서
//		BookDTO checkBook = rentBookDB.get(bdto.getBookName());
//		
//		return checkBook;
//	}
	
	// 04. insertRentBook() 메서드
	public void insertRentBook(BookDTO bdto) {
		System.out.println("대여 도서 추가");
		rentBookDB.put(bdto.getBookName(), bdto); // bdto을 데이터 타입으로 들어온 값을 rentBookDB 에 추가함
		System.out.println("현재 저장된 대여도서 수: " + rentBookDB.size());
	}
}
