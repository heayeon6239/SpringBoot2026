package com.green;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.carproduct.CarProductDTO;
import com.green.carproduct.CarProductService;
import com.green.member.MemberDTO;
import com.green.member.MemberService;

import jakarta.servlet.http.HttpSession;

// @RestController는 @Controller + @ResponseBody를 합친 어노테이션 
// => 컨트롤러 역할 + 데이터를 JSON으로 응답하여 사용 ★★★★★

// @ResponseBody(원래는 이걸 전송할때마다 써줘야했는데 @RestController로 대체됨)
// => 메서드가 변환하는 데이터를 HTML뷰를 찾는 용도가 아닌, 데이터 그 자체(JSON)로 응답 받아 직접 쓰겠다는 의미

// @RestController 하나만 맨 위에 작성하면 모든 메서드들은 @ResponseBody를 작성하지 않아도 됨

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CarProductService carproductservice; // carList메서드
	@Autowired
	MemberService memberservice; 
	
	// 자동차 리스트를 JSON으로 변환하는 API
	@GetMapping("/cars")
	public List<CarProductDTO> getCarList(){
		System.out.println("ApiController getCarList() : 자동차 리스트 요청");
		// DB에서 데이터를 가져와서 그대로 리턴(Spring이 자동으로 JSON배열(List로 담았기 때문에 배열로)로 변환)
		// {no: 1, carName:~, ...} JSON 형태로
		return carproductservice.getAllCarProduct();
	}
	
	// 회원가입 API(POST방식)
	// ★ @RequestBody : react에서 보낸 JSON형태를 자바 객체(DTO)로 담을 수 있도록 형태를 변환
	//                  (react에서 값을 받아 insert하는 경우엔 @RequestBody 필수!)
	@PostMapping("/member/signup")
	public int signup(@RequestBody MemberDTO mdto) {
		System.out.println("ApiController signup() : 회원 insert 요청");
		return memberservice.signupConfirm(mdto);
	}
	
	// 로그인 메서드
	@PostMapping("/member/login")
	public MemberDTO login(@RequestBody MemberDTO mdto, HttpSession session) {
		// 서버 session에 담기는걸 볼 수 없음 그래서 보안에 좋음
		System.out.println("ApiController signup() : 회원 insert 요청");
		
		// loginUser = {no:~, id: ~, pw:~, mail:~ ...}
		MemberDTO loginUser = memberservice.logincConfirm(mdto);
		
		// null처리 필수!!
		// 로그인 성공
		if(loginUser != null) {
			session.setAttribute("loginUser", loginUser.getId());
		}
		return loginUser;
	}
	
	// 로그아웃 메서드
	@GetMapping("/member/logout")
	public int logout(HttpSession session) {
		session.invalidate(); // 세션 삭제
		return 1; // 성공
	}
	
	// 개인의 정보를 조회하는 메서드
	@GetMapping("/member/myinfo")
	public MemberDTO myinfo(HttpSession session) {
		System.out.println("ApiController myinfo() : 회원 정보 가져오는 메서드");
		// 세션에서 로그인 한 아이디 꺼내기
		// 세션은 object라서 String으로 다운캐스팅 해야함
		String loginId = (String) session.getAttribute("loginUser"); 
		if(loginId == null) {
			// 로그인이 안되면
			return null;
		}else {
			return memberservice.oneSelect(loginId);
		}
		
	}
	
	// 개인의 정보를 삭제하는 컨트롤러
	// 삭제하다 => @DeleteMapping()
	@DeleteMapping("/member/delete")
	public int delete(HttpSession session) {
		System.out.println("ApiController delete() : 회원 삭제 메서드");
		String loginId = (String) session.getAttribute("loginUser"); 
		if(loginId == null) {
			return 0;
		}
		
		// 삭제 서비스 메서드 (삭제 성공: 1(true), 삭제 실패 : 2(false))
		boolean result = memberservice.oneDelete(loginId);
		if(result) {
			// 로그아웃
			session.invalidate(); // 세션 삭제
			return 1;
		}else {
			return 0;
		}
	}
}
