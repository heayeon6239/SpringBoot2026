package replyboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import replyboard.dto.ReplyBoardDTO;
import replyboard.service.ReplyBoardService;

@Controller
public class ReplyBoardController {
	
	@Autowired
	// 반드시 ReplyBoardService() 인터페이스를 의존객체로 삽입해야함을 주의
	ReplyBoardService replyBoardService;
	
	// 게시글 목록으로 화면 이동하는 컨트롤러
	@GetMapping("/board/list")
	public String boardList(Model model) {
		System.out.println("ReplyBoardController boardList()");
		
		List<ReplyBoardDTO> replyList = replyBoardService.getAllReplyBoard();
		model.addAttribute("rlist", replyList);
		
		return "/replyBoard/replyboardList";
	}
	
	// 게시글 작성 화면 이동하는 컨틀롤러
	@GetMapping("/board/writer")
	public String boardWriteForm() {
		System.out.println("ReplyBoardController boardWrite()");
		
		return "/replyBoard/replyboardWrite_Form";
	}
	
	// 게시글 작성 컨트롤러
//	@PostMapping("/board/writerPro")
//	public String boardWritePro(ReplyBoardDTO rdto) {
//		System.out.println("ReplyBoardController boardWritePro()");
//		
//		replyBoardService.insertReplyBoard(rdto);
//		return "redirect:/board/list";
//	}
	
	// 파일 업로드는 @PostMapping()만 가능
	@PostMapping("/board/writerPro")
	public String boardWriterPro(
			ReplyBoardDTO rdto,
			// name이랑 똑같아야함 !!!
			@RequestParam("file1") MultipartFile upload1,
			@RequestParam("file2") MultipartFile upload2
			) throws IllegalStateException, IOException {
		System.out.println("ReplyBoardController boardWriterPro()");
		
		// 01. 파일을 저장할 실제 하드디스크 위치 지정
		// WebConfig에서 설정한 'file:///c:/upload/' 이 경로와 반드시 일치해야함
		String savePath = "c:/upload/";
		
		// 02. [안전장치] 만약 C:/upload/폴더가 존재하지 않으면 프로그램을 통해 자동 생성되도록 작성함
		File saveDir = new File(savePath);
		// 존재하지 않으면
		if(!saveDir.exists()) {
			// mkdirs()는 폴더가 없어도 한꺼번에 만들어주는 메서드
			saveDir.mkdirs();
		}
		
		// 03. 첫번째 이미지 업로드 처리
		// [예외처리] 이미지가 비어있으면 추가 불가능
		if(!upload1.isEmpty()) { // 사용자가 실제 파일을 선택해서 보냈는지 확인
			// 사용자가 올린 원래 파일명 (예: 20.jpg)을 가져옴
			String originalName1 = upload1.getOriginalFilename();
			String saveName1 = originalName1;
			
			// c:/upload/20.jpg
			File file1 = new File(savePath+saveName1);
			
			// transferTo() : 이 명령어가 실행되는 순간 서버 메모리에서 존재하던 파일이 실제 하드디스크 c:/upload로 복사됨
			upload1.transferTo(file1);
			
			// DB에 저장할 파일명 DTO에 세팅
			rdto.setUpload1(saveName1);
		}
		replyBoardService.insertReplyBoard(rdto);
		return "redirect:/board/list";
	}
	
	// 게시글 상세 화면 이동 컨트롤러
	@GetMapping("/board/detail")
	public String getOneBoard(
			@RequestParam("num") int num,
			Model model
			) {
		System.out.println("ReplyBoardController boardDetailForm()");
		
		ReplyBoardDTO oneList = replyBoardService.getOneBoard(num);
		model.addAttribute("onelist", oneList);
		
		return "/replyBoard/replyboardDetail";
	}
	
	// 답글 작성하는 화면 이동 컨트롤러
	@GetMapping("/board/reply")
	public String reWriteForm(
			Model model,
			@RequestParam("num") int num,
			@RequestParam("ref") int ref,
			@RequestParam("re_step") int re_step,
			@RequestParam("re_level") int re_level
			) {
		System.out.println("ReplyBoardController reWriteForm()");
		model.addAttribute("num", num);
		model.addAttribute("ref", ref);
		model.addAttribute("re_step", re_step);
		model.addAttribute("re_level", re_level);
		
		return "/replyBoard/replyboardReWrite_Form";
	}
	
	// 답글 작성 처리하는 컨트롤러
	@PostMapping("/board/reWritePro")
	public String reWritePro(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardController reWritePro()");
		
		replyBoardService.replyProcess(rdto); // update, insert 한번에 처리
		return "redirect:/board/list";
	}
}
