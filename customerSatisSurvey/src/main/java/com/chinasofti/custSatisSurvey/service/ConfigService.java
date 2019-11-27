package com.chinasofti.custSatisSurvey.service;

import java.util.List;

import com.chinasofti.custSatisSurvey.pojo.TConfig;

 
public interface ConfigService {
	
	public List<TConfig> selectBySelectId(String selectId,String loginUser,String isAll);

}
