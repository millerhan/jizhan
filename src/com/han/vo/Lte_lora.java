package com.han.vo;

import java.util.Date;
/**
 * 
 * lte_lora 
 * Tue Jul 18 16:14:34 CST 2017
 * @author º«ÉÙ±ó 
 * 
 */ 
public class Lte_lora{
	private long id;
	private int car_id;
	private String lora_id;
	private Date time;

	public Lte_lora(){
		super();
	}

	public Lte_lora(long id,int car_id,String lora_id,Date time){
		super();
		this.id=id;
		this.car_id=car_id;
		this.lora_id=lora_id;
		this.time=time;
	}
	public Lte_lora(int car_id,String lora_id,Date time){
		super();
		this.car_id=car_id;
		this.lora_id=lora_id;
		this.time=time;
	}

	@Override
	public String toString(){
		return "Lte_lora [id=" + id + ", car_id=" + car_id + ", lora_id=" + lora_id + ", time=" + time + "]";
	}

	public void setId(long id){
		this.id=id;
	}

	public long getId(){
		return id;
	}

	public void setCar_id(int car_id){
		this.car_id=car_id;
	}

	public int getCar_id(){
		return car_id;
	}

	public void setLora_id(String lora_id){
		this.lora_id=lora_id;
	}

	public String getLora_id(){
		return lora_id;
	}

	public void setTime(Date time){
		this.time=time;
	}

	public Date getTime(){
		return time;
	}
}

