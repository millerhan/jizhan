package com.han.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.han.service.LteService;
import com.han.util.StringUtil;
import com.han.vo.Lte_gps;
import com.han.vo.Lte_lora;

@Controller
public class LoraController {

	@Autowired
	private LteService lteService;
	
	@RequestMapping("/index")
	public String index(Model model,String page){
		
		int p = 1;
		try{
			p = Integer.parseInt(page);
		}catch(Exception e){
		}
		int maxPage = lteService.findMaxPage(10);
		p = p > maxPage ? maxPage : p;
		p = p < 1 ? 1 : p;
		List<Lte_lora> loras = lteService.findPage(p, 10);

		int maxLength = 0;
		for (Lte_lora lte_lora : loras) {
			int length = lte_lora.getLora_id().length();
			maxLength = maxLength < length ? length : maxLength;
		}
		int columnSize = maxLength%12==0?maxLength/12:maxLength/12+1;
		model.addAttribute("columnSize",columnSize);
		model.addAttribute("loras", loras);
		int start = p - 3 < 1 ? 1 : p - 3;
		int end = p+3 > maxPage? maxPage : p + 3;
		
		List<Integer> pages = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			pages.add(i);
		}
		model.addAttribute("currentPage", p);
		model.addAttribute("pages", pages);
		model.addAttribute("maxPage", maxPage);
		return "index";
	}
	
	@RequestMapping("/addTestData")
	public String addDate(int size){
		List<Lte_lora> loras = new ArrayList<>();
		List<Lte_gps> gpses = new ArrayList<>();

		Random ran = new Random();
		for (int i = 0; i < size; i++) {
			int car_id = ran.nextInt(100000)+ 10000;
			Lte_lora lte = new Lte_lora(car_id, StringUtil.randomString(12*7), new Date());
			loras.add(lte);
			Calendar calendar = Calendar.getInstance();
			
			Lte_gps  gps = new Lte_gps( car_id, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), ran.nextDouble()+39, ran.nextDouble()+116, calendar.getTime());
			gpses.add(gps);
		}
		lteService.addTestData(loras, gpses);
		
		return "success";
	}

	public LteService getLteService() {
		return lteService;
	}

	public void setLteService(LteService lteService) {
		this.lteService = lteService;
	}
	
	
}
