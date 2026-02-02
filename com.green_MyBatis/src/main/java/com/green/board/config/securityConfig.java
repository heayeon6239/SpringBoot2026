package com.green.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// spring security 부품을 설정 -> config 패키지 생성 -> SecurityConfig 클래스 생성

@Configuration // 이 클래스는 환경설정하는 부분임을 알려주는 어노테이션
@EnableWebSecurity // 우리가 지정한 암호화를 웹어플리케이션 적용하겠다는 어노테이션
public class securityConfig {
	
	@Bean // IOC 스프링컨테이너에 Bean 객체로 등록(암호화 클래스를 다운받았는데 객체화되지않은 상태임으로, Bean을 통해 객체화하여 사용)
	      // 안하면 DI 의존객체로 사용할 수 없음!
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 127986 -> gklspesg(문자열 암호화)
	}
	
	// 기본적으로 동작하는 기능을 꺼야하기에 모두 disable() 비활성화함
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
	http
	.cors(cors-> cors.disable())
	.csrf(csrf-> csrf.disable());
	
	http
	.formLogin(login-> login.disable());
	
	return http.build();
	
	}

}
