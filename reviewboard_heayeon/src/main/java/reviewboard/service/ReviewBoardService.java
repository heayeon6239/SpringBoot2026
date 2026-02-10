package reviewboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reviewboard.dto.ReviewBoardDTO;
import reviewboard.mapper.ReviewBoardMapper;

@Service
public class ReviewBoardService {
	
	@Autowired
	ReviewBoardMapper reviewboardmapper;

	// 작성한 리뷰 DB에 저장하는 메서드
	public boolean addReview(ReviewBoardDTO rdto) {
		System.out.println("ReviewBoardService addReview() : 리뷰 DB에 저장 메서드");
		int result = reviewboardmapper.insertReview(rdto);
		// 리뷰 저장 성공
		if(result > 0) {
			System.out.println("리뷰 저장 성공");
			return true;
		}
		// 리뷰 저장 실패
		System.out.println("리뷰 저장 실패");
		return false;
	}

	// 전체 리뷰 리스트 출력하는 메서드
	public List<ReviewBoardDTO> allReviewList() {
		System.out.println("ReviewBoardService allReviewList() : 리뷰 전체 출력 메서드");
		List<ReviewBoardDTO> result = reviewboardmapper.selectAllReviewList();
		return result;
	}
	
	// 전체 평균 평점 구하는 메서드
	public double avgReview() {
		System.out.println("ReviewBoardService avgReview() : 전체 평점 메서드");
		double result = reviewboardmapper.avgReviewRating();
		return result;
	}

	// 해당 리뷰 상세보기 메서드
	public ReviewBoardDTO detailReview(int num) {
		System.out.println("ReviewBoardService detailReview() : 상세 리뷰 보기 메서드");
		// 상세보기 -> 조회수 증가 메서드 호출
		reviewboardmapper.updateViewCount(num);
		ReviewBoardDTO result = reviewboardmapper.selectDetailReview(num);
		return result;
	}

	// 해당 리뷰 수정 메서드
	public boolean reviewModi(ReviewBoardDTO rdto) {
		System.out.println("ReviewBoardService reviewModi() : 해당 리뷰 수정 메서드");
		int result = reviewboardmapper.updateReview(rdto);
		
		// 수정 성공
		if(result > 0) {
			System.out.println("수정 성공");
			return true;
		}
		// 수정 실패
		else {
			System.out.println("수정 실패");
			return false;
		}
		
	}

	// 해당 리뷰 삭제 메서드
	public boolean reviewDelete(int num) {
		System.out.println("ReviewBoardService reviewDelete() : 해당 리뷰 삭제 메서드");
		int result = reviewboardmapper.DeleteReview(num);
		// 삭제 성공
		if(result > 0) {
			System.out.println("삭제 성공");
			return true;
		}
		// 삭제 실패
		else {
			System.out.println("삭제 실패");
			return false;
		}
	}

	// 리뷰 개수 메서드
	public int reviewCnt() {
		System.out.println("ReviewBoardService reviewCnt() : 리뷰 개수 메서드");
		return reviewboardmapper.reviewBoardCnt();
	}

	// 한 페이지에 보여줄 리뷰 리스트만 자르는 메서드
	public List<ReviewBoardDTO> pagelist(int startRow, int endRow) {
		System.out.println("ReviewBoardService pagelist() : 한 페이지에 보여줄 리뷰 리스트만 자르는 메서드");
		return reviewboardmapper.selectPageList(startRow,endRow);
	}
	
}
