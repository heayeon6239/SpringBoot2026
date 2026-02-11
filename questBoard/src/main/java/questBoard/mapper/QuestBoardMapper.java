package questBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import questBoard.dto.QuestBoardDTO;


@Mapper
public interface QuestBoardMapper {

	// 전체 출력 메서드
	public List<QuestBoardDTO> getAllList();

	// 작성 처리 메서드
	public void insertWriteBoard(QuestBoardDTO qdto);

	// 상세 게시글 출력 메서드
	public QuestBoardDTO boardDetail(int num);

	// 댓글 insert 메서드
	public void reply(QuestBoardDTO qdto);

	// 이미 댓글이 달렸는지 확인하는 메서드
	public int check(int ref);


}
