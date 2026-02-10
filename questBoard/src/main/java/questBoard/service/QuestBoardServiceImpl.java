package questBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import questBoard.dto.QuestBoardDTO;
import questBoard.mapper.QuestBoardMapper;

@Service
public class QuestBoardServiceImpl implements QuestBoardService{
	
	@Autowired
	QuestBoardMapper questboardmapper;

	// 전체 출력 메서드
	@Override
	public List<QuestBoardDTO> getAllBoardList() {
		System.out.println("QuestBoardServiceImpl getAllBoardList()");
		return questboardmapper.getAllList();
	}

	// 리스트 작성 메서드(insert)
	@Override
	public void writeBoard(QuestBoardDTO qdto) {
		System.out.println("QuestBoardServiceImpl writeBoard()");
		questboardmapper.insertWriteBoard(qdto);
		
	}
	
	


	
}
