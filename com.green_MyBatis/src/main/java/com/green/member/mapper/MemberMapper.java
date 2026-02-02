package com.green.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.member.MemberDTO;

@Mapper // MemberMapper는 매퍼(SQL(xml파일)과의 연결)역할
public interface MemberMapper {
	// MemberDAO의 메서드들을 추상메서드로 작성
	// 설정된 메서드들은 IOC컨테이너에 탑재
	
	// 회원가입 추가 추상 메서드
	public int insertMember(MemberDTO mdto);
	
	// 회원 전체 출력 추상 메서드
	public List<MemberDTO> allSelectMember();
	
	// 이미 가입한 회원인지 확인하는 추상 메서드
	public boolean isMember(String id);
	
	// 한명의 정보를 검색하는 추상 메서드
	public MemberDTO oneSelectMember(String id);
	
	// 한명의 정보를 수정하는 추상 메서드
	public int updateMember(MemberDTO mdto);
	
	// 한명의 비밀번호를 반환하는 추상 메서드
	public String getPass(String id);
	
	// 한명의 정보를 삭제하는 추상 메서드
	public int deleteMember(String id);
}
