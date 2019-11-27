package com.chinasofti.custSatisSurvey.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasofti.custSatisSurvey.dao.TConfigMapper;
import com.chinasofti.custSatisSurvey.pojo.TConfig;
import com.chinasofti.custSatisSurvey.pojo.TConfigExample;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.pojo.TUserExample;
import com.chinasofti.custSatisSurvey.service.ConfigService;
import com.chinasofti.custSatisSurvey.service.UserService;


@Service
public class ConfigServiceImpl implements ConfigService {
	
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	
	@Autowired
	TConfigMapper  tConfigMapper;
	@Autowired
	UserService userService;
	
	public List<TConfig> selectBySelectId(String selectId,String loginUser,String isAll){
		
		 List<TUser> tUsers = new ArrayList<TUser>();
		   
		 Boolean isGroupQA = false;
		 
		TConfigExample example = new TConfigExample();
		TConfigExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause("id asc");
		criteria.andConfigTypeEqualTo(selectId);
		
		if(null!= loginUser) {
			
			if(!loginUser.equals("")) {
				tUsers = userService.getUserListByName(loginUser);
				LOGGER.info(tUsers.get(0).getUsertype());
				if(null !=  tUsers) {
					if( !tUsers.get(0).getUsertype().equals("集团QA")) {
						
						criteria.andOptionTextEqualTo(tUsers.get(0).getLob());
					}
					else {
						isGroupQA = true;
					}
				}
			}
		}
		List<TConfig> tConfigs  =  tConfigMapper.selectByExample(example);
//		LOGGER.info("isAll={}",isAll);
//		if(isAll.equals("true") && isGroupQA) {
//			
//			TConfig tConfig = new TConfig();
//			tConfig.setId(0);
//			tConfig.setOptionText("所有");
//			tConfigs.add(tConfig);
//		}
//	    
		
		List<TConfig> newTConfigs = new ArrayList<TConfig>();
		if(isAll.equals("true") && isGroupQA) {
			TConfig tConfig =  new TConfig();
			tConfig.setId(0);
			tConfig.setOptionText("所有");
			tConfig.setOptionValue("所有");
			tConfig.setConfigType("lob");
			
			newTConfigs.add(tConfig);
			newTConfigs.addAll(tConfigs);
			return newTConfigs;
		}
		return tConfigs;
	}

}
