package com.green.carproduct.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.carproduct.CarProductDTO;

@Mapper
public interface CarProductMapper {
	
	// 전체 자동차 출력
	public List<CarProductDTO> getAllCarProduct();
}
