package com.green.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	@Autowired
	DataSource datasource;

	// 작성한 게시글을 추가하는 메서드
	public int addBoardPost(BoardDTO bdto) {
		System.out.println("BoardDAO addBoardPost()");
		int result=0;
		
		String sql = "INSERT INTO board(title,content,writer) VALUE(?,?,?)";
		
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문
			// ? 대응
			psmt.setString(1, bdto.getTitle());
			psmt.setString(2, bdto.getContent());
			psmt.setString(3, bdto.getWriter());
			
			// 실행 명령
			result = psmt.executeUpdate();
			System.out.println("result : "+ result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 전체 게시글을 출력하는 메서드
	public List<BoardDTO> PrintAllBoard(){
		System.out.println("BoardDAO PrintAllBoard()");
		// DB값을 넣을 배열 생성
		List<BoardDTO> boardList = new ArrayList<>();
		// sql문 작성
		String sql = "SELECT * FROM board ";
		
		// DB데이터 가져오기
		try(
				Connection conn = datasource.getConnection(); // 주입시킨 datasource의 getConnection()메서드로 DB연결
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문 작성
			// 실행(select -> executeQuery()로 모든 데이터를 ResultSet에 담음 ★
			ResultSet rs = psmt.executeQuery();
			
			// 담긴 모든 데이터를 배열 boardList에 삽입
			while(rs.next()) { // -> 다음 데이터가 없을 때까지
				BoardDTO bdto = new BoardDTO(); // 새로운 bdto 를 반복해서 생성 후, 담아줌
				
				bdto.setId(rs.getInt("id")); // 새로 생성한 bdto의 id를 (rs에 담긴 DB데이터에서 Int기본데이터타입의("id")필드명을 가진 값으로) 수정 
				bdto.setTitle(rs.getString("title"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setContent(rs.getString("content"));
				bdto.setCreateAt(rs.getString("createAt"));
				
				boardList.add(bdto); 
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return boardList; // DB의 모든 데이터를 담은 배열을 반환
	}

	// 해당 id의 게시글 데이터 가져오는 메서드
	public BoardDTO oneboard(int id) {
		System.out.println("BoardDAO oneboard()");
		String sql = "SELECT * FROM board WHERE id=?";
		// 해당 id의 실행 결과값 bdto에 담기
		BoardDTO bdto = new BoardDTO();
		
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행 구문
			// ? 대응
			psmt.setInt(1, id);
			// 실행(ResultSet에 담기)
			ResultSet rs = psmt.executeQuery();
			
			if(rs.next()) { // 값이 한개더라도 next()안하면 null ★★★★★
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setCreateAt(rs.getString("createAt"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setContent(rs.getString("content"));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("result; "+bdto.getTitle());
		return bdto;
		
	}
	
	// 해당 게시글을 수정하는 메서드
	public int oneboardmodify(BoardDTO bdto) {
		System.out.println("BoardDAO oneboardmodify() 게시글 수정 메서드");
		String sql = "UPDATE board SET title=?, writer=?, content=? WHERE id=?";
		int result = 0;
		
		// 데이터 불러오기
		try(
				Connection conn = datasource.getConnection(); // 데이터 연결
				PreparedStatement psmt = conn.prepareStatement(sql); // 연결된 데이터에 sql문 담기
				){
			
			// 실행문
			// ?대응
			psmt.setString(1, bdto.getTitle());
			psmt.setString(2, bdto.getWriter());
			psmt.setString(3, bdto.getContent());
			psmt.setInt(4, bdto.getId());
			
			// 실행(update는 executeUpdate(), 성공하면 = 1(수정된 개수))
			result = psmt.executeUpdate();
			System.out.println("result값: "+result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// 삭제 메서드
	public int remove(int id) {
		System.out.println("BoardDAO remove()");
		String sql = "DELETE FROM board WHERE id=?";
		int result = 0;
		
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문
			// ? 대응
			psmt.setInt(1, id);
			
			// 실행
			result = psmt.executeUpdate();
			System.out.println(result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// ----------------------- 2026-01-29 --------------------------
	
	// 게시글 검색 메서드
	public List<BoardDTO> searchBoard(String searchType, String findKeyword){
		System.out.println("BoardDAO searchBoard()");
		
		List<BoardDTO> findlist = new ArrayList<>();
		String sql = "";
		
		// 날짜값이 비어있으면
//		if(writeDate.trim().isEmpty()) {
			// 서치타입이 제목
			if(searchType.equals("title")) {
				sql = "SELECT * FROM board WHERE title LIKE ? ORDER BY id DESC";
			}
			// 서치타입이 내용
			else {
				sql = "SELECT * FROM board WHERE content LIKE ? ORDER BY id DESC";
			}
//		}
//		// 날짜값이 존재하면
//		else {
//			// 서치타입이 제목
//			if(searchType.equals("title")) {
//				sql = "SELECT * FROM board WHERE title LIKE ? ORDER BY id DESC";
//			}
//			// 서치타입이 내용
//			else {
//				sql = "SELECT * FROM board WHERE content LIKE ? ORDER BY id DESC";
//			}
//		}
		
		
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문
			psmt.setString(1, "%"+findKeyword+"%");
			
			// 실행
			ResultSet rs = psmt.executeQuery();
			
			// List에 담기
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setContent(rs.getString("content"));
				bdto.setCreateAt(rs.getString("createAt"));
				
				findlist.add(bdto); 
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return findlist;
	}	

}
