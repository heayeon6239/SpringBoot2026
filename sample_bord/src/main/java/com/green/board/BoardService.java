package com.green.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	BoardDAO bdao;
	
	// 작성한 게시글 추가 메서드
	public boolean addBoard(BoardDTO bdto) {
		System.out.println("BoardService addBoard()");
		return bdao.addBoardPost(bdto) == 1;
	}
}
