package com.han.dao;

import java.util.List;

import com.han.vo.Lte_gps;

public interface Lte_gpsDao {

	public Lte_gps selectGps(int car_id);
	
	public int addTestData(List<Lte_gps> gpses);
	
}
