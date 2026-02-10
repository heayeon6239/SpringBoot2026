package com.green;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.carproduct.CarProductDTO;
import com.green.carproduct.CarProductService;

@Controller
public class HomeController {
	
	@Autowired
	CarProductService carproductservice;
	
	// http://localhost:8090 or http://localhost:8090/
	@GetMapping({"","/"})
	public String home(Model model) {
		// syso => log 찍는 용도, 반드시 필요
		System.out.println("HomeController 확인");
		
		List<CarProductDTO> carlist = carproductservice.getAllCarProduct();
		// 단, model은 한번 담아서 내보내면 다른 페이지로 이동할 때 데이터를 가지고 갈 수 없음
		model.addAttribute("carlist", carlist);
		
		return "home";
	}
}
