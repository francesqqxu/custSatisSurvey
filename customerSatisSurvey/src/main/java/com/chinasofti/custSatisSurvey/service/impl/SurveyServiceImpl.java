package com.chinasofti.custSatisSurvey.service.impl;

 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasofti.custSatisSurvey.dao.TSurveyMapper;
import com.chinasofti.custSatisSurvey.pojo.TSurvey;
import com.chinasofti.custSatisSurvey.pojo.TSurveyExample;
import com.chinasofti.custSatisSurvey.service.SurveyService;

 

@Service
public class SurveyServiceImpl implements SurveyService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyServiceImpl.class);
	
	@Autowired
	private TSurveyMapper tSurveyMapper;
	
	public void  addProjSurvey(TSurvey tSurvey) {
		
		LOGGER.info("tSurvey.custName:={}", tSurvey.getCustName());
		 
		tSurveyMapper.insert3(tSurvey);
		 
	}
	
	public List<TSurvey> getSurveys(){
		
		TSurveyExample tSurveyExample = new TSurveyExample();
		tSurveyExample.setOrderByClause("id asc");
		return tSurveyMapper.selectByExample(tSurveyExample);
	}
	
	public List<TSurvey> getSurveysByLobEvalDate(String lob,String evalBegDate, String evalEndDate){
		
		TSurveyExample tSurveyExample = new TSurveyExample();
		TSurveyExample.Criteria criteria = tSurveyExample.createCriteria();
		if(null != lob) {
			if(!lob.equals("所有")) {
				criteria.andLobEqualTo(lob);
			}
		}
		if(null != evalBegDate) {
			if(null != evalEndDate) {
				criteria.andEvalDateBetween(evalBegDate, evalEndDate);	
			}
		}	
		
		return tSurveyMapper.selectByExample(tSurveyExample);
		 
	}
	

}
