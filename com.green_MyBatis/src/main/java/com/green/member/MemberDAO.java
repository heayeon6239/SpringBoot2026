package com.green.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.cj.protocol.Resultset;

@Repository // 데이터가 저장된 저장소
public class MemberDAO {
	
	// MySQL Driver 설치 및 JDBC 환경 설정 완료
	// 외부에서 DataSource를 DI로 삽입
	@Autowired
	private DataSource dataSource;

//	@Autowired
//	MemberDTO mdto;
	
	// 쿼리문 사용할 공간
	public int insertMember(MemberDTO mdto) {
		System.out.println("MemberDAO insertMember()");
		
		// 실무에서 쿼리문 작성시 대문자로 작성
		// NO, REG_DATE, MOD_DATE는 default 값이 존재하므로 추가하지 않아도 됨
		// 추가할 필드명이 정해져 있을 경우 반드시 (필드명1, 필드명2 ...) 필드명을 명시
		// INSERT INTO USER_MEMBER(id,pw,mail,phone) VALUES(?,?,?,?);
		String sql = "INSERT INTO user_member(id,pw,mail,phone) VALUES(?,?,?,?)";
		int result = 0;
		
		// DB는 네트워크를 통해 자료를 가져오므로 try ~ catch() 구문 이용
		try(
				// Connection 클래스를 이용해 dataSource를 getConnection()해야함
				// Connection은 연결하는 자원으로 사용하고 나면 반드시 반납해야함, close()를 해야함
				// try(connection~) => 자동 close()됨
				Connection conn = dataSource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// ?,?,?,? 값을 대응해주어야함
			// input에 입력 => mdto 가방에 담긴 상태
			// mdto라는 가방에서 필요한 자원을 getId()로 꺼내옴
			psmt.setString(1, mdto.getId());
			psmt.setString(2, mdto.getPw());
			psmt.setString(3, mdto.getMail());
			psmt.setString(4, mdto.getPhone());
			
			// insert, delete, update구문은 실행명령 : executeUpdate()
			result = psmt.executeUpdate();
			// executeUpdate() 메서드의 의미는 insert, delete, update 문을 실행하고나면
			// 실행 결과를 int 데이터 타입의 행의 개수로 반환한다는 의미
			// insert 1건 성공 => 반환값 : 1
			// insert 0건 중복체크 => 반환값 : 0
			// update 3건 수정 => 반환값 : 3
			// delete 5건 수정 => 반환값 : 5
			
			System.out.println("MemberDAO insertMember result값 "+result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 회원가입한 유저 모두 출력시키는 메서드 작성
	public List<MemberDTO> allSelectMember() {
		System.out.println("MemberDAO allSelectMember()");
		// 전체 목록 검색 sql
		String sql = "SELECT * FROM user_member";
		// List<E> 인터페이스이므로 => 구현할 수 없음
		// 고로 ArrayList<>를 이용하여 출력
		List<MemberDTO> list = new ArrayList<>();
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				// select 구문은 executeQuery()로 실행한 결과를 ResultSet 객체에 담음
				ResultSet rs = psmt.executeQuery();
				){
			
			// rs 결과값 
			// => no  id  pw  mail  phone  reg_date  mod_date
			//     1   1   1   1     1       2026~     2026~
			//     2   2   2   2     2       2026~     2026~
			// rs.next()는 다음행의 값이 존재하면 true, 아니면 false 반환
			// while문의 rs.next()는 먼저 한 행을 돌고, 다음행 ... 
			while(rs.next()) {
				MemberDTO mdto = new MemberDTO(); 
				// mdto가방을 rs의 결과값을 저장하는 용도
				mdto.setNo(rs.getInt("no"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setMail(rs.getString("mail"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setReg_date(rs.getString("reg_date"));
				mdto.setMod_date(rs.getString("mod_date"));
				
				// ArrayList에 추가
				list.add(mdto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public boolean isMember(String id) {
		System.out.println("MemberDAO isMember()");
		return false;
	}
	
	// ----------------------- 2026-01-27 추가 쿼리 작성 부분 ---------------------------
	
	// 개인 한 사람의 정보를 검색하는 메서드(단, 정보 수정시 비밀번호가 일치하는지 확인)
	public MemberDTO oneSelectMember(String id) {
		System.out.println("MemberDAO oneSelectMember()");
		// 01. 반환받을 MemberDTO 객체화
		MemberDTO mdto = new MemberDTO();
		// 02. sql 구문 작성
		String sql = "SELECT * FROM user_member WHERE id=?";
		// 03. 예외 처리 try(자동 close를 위해 Connection 설정) ~ catch()
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql); // 그냥 Statement 를 쓰면 필드명을 다 박아줘야함
				){
			
			// 실행문 작성은 여기서 (※ 항상 ? 대응을 먼저 작성)
			psmt.setString(1, id);
			
			// select문은 반드시 executeQuery()로 실행해서 ResultSet 객체에 담음 ★
			ResultSet rs = psmt.executeQuery();
			
			// mdto에 값 담기 (※ rs.next() 없이 값을 꺼내오면 항상 빈 DTO임을 주의)
			if(rs.next()) { // 값이 존재하는지 항상 물어봐야함 ★
				mdto.setNo(rs.getInt("no"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setMail(rs.getString("mail"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setReg_date(rs.getString("reg_date"));
				mdto.setMod_date(rs.getString("mod_date"));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return mdto;
		
	}
	
	// 개인 한사람의 정보를 수정하는 쿼리(단, 정보 수정시 비밀번호가 일치하는지 확인)
	public int updateMember(MemberDTO mdto) {
		System.out.println("MemberDAO updateMember()");
		int result=0;
		// 반환할 값 객체화
		//MemberDTO mdto = new MemberDTO();
		// sql문
		String sql = "UPDATE user_member SET mail=?, phone=? WHERE id=?";
		// 예외처리
		try(
				// 데이터랑 연결
				Connection conn = dataSource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문 작성
			// ? 대응
			psmt.setString(1, mdto.getMail());
			psmt.setString(2, mdto.getPhone());
			psmt.setString(3, mdto.getId());
			
			// executeUpdate()로 실행, 업데이트(수정)해서 result에 담기
			result = psmt.executeUpdate();
			System.out.println("update 결과값"+result);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	// 개인 한사람의 비밀번호 리턴하는 쿼리
	public String getPass(String id) {
		System.out.println("MemberDAO getPass()");
		String pass="";
		String sql = "SELECT pw FROM user_member WHERE id=?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문
			// ? 대응
			psmt.setString(1, id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				pass = rs.getString(1); // 비밀번호 값에 저장된 매핑인덱스(= getString("pw"))
			}
			System.out.println("getPass 값: "+pass);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pass;
	}
	
	// 한사람 개인의 정보를 삭제하는 메서드 작성
	public int deleteMember(String id) {
		System.out.println("MemberDAO deleteMember()");
		int result = 0;
		
		String sql = "DELETE FROM user_member WHERE id=?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
			
			// 실행문
			// ? 대응
			psmt.setString(1, id);
			// executeUpdate(): delete, insert, update
			// executeQuery() : select
			result = psmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	// --------------------- 2026-01-29 ------------------------
	
	// 로그인 메서드
//	public MemberDTO loginMember(String id, String pw) {
//		System.out.println("MemberDAO loginMember()");
//		String sql = "SELECT * FROM user_member WHERE id=? AND pw=?";
//		int result = 0;
//		MemberDTO mdto = new MemberDTO();
//		
//		try(
//				Connection conn = dataSource.getConnection();
//				PreparedStatement psmt = conn.prepareStatement(sql);
//				){
//			
//			// 실행문
//			psmt.setString(1, id);
//			psmt.setString(2, pw);
//			
//			// 실행
//			ResultSet rs = psmt.executeQuery();
//			
//			while(rs.next()) {
//				mdto.setId(rs.getString("id"));
//				mdto.setPw(rs.getString("pw"));
//				
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return mdto;
//	}
	

	
}
