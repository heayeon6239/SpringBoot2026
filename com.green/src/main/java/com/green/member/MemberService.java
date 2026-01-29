package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	// PasswordEncoder 객체도 DI(의존객체) 정의
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// - 회원가입이 제대로 되었는지, 혹은 실패했는지 예외처리
	public int signupConfirm(MemberDTO mdto) { // 자동 생성
		System.out.println("MemberService signupConfirm()메서드 확인");
		
		// (1) 회원가입 중복체크
		boolean isMember = memberdao.isMember(mdto.getId());
		// 회원가입 중복체크 통과했다면
		if(isMember == false) {
			
			// 문자인 pw를 암호화된 비밀번호로 변환해주는 코드(passwordEncoder.encode(null)안에 암호화하고자하는 필드명 입력)
			// encode(암호화) : 인간언어 -> 기계어
			// decode(복호화) : 기계어 -> 인간언어
			String encodepw = passwordEncoder.encode(mdto.getPw());
			
			// 암호화된 encodepw를 mdto로 넣어서 mdto.getPw() => 암호화된 코드로 수정
			mdto.setPw(encodepw);
			
			// 중복된 아이디가 존재하지 않을 때 DB에 삽입
			// DB에 회원정보가 추가되는 부분 => 암호화 되어야함
			int result = memberdao.insertMember(mdto); // 수정된 값 들어감
			
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
	
	// ----------------------- 2026-01-27 서비스로직 작성 부분 ---------------------------
	
	// 해당 id의 정보를 받아오는 메서드
	public MemberDTO oneSelect(String id) {
		System.out.println("MemberService oneSelect()");
		return memberdao.oneSelectMember(id);
	}
	
//	public int oneModify(MemberDTO mdto) {
//		System.out.println("MemberService oneModify()");
//		String password = memberdao.getPass(mdto.getId()); // DB에서의 해당 id의 비밀번호 값
//		// 비밀번호가 일치하면 수정
//		if(password.equals(mdto.getId()) && password != null) { // DB의 비밀번호가 입력한 비밀번호 값 비교
//			return memberdao.updateMember(mdto);
//		}else {
//			return -1; // 실패
//		}
//		
//	}
	
	// 개인 1명의 비밀번호만 출력하는 메서드
	public String onePass(String id) {
		// void가 아닌 이상 데이터 타입이 존재하면 반드시 return 필요
		return memberdao.getPass(id);
	}
	
	// 개인 1명의 정보를 수정하는 메서드(DB의 비밀번호와 일치하는지 비교)
	public boolean modifyMember(MemberDTO mdto) {
		System.out.println("MemberService modifyMember()");
		
		// DB조회
		String dbPass = memberdao.getPass(mdto.getId());
		System.out.println("dbPass: "+dbPass);
		System.out.println("광민확인"+mdto.getPw());
		// if로 비교
		if(dbPass != null && dbPass.equals(mdto.getPw())) {
			// 내가 입력한 DB의 패스워드가 존재하면
			return memberdao.updateMember(mdto) == 1; // 1 이면 업데이트됨, 1 == 1 -> true !
		}else {
			// 내가 입력한 DB의 패스워드가 존재하지 않으면
			return false;
		}
	}
	
	// 개인 1명의 정보를 삭제하는 메서드
	public boolean oneDelete(String id) {
		System.out.println("MemberService oneDelete()");
		return memberdao.deleteMember(id) == 1; // 값이 일치하면 true
	}
	
	// --------------------- 2026-01-29 ------------------------
	
	// 로그인 메서드(암호화된 DB를 복호화하여 로그인하는 메서드)
	public MemberDTO logincConfirm(MemberDTO mdto) {
		System.out.println("MemberService login()");
		
		// 01. DB에서 꺼낸 해당 로그인 정보 가져오기
		MemberDTO result = memberdao.oneSelectMember(mdto.getId());
		
		// 02. 꺼내온 정보와 입력한 정보가 일치하는지 확인
		// 암호화된 데이터 -> PasswordEncoder.matches(사용자가 입력한 구문, DB에 저장된 암호문)
		
		if(result != null && result.getPw() != null) {
			// 복호화 시켜서 비교(pw를 입력하면 암호화하여 mdto에 넣어 DB에 저장했기 때문에?, 값을 가져올 때도 mdto를 통해 가져옴)
			if(passwordEncoder.matches(mdto.getPw(), result.getPw())) {
				// 로그인 성공
				return result;
			}

		}
		return null;
	}
	
	
}
