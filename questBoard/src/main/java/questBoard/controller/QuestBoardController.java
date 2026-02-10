package questBoard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
		
		return "boardList";
	}
	
	// 작성 화면 이동 컨트롤러
	@GetMapping("/board/write")
	public String boardWriteForm() {
		System.out.println("QuestBoardController boardWriteForm()");
		
		return "boardWrite";
	}
	
	// 작성 처리 컨트롤러
	@PostMapping("/board/writePro")
	public String boardWritePro(QuestBoardDTO qdto) {
		System.out.println("QuestBoardController boardWriteForm()");
		
		questboardservice.writeBoard(qdto);
		
		return "redirect:/board/list";
	}
	
}
