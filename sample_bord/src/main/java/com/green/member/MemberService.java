package com.green.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.member.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper membermapper;

	public boolean signup(MemberDTO mdto) {
		int result = membermapper.insertMember(mdto);
		return false;
	}

}
