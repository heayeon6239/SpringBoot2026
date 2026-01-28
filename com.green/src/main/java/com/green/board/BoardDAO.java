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

	// application.properties에 설정된 환경의 My SQL과 연결한다는 의미(my batis? 라면 또 다름)
	@Autowired
	private DataSource datasource;

	// 쿼리문 작성시 KeyPoint
	// (1) 한 사람, 하나의 자료를 insert, select할때는 => DTO객체에 담아 사용
	// (2) 전체목록, 전체회원... 여러개의 자료를 insert, select할때는 => List<E> list = new
	// ArrayList<E> 업캐스팅
	// (3) 필드명하나 select 할때는 => String, int, boolean
	// (4) 메서드 작성시, void -> return X / 데이터 타입이 존재 -> 반환값 return O
	// (5) try(){}catch(){} 사용

	// 게시글 작성하여 추가하는 메서드(쿼리문)
	public void insertBoard(BoardDTO bdto) {

		// 추가하는 쿼리문 insert into 테이블명 values()
		String sql = "INSERT INTO board(writer,subject,writerPw,content) VALUES(?,?,?,?)";

		try (
				// datasource(주입한 데이터베이스 원본의 자료)들을 연결
				Connection conn = datasource.getConnection();
				// 주입한 데이터베이스를 꺼내서 conn에 담기
				// (conn = (url, username, userPassword)
				// (conn = (localhost:3306, "root", "12345678")
				PreparedStatement psmt = conn.prepareStatement(sql);) {

			// 실행문 작성
			// ? 대응
			psmt.setString(1, bdto.getWriter());
			psmt.setString(2, bdto.getSubject());
			psmt.setString(3, bdto.getWriterPw());
			psmt.setString(4, bdto.getContent());

			// sql문 실행(※ select문 -> 반드시 ResultSet에 담아야함)
			psmt.executeUpdate();
			System.out.println(bdto.getWriterPw());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 전체 출력 메서드
	public List<BoardDTO> getAllBoard() {
		System.out.println("BoardDAO getAllBoard()");
		List<BoardDTO> boardlist = new ArrayList<>();
		// sql
		String sql = "SELECT * FROM board ORDER BY num DESC";

		try (
				Connection conn = datasource.getConnection(); 
				PreparedStatement psmt = conn.prepareStatement(sql);) {

			// 실행
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				// BoardDTO 인스턴스 생성하여 데이터 값 담아줌
				BoardDTO bdto = new BoardDTO();
				bdto.setNum(rs.getInt("num")); // 1번째 필드명 = rs.getInt(1)
				bdto.setWriter(rs.getString("writer"));
				bdto.setWriterPw(rs.getString("writerPw"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setReg_date(rs.getString("reg_date"));
				bdto.setContent(rs.getString("content"));
				bdto.setReadcount(rs.getInt("readcount"));

				boardlist.add(bdto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardlist;
	}

	// 하나의 게시글 상세 정보
	public BoardDTO getOneBoard(int num) {
		System.out.println("BoardDAO getAllBoard()");
		BoardDTO bdto = new BoardDTO();
		// readcount 클릭할 때마다 누적하는 sql문
		String sql = "UPDATE board SET readcount=readcount+1 WHERE NUM=?";
		String sql2 = "SELECT * FROM board WHERE num=?";
		
		// Connection 연결용 try~catch()구문
		// (1)조회수 증가 sql try~catch()구문 & (2)해당 게시글 정보 가져오는 sql2 try~catch 구문
		try(Connection conn = datasource.getConnection();){
				// 조회수 증가
				try(PreparedStatement psmt = conn.prepareStatement(sql)){
					// ?대응
					psmt.setInt(1, num);
					psmt.executeUpdate();
				}
				// 하나의 게시글 정보
				try(PreparedStatement psmt = conn.prepareStatement(sql2)){
					// ?대응
					psmt.setInt(1, num);
					ResultSet rs = psmt.executeQuery();
					
					if(rs.next()) {
						// BoardDTO 인스턴스 생성하여 데이터 값 담아줌
						
						bdto.setNum(rs.getInt("num")); // 1번째 필드명 = rs.getInt(1)
						bdto.setWriter(rs.getString("writer"));
						bdto.setWriterPw(rs.getString("writerPw"));
						bdto.setSubject(rs.getString("subject"));
						bdto.setReg_date(rs.getString("reg_date"));
						bdto.setContent(rs.getString("content"));
						bdto.setReadcount(rs.getInt("readcount"));
					}
				}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bdto;
	}
	
	// 해당 게시글을 수정하는 메서드
	public int updateBoard(BoardDTO bdto) {
		System.out.println("BoardDAO updateBoard()");
		int result = 0;
		// 수정할 때 반드시 번호와 비밀번호가 일치해야만 수정 가능 쿼리
		String sql = "UPDATE board SET subject=?, content=? WHERE num=? AND writerPw=?";
		
		try(
				Connection conn = datasource.getConnection(); 
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문
			psmt.setString(1, bdto.getSubject());
			psmt.setString(2, bdto.getContent());
			psmt.setInt(3, bdto.getNum());
			psmt.setString(4, bdto.getWriterPw());
			
			// 실행
			result = psmt.executeUpdate();
			System.out.println("result: "+result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
