package com.han.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.dao.Lte_gpsDao;
import com.han.dao.Lte_loraDao;
import com.han.vo.Lte_gps;
import com.han.vo.Lte_lora;

@Service
public class LteServiceImpl implements LteService {

	@Autowired
	private Lte_loraDao lte_loraDao;
	@Autowired
	private Lte_gpsDao lte_gpsDao;

	@Override
	public int addTestData(List<Lte_lora> loras,List<Lte_gps> gpses) {
		// TODO Auto-generated method stub
		return lte_loraDao.addTestData(loras) + lte_gpsDao.addTestData(gpses);
	}

	public Lte_loraDao getLte_loraDao() {
		return lte_loraDao;
	}

	public void setLte_loraDao(Lte_loraDao lte_loraDao) {
		this.lte_loraDao = lte_loraDao;
	}


	public Lte_gpsDao getLte_gpsDao() {
		return lte_gpsDao;
	}

	public void setLte_gpsDao(Lte_gpsDao lte_gpsDao) {
		this.lte_gpsDao = lte_gpsDao;
	}

	@Override
	public List<Lte_lora> findAll() {
		// TODO Auto-generated method stub
		return lte_loraDao.selectAll();
	}

	@Override
	public List<Lte_lora> findPage(int page, int size) {
		// TODO Auto-generated method stub

		return lte_loraDao.selectByPage((page - 1) * size, size);
	}

	@Override
	public int findMaxPage(int size) {
		// TODO Auto-generated method stub
		int recordSize = lte_loraDao.selectRecords();
		int pages = recordSize % size == 0 ? recordSize / size : recordSize / size + 1;
		return pages;
	}

	@Override
	public Lte_gps findGpsByCarId(int car_id) {
		// TODO Auto-generated method stub
		return lte_gpsDao.selectGps(car_id);
	}

}
