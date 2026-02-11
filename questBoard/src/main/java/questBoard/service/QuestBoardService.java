package questBoard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import questBoard.dto.QuestBoardDTO;

public interface QuestBoardService {

	// 전체 출력 메서드
	public List<QuestBoardDTO> getAllBoardList();
	
	// 리스트 작성 메서드(insert)
	public void writeBoard(QuestBoardDTO qdto);

	// 상세 게시글 출력 메서드
	public QuestBoardDTO detail(int num);

	// 댓글 insert 메서드
	public void replywrite(QuestBoardDTO qdto);

	// 이미 댓글이 달렸는지 확인하는 메서드
	public boolean checkReply(@Param("ref") int ref);
	
	
}
