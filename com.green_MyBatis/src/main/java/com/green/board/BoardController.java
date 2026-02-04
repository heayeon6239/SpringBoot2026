package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.member.MemberDTO;

import jakarta.servlet.http.HttpSession;

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
	public String boardWritePro(BoardDTO bdto, HttpSession session) {
		System.out.println("(1) BoardController boardWritePro()");
		
		// session.setAttribute("loginmember") 저장한 데이터를 꺼내와야함
		// 세션에서 값을 꺼내오는 메서드 session.getAttribute("loginmember")
		// ★ Session은 자바의 Object 최상위 객체이므로, 다운캐스팅 해야함 !! 
		// 로그인 id => admin9867의 정보 한 행이 모두 MemberDTO타입으로 loginMember에 저장
		MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginmember"); // 명시적 형변환
		
		// 로그인 정보가 존재하는지 체크하는 코드 필요
		if(loginedMember != null) {
			// 지금 현재 로그인된 id는 loginedMember.getId()
			bdto.setId(loginedMember.getId()); // session에 저장된 로그인된 id의 값을 들고옴
			System.out.println("DB에 저장될 ID확인"+loginedMember.getId());
		}else {
			System.out.println("로그인 실패");
			return "redirect:/member/login";
		}
		
		boardservice.addBoard(bdto);
		// 저장 후에는 게시판 목록으로 페이지 이동(redirect)
		return "redirect:/board/list";
	}
	
	// 03. 전체 게시글 목록 출력 (boardlist에 담긴 전체 데이터 값들을 model에 담음)
	// 03-1 검색기능을 추가한 커스텀 버전
	@GetMapping("/board/list")
	public String boardListForm(Model model, 
			@RequestParam(value="searchType", required=false) String searchType, 
			@RequestParam(value="searchKeyword", required=false) String searchKeyword,
			// 01. 페이지 번호 => 1부터 시작이므로 초기값 1로 정의
			@RequestParam(value="page", defaultValue = "1") int page,
			// 02. 페이지 사이즈 => 한 화면에 보여지는 게시글의 개수를 5로 초기화
			@RequestParam(value="pageSize", defaultValue = "5") int pageSize
			) {
		System.out.println("(1) BoardController boardListForm()");
		
		// 검색 유무에 따라 totalCnt 값 담기
		int totalCnt;
		if(searchType != null && searchKeyword.trim().isEmpty()) {
			// 03. 검색 결과 게시글의 개수인 totalCnt 메서드 가져오기
			totalCnt = boardservice.getSearchCount(searchType, searchKeyword);
		}else {
			// 03. 전체 게시글의 개수인 totalCnt 메서드 가져오기
			totalCnt = boardservice.getAllCount();
		}
		
		// 04. pageHandler 클래스 접근하기 위해 인스턴스화
		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
		
		List<BoardDTO> listboard;
		
		// 검색 내용 출력
		if(searchType != null && !searchKeyword.trim().isEmpty()) { // 공백인지 아닌지
//			listboard = boardservice.searchBoard(searchType, SearchKeyword);
			listboard = boardservice.getSearchPageList(searchType, searchKeyword, ph.getStartRow(), pageSize);
		}
		// 전체 출력
		else {
//			listboard = boardservice.allBoard(); // 전체보기
			// 위 메서드 사용 못하는 이유 : 페이징이 안된 모든 레코드가 출력되는 메서드이므로 사용 금지
			listboard = boardservice.getPageList(ph.getStartRow(), pageSize);
		}
		
		// 검색 내용만 담겼거나, 전체가 담긴 listboard를 li에 담음
		model.addAttribute("li", listboard);
		// PageHandler 클래스 모두 model객체에 담아서 html로 보내야 UI화면에 페이징을 그릴 수 있음
		model.addAttribute("ph", ph);
		// 검색하는 타입과, 항목을 UI에 넘겨주지 않으면 오류가 뜸 
		// 반드시 searchType, SearchKeyword를 model에 담아서 boardList.html에 넘겨줘야함
		// listboard에 매개변수 값으로 searchType, SearchKeyword가 포함되어 있기 때문
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		
		
		String nextPage = "/board/boardList";
		return nextPage;
	}
	// 03-2 원본 버전
//	@GetMapping("/board/list")
//	public String boardListForm(Model model) {
//		System.out.println("(1) BoardController boardListForm()");
//		List<BoardDTO> listboard = boardservice.allBoard(); // List<E> 부모 타입으로 호출(Arraylist 배열)
//		model.addAttribute("li", listboard);
//		String nextPage = "/board/boardList";
//		return nextPage;
//	}
	
	// 04. 해당 게시글 출력(num 번호를 받아 -> 해당 게시글 DB에서 조회 -> 받아온 상세 정보를 boardDTO에 저장
	@GetMapping("board/boardInfo")
	public String boardInfoForm(@RequestParam("num") int num, Model model) {
		System.out.println("(1) BoardController boardInfoForm()");
	
		BoardDTO bdto = boardservice.oneBoard(num); // 정보 출력
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
	
	// ----------------------- 2026-01-29 --------------------------
	
	// 07. 해당 게시글 삭제 핸들러
	@GetMapping("/board/deletePro")
	public String deletePro(@RequestParam("num") int num, @RequestParam("writerPw") String writerPw) {
		System.out.println("(1) BoardController deletePro()");
		boolean result = boardservice.removeBoard(num, writerPw);
		// 삭제 성공 -> list
		if(result) {
			return "redirect:/board/list";
		}
		// 삭제 실패 -> boardInfo 유지
		else {
			return "redirect:/board/boardInfo?num="+num;
		}
		
	}
	
	// ----------------------- 2026-02-04 -------------------------
	
	// 로그인된 나의 게시글 목록 검색하는 핸들러
	@GetMapping("/board/mypage")
	public String myBoardList(
			Model model, 
			HttpSession session,
			@RequestParam(value="page", defaultValue = "1") int page
			) {
		System.out.println("BoardController myBoardList()");
		
		// 세션 키 이름을 loginmember로 가져오기(: getAttribute(key값))
		// 해당 id의 모든 데이터(MemberDTO)를 가져와야함
		// loginId = MemberDTO 전부 담김
		MemberDTO loginId = (MemberDTO) session.getAttribute("loginmember"); // MemberDTO로 다운 캐스팅 ★★★★★
		
		// 로그인 실패 & 로그인이 안된 상태 => member/login으로 이동
		if(loginId == null) {
			System.out.println("로그인 X 로그인 필요");
			return "redirect:/member/login";
		}
		
		int pageSize = 5; // @RequestParam으로 값 지정도 가능하고 이렇게도 가능 !!
		
		// 로그인된 내 게시글의 개수 조회
		int totalCnt = boardservice.getMyBoardCount(loginId.getId());
		
		// pageHandler 클래스 인스턴스(페이징 기능 사용가능)
		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
		
		// 로그인된 내 게시글 목록 가져오기(ph로 인스턴스 했기 때문에 ph.getStartRow() 이렇게 사용가능)
		List<BoardDTO> mylist = boardservice.getMyBoardList(loginId.getId(), ph.getStartRow(), pageSize);
		
		// model로 공유
		model.addAttribute("list", mylist);
		model.addAttribute("ph", ph);
		
		return "/board/mypage";
	}
	

}
