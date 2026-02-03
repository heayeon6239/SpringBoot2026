package com.green.board;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.board.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
//	BoardDAO bdao;
	BoardMapper boardmapper;
	
	// 게시글이 추가되는 메서드(bdao호출하여 사용)
	public void addBoard(BoardDTO bdto) {
		System.out.println("service: "+bdto.getWriterPw());
		System.out.println("BoardService addBoard()");
		boardmapper.insertBoard(bdto);
	}
	
	// 전체 게시글 출력 메서드
	public List<BoardDTO> allBoard(){
		System.out.println("BoardService allBoard()");
		return boardmapper.getAllBoard();
	}
	
	// 해당 게시글 출력 메서드
	public BoardDTO oneBoard(int num) {
		System.out.println("BoardService oneBoard()");
		boardmapper.getOneBoard02(num); // 조회수 증가
		return boardmapper.getOneBoard(num); // 출력
	}
	
	// 해당 게시글 수정 메서드
	public boolean modifyBoard(BoardDTO bdto) {
		System.out.println("BoardService updateBoard()");
		int result = boardmapper.updateBoard(bdto);
		if(result > 0) {
			System.out.println("게시글 수정 완료");
			return true;
		}else {
			System.out.println("게시글 수정 실패");
			return false;
		}
	}
	
	// ----------------------- 2026-01-29 --------------------------
	
	// 해당 게시글 삭제 메서드
	public boolean removeBoard(int num, String writerPw) {
		System.out.println("BoardService removeBoard()");
		int result = boardmapper.deleteBoard(num, writerPw);
		if(result > 0) {
			System.out.println("게시글 삭제 성공");
			return true;
		}else {
			System.out.println("게시글 삭제 실패");
			return false;
		}
	}
	
	// 게시글 검색 메서드
	public List<BoardDTO> searchBoard(String searchType, String SearchKeyword){
		System.out.println("BoardService searchBoard()");
		System.out.println("searchType: "+searchType+"SearchKeyword: "+SearchKeyword);
		return boardmapper.getSearchBoard(searchType, SearchKeyword);
	}
	
	// ----------------------- 2026-02-03 --------------------------
	
	// 전체 게시글의 개수를 구하는 메서드
	public int getAllCount() {
		System.out.println("BoardService getAllCount()");
		return boardmapper.getAllCount();
	}
	
	// 전체 게시글의 시작 게시글 번호(startRow), 몇개의 행(pageSize)만큼 보는 메서드
	public List<BoardDTO> getPageList(int startRow, int pageSize){
		System.out.println("BoardService getPageList()");
		return boardmapper.getPageList(startRow, pageSize);
	}
}
