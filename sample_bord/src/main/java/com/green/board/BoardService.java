package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.board.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
//	BoardDAO bdao;
	BoardMapper boardmapper;
	
	// 작성한 게시글 추가 메서드
	public boolean addBoard(BoardDTO bdto) {
		System.out.println("BoardService addBoard()");
		return boardmapper.addBoardPost(bdto) == 1;
	}
	
	// 전체 게시글 출력 메서드
	public List<BoardDTO> printAll(){
		System.out.println("BoardService printAll()");
		return boardmapper.PrintAllBoard();
	}
	
	// 해당 id의 게시글 데이터를 가져오는 메서드
	public BoardDTO printoneboard(int id) {
		System.out.println("BoardService printoneboard() 해당 id 1개의 데이터를 가져오는 메서드");
		BoardDTO bdto = boardmapper.oneboard(id);
		return bdto;
	}
	
	// 해당 id의 게시글의 수정 확인 메서드
	public boolean onemodify(BoardDTO bdto) {
		System.out.println("BoardService onemodify() 수정 확인 메서드");
		System.out.println(boardmapper.oneboardmodify(bdto));
		return boardmapper.oneboardmodify(bdto) == 1;
	}

	// 해당 id의 게시글의 삭제 메서드
	public boolean removeboard(int id) {
		System.out.println("BoardService removeboard() 삭제 메서드");
		return boardmapper.remove(id) == 1;
	}
	
	// ----------------------- 2026-01-29 --------------------------
	
	// 게시글 검색 메서드
	public List<BoardDTO> search(String searchType, String findKeyword){
		System.out.println("BoardService search() 검색 메서드");
		return boardmapper.searchBoard(searchType, findKeyword);
	}
	
	// ----------------------- 2026-02-03 --------------------------
	
	// 전체 게시글의 개수를 구하는 메서드
	public int getAllCount() {
		System.out.println("BoardService getAllCount()");
		return boardmapper.getAllCount();
	}
	
	// 전체 게시글의 시작 게시글번호(startRow), 몇개의 게시글(onePageSize)만큼 출력하는 메서드
	// (페이징 처리가 된 버전)
	public List<BoardDTO> getPageList(int startRow, int onePageSize){
		System.out.println("BoardService getPageList()");
		return boardmapper.getPageList(startRow, onePageSize);
	}
}
