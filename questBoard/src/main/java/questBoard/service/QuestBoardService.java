package questBoard.service;

import java.util.List;

import questBoard.dto.QuestBoardDTO;

public interface QuestBoardService {

	// 전체 출력 메서드
	public List<QuestBoardDTO> getAllBoardList();
	
	// 리스트 작성 메서드(insert)
	public void writeBoard(QuestBoardDTO qdto);
	
	
}
