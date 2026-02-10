package reviewboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import reviewboard.dto.PageHandler;
import reviewboard.dto.ReviewBoardDTO;
import reviewboard.service.ReviewBoardService;

@Controller
public class ReviewBoardController {
	
	@Autowired
	ReviewBoardService reviewboardservice;
	
	// 01. 전체 리뷰 출력하는 컨트롤러(홈 화면 역할), 평균 평점
	@GetMapping({"/",""})
	public String reviewlistPro(Model model,
			// 기본으로 보여주는 페이지 : 1
			@RequestParam(value="page", defaultValue="1") int page,
			// 한 페이지에 보여주는 게시글 개수 : 1
			@RequestParam(value="pageSize", defaultValue="5") int pageSize
			) {
		System.out.println("ReviewBoardController reviewlistPro() : 리뷰 리스트 출력");
		List<ReviewBoardDTO> result = reviewboardservice.allReviewList();
		
		int totalCnt;
		
		// 출력할 리뷰 존재
		if(result != null) {
			System.out.println("리뷰 존재");
			double avg = reviewboardservice.avgReview();
			System.out.println(avg);
			// 평균별점 반올림, 반내림
			int star = (int) Math.round(avg);
			model.addAttribute("list", result); // model객체에 담아서 html로 공유
			model.addAttribute("avg", avg);
			model.addAttribute("star", star);
			// 전체 리뷰 개수 메서드 호출
			totalCnt = reviewboardservice.reviewCnt();
			
			// pageHandler 접근
			PageHandler ph = new PageHandler(totalCnt, page, pageSize);
			List<ReviewBoardDTO> reviewlist;
			reviewlist = reviewboardservice.pagelist(ph.getStartRow(),ph.getEndRow());
			model.addAttribute("ph", ph);
			model.addAttribute("reviewlist", reviewlist);
		}
		// 출력할 리뷰가 없음
		else {
			System.out.println("리뷰 내역이 없습니다.");
		}
		return "reviewList";
	}

	
	// 02. 리뷰 작성하는 화면 이동 컨트롤러
	@GetMapping("/review/reviewwrite")
	public String reviewWrite() {
		System.out.println("ReviewBoardController reviewWrite() : 리뷰 작성 화면 출력");
		String nextPage = "reviewWrite";
		return nextPage;
	}
	
	// 03. 리뷰 저장 컨트롤러
	@PostMapping("/review/reviewWritePro")
	public String reviewWritePro(ReviewBoardDTO rdto) {
		System.out.println("ReviewBoardController reviewWritePro() : 리뷰 저장 메서드");
		boolean result = reviewboardservice.addReview(rdto);
		// 저장 성공
		if(result) {
			return "redirect:/"; // 메인으로 이동
		}
		// 저장 실패
		else {
			return "/review/reviewwrite"; // 다시 리뷰 작성 페이지로 이동
		}
	}
	
	// 04. 해당 리뷰 상세보기 컨트롤러
	@GetMapping("/review/reviewDetail")
	public String reviewDetail( // th:href="@{/review/reviewDetail(num=${li.num})}" 매개변수값으로 num을 넘겨줌
			@RequestParam("num") int num,
			Model model
			) { 
		System.out.println("ReviewBoardController reviewDetail() : 상세 리뷰 화면 출력");
		ReviewBoardDTO result = reviewboardservice.detailReview(num);
		model.addAttribute("detailReview", result);
		String nextPage = "reviewDetail";
		return nextPage;
	}
	
	// 05. 해당 리뷰 수정하는 화면 이동 컨트롤러
	@GetMapping("/review/reviewModify")
	public String reviewModifyForm(
			@RequestParam("num") int num,
			Model model
			) {
		System.out.println("ReviewBoardController reviewModify() : 해당 리뷰 수정 화면 출력");
		// 수정할 해당 리뷰 정보 가져오는 메서드
		ReviewBoardDTO oneReview = reviewboardservice.detailReview(num);
		model.addAttribute("list", oneReview);
		return "reviewModify";
	}
	
	// 06. 해당 리뷰 수정 컨트롤러
	@PostMapping("/review/reviewModifyPro")
	public String reviewModifyPro(
			ReviewBoardDTO rdto
			) {
		System.out.println("ReviewBoardController reviewModify() : 해당 리뷰 수정");
		boolean result = reviewboardservice.reviewModi(rdto);
		// 수정 성공
		if(result) {
			return "redirect:/";
		}
		// 수정 실패
		else {
			return "/review/reviewModify";
		}
	}
	
	// 07. 해당 리뷰 삭제 메서드
	@PostMapping("/review/reviewdelete")
	public String reviewdelete(
			@RequestParam("num") int num,
			RedirectAttributes re
			) {
		System.out.println("ReviewBoardController reviewdelete() : 해당 리뷰 삭제");
		boolean result = reviewboardservice.reviewDelete(num);
		if(result) {
			re.addFlashAttribute("msg", "삭제되었습니다");
			return "redirect:/";
		}else {
			re.addFlashAttribute("msg", "다시 삭제해주세요");
			return "redirect:/";
		}
	}
}
