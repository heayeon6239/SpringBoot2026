package com.green;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.carproduct.CarProductDTO;
import com.green.carproduct.CarProductService;

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
	
	// 자동차 리스트를 JSON으로 변환하는 API
	@GetMapping("/cars")
	public List<CarProductDTO> getCarList(){
		System.out.println("ApiController getCarList() : 자동차 리스트 요청");
		// DB에서 데이터를 가져와서 그대로 리턴(Spring이 자동으로 JSON배열(List로 담았기 때문에 배열로)로 변환)
		// {no: 1, carName:~, ...} JSON 형태로
		return carproductservice.getAllCarProduct();
	}
}
