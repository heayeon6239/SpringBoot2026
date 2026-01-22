package com.green;

// DTO(Data Transfer Object)는 데이터 전송객체 : DTO라는 가방에 입력한 값을 담아서 이동
public class MemberDTO {
	
	// 멤버변수는 접근제어자 private 이용 
	// (private 은 자기 자신 클래스만 접근 가능, 반드시 getter/setter)
	private String id;
	private String pw;
	private String email;
	
	// getter/setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
