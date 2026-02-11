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

	// 상세 게시글 출력 메서드
	@Override
	public QuestBoardDTO detail(int num) {
		System.out.println("QuestBoardServiceImpl detail()");
		return questboardmapper.boardDetail(num);
	}

	// 댓글 insert 메서드
	@Override
	public void replywrite(QuestBoardDTO qdto) {
		System.out.println("QuestBoardServiceImpl replywrite()");
		questboardmapper.reply(qdto);
		
	}

	// 이미 댓글이 달렸는지 확인하는 메서드
	@Override
	public boolean checkReply(int ref) {
		System.out.println("QuestBoardServiceImpl checkReply()");
		int result = questboardmapper.check(ref);
		// 존재 = 1
		if(result > 0) {
			return true;
		}
		// 존재X = 0
		else {
			return false;
		}
	}
	
	


	
}
