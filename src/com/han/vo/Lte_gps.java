package com.han.vo;

import java.util.Date;
/**
 * 
 * lte_gps 
 * Tue Jul 18 16:14:34 CST 2017
 * @author º«ÉÙ±ó 
 * 
 */ 
public class Lte_gps{
	private long id;
	private int car_id;
	private int gps_year;
	private int gps_month;
	private int gps_day;
	private int gps_hour;
	private int gps_miniute;
	private int gps_second;
	private double gps_latitude;
	private double gps_longitude;
	private Date time;

	public Lte_gps(){
		super();
	}

	public Lte_gps(long id,int car_id,int gps_year,int gps_month,int gps_day,int gps_hour,int gps_miniute,int gps_second,double gps_latitude,double gps_longitude,Date time){
		super();
		this.id=id;
		this.car_id=car_id;
		this.gps_year=gps_year;
		this.gps_month=gps_month;
		this.gps_day=gps_day;
		this.gps_hour=gps_hour;
		this.gps_miniute=gps_miniute;
		this.gps_second=gps_second;
		this.gps_latitude=gps_latitude;
		this.gps_longitude=gps_longitude;
		this.time=time;
	}
	public Lte_gps(int car_id,int gps_year,int gps_month,int gps_day,int gps_hour,int gps_miniute,int gps_second,double gps_latitude,double gps_longitude,Date time){
		super();
		this.car_id=car_id;
		this.gps_year=gps_year;
		this.gps_month=gps_month;
		this.gps_day=gps_day;
		this.gps_hour=gps_hour;
		this.gps_miniute=gps_miniute;
		this.gps_second=gps_second;
		this.gps_latitude=gps_latitude;
		this.gps_longitude=gps_longitude;
		this.time=time;
	}

	@Override
	public String toString(){
		return "Lte_gps [id=" + id + ", car_id=" + car_id + ", gps_year=" + gps_year + ", gps_month=" + gps_month + ", gps_day=" + gps_day + ", gps_hour=" + gps_hour + ", gps_miniute=" + gps_miniute + ", gps_second=" + gps_second + ", gps_latitude=" + gps_latitude + ", gps_longitude=" + gps_longitude + ", time=" + time + "]";
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

	public void setGps_year(int gps_year){
		this.gps_year=gps_year;
	}

	public int getGps_year(){
		return gps_year;
	}

	public void setGps_month(int gps_month){
		this.gps_month=gps_month;
	}

	public int getGps_month(){
		return gps_month;
	}

	public void setGps_day(int gps_day){
		this.gps_day=gps_day;
	}

	public int getGps_day(){
		return gps_day;
	}

	public void setGps_hour(int gps_hour){
		this.gps_hour=gps_hour;
	}

	public int getGps_hour(){
		return gps_hour;
	}

	public void setGps_miniute(int gps_miniute){
		this.gps_miniute=gps_miniute;
	}

	public int getGps_miniute(){
		return gps_miniute;
	}

	public void setGps_second(int gps_second){
		this.gps_second=gps_second;
	}

	public int getGps_second(){
		return gps_second;
	}

	public void setGps_latitude(double gps_latitude){
		this.gps_latitude=gps_latitude;
	}

	public double getGps_latitude(){
		return gps_latitude;
	}

	public void setGps_longitude(double gps_longitude){
		this.gps_longitude=gps_longitude;
	}

	public double getGps_longitude(){
		return gps_longitude;
	}

	public void setTime(Date time){
		this.time=time;
	}

	public Date getTime(){
		return time;
	}
}

