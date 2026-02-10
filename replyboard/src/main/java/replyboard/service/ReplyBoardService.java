package replyboard.service;

import java.util.List;

import replyboard.dto.ReplyBoardDTO;

public interface ReplyBoardService {
	// 각종 SQL을 위한 메서드 작성
	// 게시글 작성하여 추가하는 메서드
	public void insertReplyBoard(ReplyBoardDTO rdto);
	
	// 게시글 전체 목록 검색 메서드
	public List<ReplyBoardDTO> getAllReplyBoard();
	
	// 하나의 게시글을 리턴받는 메서드
	public ReplyBoardDTO getOneBoard(int num);
	
	// 답글 작성하여 추가하는 메서드
	public void reWriteInsert(ReplyBoardDTO rdto);
	
	// 답글 작성 시 부모글의 re_level보다 큰 값들을 모두 1씩 증가시키는 메서드
	// 예) ref : 1, re_step : 1, re_level : 1 => 원글
	// 원글에 답글을 달 경우 => ref : 1, re_step : 2, re_level : 2
	public void reSqUpdate(ReplyBoardDTO rdto);
	
	// 답글 추가시 reSqUpdate()메서드가 먼저 실행되도록 묶음으로 만든 메서드
	// reWriteInsert() + reSqlUpdate() 합쳐서 실행하는 메서드
	// 이유 : 답글은 추가되기 전에 기존의 ref, re_step, re_level의 값이 먼저 변경된 후 추가되어야하기 때문에,
	//       반드시 reSqUpdate() -> reWriteInsert() 순서로 실행되어야함
	public void replyProcess(ReplyBoardDTO rdto);
}
