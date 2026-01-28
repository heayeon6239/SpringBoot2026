package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardservice;
	
	// 01. 게시글 작성 화면으로 이동하는 핸들러
	@GetMapping("/board/write")
	public String boardWriteForm() {
		System.out.println("(1) BoardController boardWriteForm()");
		String nextPage = "/board/boardWrite_form";
		return nextPage;
	}
	
	// 02. 화면에서 입력한 데이터를 DB에 영구 저장하는 데이터 추가 컨트롤러
	@PostMapping("/board/writePro")
	public String boardWritePro(BoardDTO bdto) {
		System.out.println("(1) BoardController boardWritePro()");
		boardservice.addBoard(bdto);
		// 저장 후에는 게시판 목록으로 페이지 이동(redirect)
		return "redirect:/board/list";
	}
	
	// 03. 전체 게시글 목록 출력 (boardlist에 담긴 전체 데이터 값들을 model에 담음)
	@GetMapping("/board/list")
	public String boardListForm(Model model) {
		System.out.println("(1) BoardController boardListForm()");
		List<BoardDTO> listboard = boardservice.allBoard(); // List<E> 부모 타입으로 호출(Arraylist 배열)
		model.addAttribute("li", listboard);
		String nextPage = "/board/boardList";
		return nextPage;
	}
	
	// 04. 해당 게시글 출력(num 번호를 받아 -> 해당 게시글 DB에서 조회 -> 받아온 상세 정보를 boardDTO에 저장
	@GetMapping("board/boardInfo")
	public String boardInfoForm(@RequestParam("num") int num, Model model) {
		System.out.println("(1) BoardController boardInfoForm()");
		
		BoardDTO bdto = boardservice.oneBoard(num);
		model.addAttribute("oneboard", bdto);
		
		String nextPage = "/board/boardInfo";
		return nextPage;
	}
	
	// 05. 해당 게시글 수정 화면 이동
	@GetMapping("/board/update")
	public String boardUpdateForm(@RequestParam("num") int num, Model model) {
		System.out.println("(1) BoardController boardUpdateForm()");
		
		BoardDTO bdto = boardservice.oneBoard(num); // 수정하고자하는 해당 게시글의 데이터 정보 들고오기 
		model.addAttribute("oneboard", bdto);
		
		String nextPage = "/board/boardUpdate_form";
		return nextPage;
	}
	
	// 06. 해당 게시글 수정 처리하는 핸들러
	@PostMapping("/board/updatePro")
	public String boardUpdatePro(BoardDTO bdto, Model model) {
		System.out.println("(1) BoardController boardUpdatePro()");
		
		boolean result = boardservice.modifyBoard(bdto);
		if(result) {
			return "redirect:/board/list";
		}else {
			return "redirect:/board/update?num="+bdto.getNum();
		}
		
	}
}
