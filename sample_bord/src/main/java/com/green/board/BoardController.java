package com.green.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {
	
	// 메인게시판으로 화면 이동하는 메서드
	@GetMapping("/board/boardMain")
	public String boardPage() {
		System.out.println("BoardController boardPage()");
		String nextPage= "board/boardMain";
		return nextPage;
	}
	
	// 게시글 작성 화면으로 이동하는 메서드
	@GetMapping("/board/boardWrite")
	public String boardForm() {
		System.out.println("BoardController boardForm()");
		String nextPage="/board/boardWrite";
		return nextPage;
	}
	
	// 게시글 작성 후 게시하는 메서드
	@PostMapping("/board/boardWrite")
	public String boardPost(BoardDTO bdto) {
		System.out.println("BoardController boardPost()");
		String nextPage= "board/boardMain"; // 게시 후 메인 게시글로 돌아옴
		boolean result = 
		
		// 게시 성공(true)
		if()
		return nextPage;
	}
}
