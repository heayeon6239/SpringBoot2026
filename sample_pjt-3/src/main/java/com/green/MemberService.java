package com.green;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service의 의미 : MemberServie 클래스는 비즈니스 로직을 작성하는 클래스
@Service
public class MemberService {
	
	// MemberDAO 클래스를 MemberService 클래스에서 사용하는 방법
	// -> DI(의존성 객체 주입)를 이용해 외부로부터 객체를 주입하여 사용(DI를 의미하는 @Autowired)
	@Autowired
	MemberDAO mdao;

	public void signUpConfirm(MemberDTO mdto) {
		System.out.println("회원가입 출력하는 이야!");
		mdao.insertMember(mdto);
		
	}

	public void signInConfirm(MemberDTO mdto) {
		System.out.println("로그인 정보 출력하는 이야!");
		// DAO에서 return한 값(맵에서 로그인한 id,pw와 동일한 데이터)의 주소가 loginMember에 들어감
		MemberDTO loginMember = mdao.selectMember(mdto); 
		
		// id와 pw 비교해서 같으면 로그인 성공, 아니면 로그인 실패
		
		// [Error] because "loginMember" is null => 로그인할 id,pw를 입력하지 않은채로 로그인을 실행할 때 발생하는 오류
		// 반드시 null을 예외로 처리(NullPointerException)
		if(loginMember != null && mdto.getPw().equals(loginMember.getPw())) {
			// mdto.getPw() : 입력해서 DTO에 들어간 pw
			// loginMember.getPw() : 맵에서 id를 key값으로 찾은 동일한 id의 데이터에서의 pw
			// => 이 두개를 비교
			System.out.println("id: "+loginMember.getId());
			System.out.println("pw: "+loginMember.getPw());
			System.out.println("로그인 성공");
		}else {
			System.out.println("로그인 실패");
		}
		
	}

	
}
