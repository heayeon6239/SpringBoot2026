package com.green.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.member.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper membermapper;
	@Autowired
	PasswordEncoder passwordEncoder;

	// 회원가입 메서드
	public boolean signup(MemberDTO mdto) {
		System.out.println("MemberService signup()");
		
		String encodepw = passwordEncoder.encode(mdto.getPw()); // 입력한 비밀번호 암호화
		mdto.setPw(encodepw); // 암호화된 비밀번호로 다시 mdto 수정해줌
		int result = membermapper.insertMember(mdto); // 수정된 mdto로 DB에 들어감
		
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}

	// 로그인 메서드
	public MemberDTO login(MemberDTO mdto) {
		System.out.println("MemberService login()");
		
		MemberDTO user = membermapper.selectlogin(mdto);
		if(passwordEncoder.matches(mdto.getPw(), user.getPw())) {
			return user;
		}
		
		return null;
	}

}
