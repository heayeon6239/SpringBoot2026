package com.green;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdressController {
	
	// heap 메모리에 주소 데이터를 담을 리스트 필요
	// ArrayList<E> / List가 부모(업캐스팅)
	private List<AdressDTO> addressList = new ArrayList<>();
	
	// 01. 주소록 목록 화면
	@GetMapping("/address")
	public String list(Model model) {
		model.addAttribute("list", addressList); // list에 담음
		return "address-list";
	}
	
	// 02. 주소 등록화면(화면이 존재X)
	@PostMapping("/add-address")
	public String addr(AdressDTO adto) {
		// ArrayList 삽입 (.add(value))
		addressList.add(adto);
		// 현재 url은 add-address인데 => address로 이동
		return "redirect:/address";
	}
}
