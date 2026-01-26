package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// controller -> service : DAO 메서드 찾았니
// DAO야 메서드 있음 -> DB에서 찾아옴
// DB -> id,pw값 들고 -> DAO로 보냄 -> DAO는 service의 메서드로 보냄 -> service는 controller한테 보냄
@Service
public class MemberService {

	// - id 중복 체크, 성공, 실패 상수변수 정의
	// (1) 회원가입의 중복을 확인하는 상수
	public final static int user_id_already_exit = 0;
	// (2) 회원가입의 성공여부를 확인하는 상수
	public final static int user_id_success = 1;
	// (3) 회원가입의 실패를 확인하는 상수
	public final static int user_id_fail = -1;
	
	// MemberDAO도 DI를 정의
	@Autowired
	MemberDAO memberdao;
	
	// - 회원가입이 제대로 되었는지, 혹은 실패했는지 예외처리
	public int signupConfirm(MemberDTO mdto) { // 자동 생성
		System.out.println("MemberService signupConfirm()메서드 확인");
		
		// (1) 회원가입 중복체크
		boolean isMember = memberdao.isMember(mdto.getId());
		// 회원가입 중복체크 통과했다면
		if(isMember == false) {
			// 중복된 아이디가 존재하지 않을 때 DB에 삽입
			int result = memberdao.insertMember(mdto); 
			if(result > 0) {
				return user_id_success; // result = 1(회원가입 성공)
			}else {
				return user_id_fail; // result = -1(회원가입 실패)
			}
		}
		// 중복된 아이디가 존재할 때
		else {
			return user_id_already_exit; // result = 0(아이디 중복)
		}
	}
	
	// - 회원 전체 목록 출력하는 메서드
	public List<MemberDTO> allListMember() {
		System.out.println("MemberService printAllUser()");
		return memberdao.allSelectMember();
		
	}
	
}
