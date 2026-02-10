package reviewboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import reviewboard.dto.ReviewBoardDTO;

@Mapper // sql문을 작성할 인터페이스
public interface ReviewBoardMapper {

	// 리뷰 저장하는 메서드
	public int insertReview(ReviewBoardDTO rdto);

	// 전체 리뷰 리스트 출력 메서드
	public List<ReviewBoardDTO> selectAllReviewList();

	// 전체 평균 평점 구하는 메서드
	public double avgReviewRating();

	// 해당 상세 리뷰 보는 메서드
	public ReviewBoardDTO selectDetailReview(int num);
	
	// 해당 상세 리뷰 조회수 증가 메서드
	public void updateViewCount(int num);

	// 해당 리뷰 수정 메서드
	public int updateReview(ReviewBoardDTO rdto);

	// 해당 리뷰 삭제 메서드
	public int DeleteReview(int num);

	// 리뷰 개수 메서드
	public int reviewBoardCnt();

	// 한 페이지에 보여줄 리뷰 리스트만 자르는 메서드
	public List<ReviewBoardDTO> selectPageList(
			@Param("startRow") int startRow, 
			@Param("endRow") int endRow
			);
	
	
}
