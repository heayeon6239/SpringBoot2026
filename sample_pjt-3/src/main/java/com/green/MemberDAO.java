package com.green;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

// @Repository : 데이터 저장소 DAO(sql,쿼리문의 집합장소 => 데이터를 직접 처리하는 객체)라는 의미
@Repository 
public class MemberDAO {
	
	// 원래 DB 커넥션이 존재해야 하지만, 현재 DB가 존재하지 않으므로 HashMap<>을 이용해 DB처럼 사용
	private Map<String, MemberDTO> memberDB = new HashMap<>();
	
	// insertMember 메서드
	public void insertMember(MemberDTO mdto) {
		// 원래는 Select, Delete, Insert 문이 들어감
		System.out.println("회원가입을 추가하는 메서드");
		memberDB.put(mdto.getId(),mdto);
		printMember();
	}
	
	// 회원정보 출력 메서드
	public void printMember() {
		for(String key : memberDB.keySet()) {
			MemberDTO mdto = memberDB.get(key);
			System.out.println("id: "+mdto.getId());
			System.out.println("pw: "+mdto.getPw());
		}
	}
	
	// selectMember 메서드
	public MemberDTO selectMember(MemberDTO mdto) {
		System.out.println("로그인 정보를 확인하는 메서드");
		
		MemberDTO loginMember = memberDB.get(mdto.getId()); 
		// 로그인한 id를 key값으로 넣어서 해당 key에 해당하는 value 값을 데이터타입이(MemberDTO)인 loginMember에 값을 담음
		// loginMember => kkk(key) : "kkk","123","kkk@naver.com"(value)
		
		return loginMember; // 맵에서 찾은 데이터의 주소를 반환
	}
}
