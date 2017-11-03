package com.han.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.han.service.LteService;
import com.han.vo.Lte_gps;

@Controller
public class GpsController {

	@Autowired
	private LteService lteService;
	
	@ResponseBody
	@RequestMapping("/findGps")
	public Lte_gps findGps(int car_id){
		Lte_gps lg = lteService.findGpsByCarId(car_id);
		if(lg == null){
			return new Lte_gps();
		}
		return lg;
	}

	public LteService getLteService() {
		return lteService;
	}

	public void setLteService(LteService lteService) {
		this.lteService = lteService;
	}
	
	
}
