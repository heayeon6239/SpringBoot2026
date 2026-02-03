package com.green.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.board.BoardDTO;

@Mapper
public interface BoardMapper {
	
	// 작성한 게시글 추가 추상 메서드
	public int addBoardPost(BoardDTO bdto);
	
	// 전체 게시글 출력 추상 메서드
	public List<BoardDTO> PrintAllBoard();
	
	// 해당 id 게시글 추상 메서드
	public BoardDTO oneboard(int id);
	
	// 해당 게시글 수정 추상 메서드
	public int oneboardmodify(BoardDTO bdto);
	
	// 해당 게시글 삭제 추상 메서드
	public int remove(int id);
	
	// 게시글 검색 추상 메서드
	public List<BoardDTO> searchBoard(
			@Param("searchType") String searchType, 
			@Param("findKeyword") String findKeyword
			);
	
	// ----------------------- 2026-02-03 --------------------------
	
	// 전체 게시글의 개수를 구하는 메서드
	public int getAllCount();
	
	// 전체 게시글의 시작 게시글번호(startRow), 몇개의 게시글(onePageSize)만큼 출력하는 메서드
	public List<BoardDTO> getPageList(
			@Param("startRow") int startRow,
			@Param("onePageSize") int onePageSize
			);
}