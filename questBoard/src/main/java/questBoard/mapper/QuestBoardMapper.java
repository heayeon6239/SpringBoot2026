package questBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import questBoard.dto.QuestBoardDTO;


@Mapper
public interface QuestBoardMapper {

	// 전체 출력 메서드
	public List<QuestBoardDTO> getAllList();

	// 작성 처리 메서드
	public void insertWriteBoard(QuestBoardDTO qdto);

}
