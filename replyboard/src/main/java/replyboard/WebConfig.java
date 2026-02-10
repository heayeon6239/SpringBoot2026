package replyboard;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이 클래스는 스프링부트의 설정파일임을 알려주는 어노테이션
public class WebConfig implements WebMvcConfigurer{
	
	// addResourceHandlers : 정적 리소스(HTML, CSS, JS 등)을 관리하는 메서드
	// 외부의 물리적인 경로를 웹에서 사용하는 URL주소로 매핑(연결)하는 설정을 담당
	
	// file:///c:/upload/ => 실제로 파일이 저장되는 물리적인 경로
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**")
		        .addResourceLocations("file:///c:/upload/");
	}
}
