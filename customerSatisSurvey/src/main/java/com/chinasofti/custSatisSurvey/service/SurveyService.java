package com.chinasofti.custSatisSurvey.service;

import java.util.List;

import com.chinasofti.custSatisSurvey.pojo.TSurvey;

public interface SurveyService {
	
	public void addProjSurvey(TSurvey tSurvey);
	
	public List<TSurvey> getSurveys();
	
	public List<TSurvey> getSurveysByLobEvalDate(String lob,String evalBegDate, String evalEndDate);

}
