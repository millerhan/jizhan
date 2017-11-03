package com.han.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.han.vo.Lte_lora;

public interface Lte_loraDao {

	public List<Lte_lora> selectAll();
	
	public List<Lte_lora> selectByPage(@Param("start")int start,@Param("size")int size);
	
	public int addTestData(List<Lte_lora> loras);
	
	public int selectRecords();
	
}
