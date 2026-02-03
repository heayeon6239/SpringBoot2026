package com.green.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.board.BoardDTO;

@Mapper
public interface BoardMapper {
	
	// 게시글 추가 추상 메서드
	public void insertBoard(BoardDTO bdto);
	
	// 전체 게시글 출력 추상 메서드
	public List<BoardDTO> getAllBoard();
	
	// 해당 게시글 상세 정보 출력 추상 메서드
	public BoardDTO getOneBoard(int num);
	public int getOneBoard02(int num);
	
	// 해당 게시글 수정 추상 메서드
	public int updateBoard(BoardDTO bdto);
	
	// 해당 게시글 삭제 추상 메서드
	// 매개변수가 2개 이상인 경우 -> @Param("변수") 데이터 타입 필드명 이용해 작성
	public int deleteBoard(@Param("num") int num, @Param("writerPw") String writerPw);
	
	// 제목, 내용 검색 추상 메서드
	public List<BoardDTO> getSearchBoard(@Param("searchType") String searchType, @Param("SearchKeyword") String SearchKeyword);
	
	// 전체 게시글의 개수를 구하는 메서드
	public int getAllCount();
	
	// 전체 게시글의 시작 게시글 번호(startRow), 몇개의 행(pageSize)만큼 보는 메서드
	public List<BoardDTO> getPageList(@Param("startRow") int startRow, @Param("pageSize") int pageSize);
}
	
	
