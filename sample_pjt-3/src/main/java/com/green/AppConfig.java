package com.green;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 비즈니스 클래스에 @Service가 존재하면 AppConfig는 필요없음 !!

// AppConfig 클래스 : 환경을 설정해주는 클래스 (환경설정 클래스)
// 여기서 객체생성해
@Configuration
public class AppConfig {
	// MemberService 를 Bean 객체로 생성
//	@Bean
//	public MemberService memberService() {
//		// IOC 컨테이너에 MemberService 클래스를 생성
//		return new MemberService();
//	}
	
}
