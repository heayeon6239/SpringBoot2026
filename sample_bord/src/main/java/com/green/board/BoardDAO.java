package com.green.board;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	@Autowired
	DataSource datasource;

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

}
