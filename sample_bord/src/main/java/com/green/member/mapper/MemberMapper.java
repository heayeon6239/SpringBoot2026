package com.green.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.green.member.MemberDTO;

@Mapper
public interface MemberMapper {

	// 회원가입 추가 추상 메서드
	public int insertMember(MemberDTO mdto);
	
}
