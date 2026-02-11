package questBoard.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import questBoard.dto.QuestBoardDTO;
import questBoard.service.QuestBoardService;

@Controller
public class QuestBoardController {
	
	@Autowired
	QuestBoardService questboardservice;
	
	// 전체 출력 화면 이동 컨트롤러
	@GetMapping("/board/list")
	public String boardListForm(Model model) {
		System.out.println("QuestBoardController boardListForm()");
		
		List<QuestBoardDTO> allList = questboardservice.getAllBoardList();
		model.addAttribute("list", allList);
		
		System.out.println("리스트 개수: " + allList.size());
		if(!allList.isEmpty()){
		    System.out.println("첫번째 날짜 데이터: " + allList.get(0).getReg_date());
		}
		
		return "boardList";
	}
	
	// 작성 화면 이동 컨트롤러
	@GetMapping("/board/write")
	public String boardWriteForm() {
		System.out.println("QuestBoardController boardWriteForm()");
		
		return "boardWrite";
	}
	
	// 작성 처리 컨트롤러 (※ 파일 업로드는 @PostMapping만 가능)
	@PostMapping("/board/writePro")
	public String boardWritePro(
			QuestBoardDTO qdto,
			// 이미지 받아오기(<input type="file" name="img">에서 name이랑 같아야함 ★)
			@RequestParam("imgFile") MultipartFile upload
			) throws IllegalStateException, IOException {
		System.out.println("QuestBoardController boardWriteForm()");
		
		// 이미지 파트
		
		// 01. 파일을 저장할 실제 하드디스크 위치 지정
		// (※ WebConfig에서 설정한 경로('file:///c:/upload/')와 반드시 일치)
		String savePath = "c:/upload/";
		
		// 02. [안전장치] c:/upload/경로의 폴더가 존재하지 않을 경우, 자동생성 : mkdirs()
		// File은 해당 폴더의 존재여부 확인(exists())과 자동생성해주는(mkdirs())메서드를 사용하기 위해 인스턴스
		File saveDir = new File(savePath);
		if(!saveDir.exists()) {
			saveDir.mkdirs();
		}
		
		// 03. 이미지 업로드 처리
		// [예외처리] 작성자가 이미지를 선택했을때(미선택시 해당사항 없음)
		if(!upload.isEmpty()) {
			// 사용자가 올린 원래 파일명 (예: 20.jpg)을 가져옴
			String originalName = upload.getOriginalFilename();
			
			// c:/upload/20.jpg
			File file = new File(savePath + originalName);
			
			// transferTo() : 이 명령어가 실행되는 순간 서버 메모리에서 존재하던 파일이 실제 하드디스크 c:/upload로 복사됨
			upload.transferTo(file);
			
			// DB에 저장할 파일명 DTO에 세팅
			qdto.setImg(originalName);
		}
		
		questboardservice.writeBoard(qdto);
		
		return "redirect:/board/list";
	}
	
	// 상세 보기 화면 이동 컨트롤러
	@GetMapping("/board/detail")
	public String boardDetail(
			Model model,
			@RequestParam("num") int num
			) {
		System.out.println("QuestBoardController boardDetail()");
		QuestBoardDTO result = questboardservice.detail(num);
		model.addAttribute("onelist", result);
		
		System.out.println("상세보기 제목:" + result.getSubject());
		
		return "boardDetail";
	}
	
	// 답글 쓰기 화면 이동 컨트롤러
	@GetMapping("/board/reply")
	public String replyBoardForm(
			RedirectAttributes re,
			Model model,
			@RequestParam("num") int num, // 몇번 게시글의 답변을 쓸건지
			@RequestParam("ref") int ref // 어떤 게시글의 답변인지 구분하기 위함, ref로 리스트를 출력할 때 질문과 답변을 함께 붙여서 출력 가능
			) {
		System.out.println("QuestBoardController replyBoard()");
		
		// 이미 댓글이 달렸는지 확인하는 메서드
		boolean result = questboardservice.checkReply(ref);
		if(result) {
			re.addFlashAttribute("msg", "이미 답글이 달린 게시글입니다.");
			return "redirect:/board/list";
		}else {
			// model에 담아서 num, ref값을 공유하기위해 replyboardReWrite_Form에서 사용하진않고 계속 들고 옮겨다님 
			model.addAttribute("num", num);
			model.addAttribute("ref", ref);
			
			return "replyboardReWrite_Form";
		}
	}
	
	// 답글 처리 컨트롤러
	@PostMapping("/board/reWritePro")
	public String replyWritePro(
			QuestBoardDTO qdto
			) {
		System.out.println("QuestBoardController replyWritePro()");
		questboardservice.replywrite(qdto); // 여기서 num, ref 값을 매개변수로 사용하기 위해 위에서부터 값을 계속 들고다님
		
		return "redirect:/board/list";
	}
	
}
