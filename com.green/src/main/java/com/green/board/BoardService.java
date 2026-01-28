package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	BoardDAO bdao;
	
	// 게시글이 추가되는 메서드(bdao호출하여 사용)
	public void addBoard(BoardDTO bdto) {
		System.out.println("BoardService addBoard()");
		bdao.insertBoard(bdto);
	}
	
	// 전체 게시글 출력 메서드
	public List<BoardDTO> allBoard(){
		System.out.println("BoardService allBoard()");
		return bdao.getAllBoard();
	}
	
	// 해당 게시글 출력 메서드
	public BoardDTO oneBoard(int num) {
		System.out.println("BoardService oneBoard()");
		return bdao.getOneBoard(num);
	}
	
	// 해당 게시글 수정 메서드
	public boolean modifyBoard(BoardDTO bdto) {
		System.out.println("BoardService updateBoard()");
		int result = bdao.updateBoard(bdto);
		if(result > 0) {
			System.out.println("게시글 수정 완료");
			return true;
		}else {
			System.out.println("게시글 수정 실패");
			return false;
		}
	}
}
