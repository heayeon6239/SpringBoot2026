package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardservice;
	
	// 메인게시판으로 화면 이동하는 메서드(전체 게시글 출력)
	@GetMapping("/board/boardMain")
	public String boardPage(Model model, 
			@RequestParam(value="searchType", required=false) String searchType, 
			@RequestParam(value="findKeyword", required=false)String findKeyword, 
			RedirectAttributes re) { // boardList를 화면에 출력시켜야 하기때문에 model을 통해 값을 담아서 공유해야함
		
		System.out.println("BoardController boardPage()");
		System.out.println(searchType);
		System.out.println(findKeyword);
		String nextPage= "board/boardMain";
		List<BoardDTO> boardList;
		
		// 검색 게시판 출력
		if(searchType != null && !findKeyword.trim().isEmpty() ) {
			boardList = boardservice.search(searchType, findKeyword);
			// 검색 결과가 없을 경우
			if(boardList.isEmpty()) {
				re.addFlashAttribute("msg", "검색 결과가 없습니다.");
				return "redirect:/board/boardMain";
			}
		}
		// 전체 게시판 출력
		else {
			boardList = boardservice.printAll();
		}
		
		model.addAttribute("list", boardList);
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
	@PostMapping("/board/boardContfirm")
	public String boardPost(BoardDTO bdto,RedirectAttributes re) {
		System.out.println("BoardController boardPost()");
		String nextPage= "/board/boardWrite"; // 게시에 실패할 경우 다시 작성화면으로 돌아옴
		boolean result = boardservice.addBoard(bdto);
		
		// 게시 성공(true)
		if(result) {
			re.addFlashAttribute("msg", "글이 성공적으로 게시되었습니다.");
			return "redirect:/board/boardMain";
		}else {
			re.addFlashAttribute("msg", "글 게시를 실패하였습니다.");
			return nextPage;
		}
		
	}
	
	// 해당 작성자의 게시글로 상세 이동하는 메서드
	@GetMapping("/board/oneboard")
	public String oneboardPage(@RequestParam("id") int id, Model model) {
		System.out.println("BoardController oneboardPage()");
		BoardDTO bdto = boardservice.printoneboard(id); // service에서 해당 작성자의 게시글 데이터를 bdto에 다시 담음
		model.addAttribute("onelist", bdto); // bdto에 담은걸 onelist라는 변수에 담아 model을 통해 공유
//		System.out.println("result; "+bdto.getTitle());
		return "/board/oneboard";
	}
	
	// 해당 작성자의 게시글을 수정하는 페이지로 이동하는 메서드
	@GetMapping("/board/boardmodify")
	public String boardmodifyPage(@RequestParam("id") int id, Model model) {
		System.out.println("BoardController boardmodifyPage() 수정페이지로 이동");
		BoardDTO bdto = boardservice.printoneboard(id); // service에서 가져온 해당 게시글의 데이터를 bdto에 담음
		model.addAttribute("onelist", bdto); // model에 담아서 공유
		return "/board/boardmodify";
	}
	
	// 해당 작성자의 게시글을 수정한 후, 메인 출력 페이지로 이동하는 메서드
	@PostMapping("/board/boardmodify")
	public String boardmodify(BoardDTO bdto) {
		System.out.println("BoardController boardmodify() 수정 메서드");
		boolean result = boardservice.onemodify(bdto);
		System.out.println(result);
		
		// 수정 성공(true)
		if(result) {
			return "redirect:/board/boardMain";
			
		}
		// 수정 실패(false)
		else {
			return "redirect:/board/boardmodify?id="+bdto.getId();
		}
	}
	
	// 해당 작성자의 게시글을 삭제한 후, 메인 출력 페이지로 이동하는 메서드
	@GetMapping("/board/boardremove")
	public String boardremove(@RequestParam("id") int id) {
		System.out.println("BoardController boardremove() 삭제 메서드");
		boolean result = boardservice.removeboard(id);
		if(result) {
			return "redirect:/board/boardMain";
		}else {
			return "redirect:/board/boardMain";
		}
		
	}
	
	// ----------------------- 2026-01-29 --------------------------

	
	
}

