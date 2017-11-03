package com.han.service;

import java.util.List;

import com.han.vo.Lte_gps;
import com.han.vo.Lte_lora;

public interface LteService {

	public List<Lte_lora> findAll();

	public List<Lte_lora> findPage(int page,int size);
	
	public int addTestData(List<Lte_lora> loras,List<Lte_gps> gpses);
	
	public int findMaxPage(int size);
	
	public Lte_gps findGpsByCarId(int car_id);

}
