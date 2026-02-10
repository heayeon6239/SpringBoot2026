package com.green.carproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.carproduct.mapper.CarProductMapper;

@Service
public class CarProductService {
	
	// 의존객체 삽입안하면 carproduct-mapper.xml의 SQL문 사용할 수 없음
	@Autowired
	CarProductMapper carproductmapper;
	
	// 전체 자동차 출력
	public List<CarProductDTO> getAllCarProduct(){
		System.out.println("CarProductService getAllCarProduct()");
		return carproductmapper.getAllCarProduct();
	}
}
