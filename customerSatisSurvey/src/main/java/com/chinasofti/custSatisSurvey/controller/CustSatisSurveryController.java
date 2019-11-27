package com.chinasofti.custSatisSurvey.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.custSatisSurvey.Exception.TipException;
import com.chinasofti.custSatisSurvey.controller.helper.ExceptionHelper;
import com.chinasofti.custSatisSurvey.dto.RestResponseBo;
import com.chinasofti.custSatisSurvey.pojo.TSurvey;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.service.SurveyService;
import com.chinasofti.custSatisSurvey.util.AppUtils;
import com.chinasofti.custSatisSurvey.util.ExcelUtils;
import com.chinasofti.custSatisSurvey.util.WebUtil;
 

@Controller
@RequestMapping("/custSatisSurvey")
public class CustSatisSurveryController {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(CustSatisSurveryController.class);
	
	@Autowired
	private SurveyService  surveyService;
	
	@RequestMapping("/project/add")
	@ResponseBody
	public Object projSurveyAdd(TSurvey tSurvey,ServletRequest request) {
		
		LOGGER.info("projSurveyAdd");
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		WebUtil.getReqInfo(httpReq);
		
		String surveytype = "";
		String msg = "";
		TUser tUser = null;
		try {
			tUser = WebUtil.getLoginCustUser(httpReq);
			if(null != tUser) {
				LOGGER.info("tUser.lob:={}", tUser.getLob());
				tSurvey.setLob(tUser.getLob());
				surveyService.addProjSurvey(tSurvey);
			}else {
				LOGGER.info("no user info in session");
				surveytype = tUser.getSurveytype();
				switch(surveytype) {
           		case "项目提交":
           			msg = "session失效，请重新登录！";
           			break; 
           		case "人力外包":
           			msg = "session失效，请重新登录！";
           			break; 
           			 
           		case "project":
           			msg = "session invalid，please login again！";
           			break; 
           			 
           		case "staff":
           			msg = "session invalid，please login again！";
           			break; 
           			 
           		default: 
           			msg = "session失效，请重新登录！";
           			break; 
           			 
           }
			 
		    	return  RestResponseBo.fail(msg);
			}
			
		}catch(Exception e) {
			surveytype = tUser.getSurveytype();
			switch(surveytype) {
       		case "项目提交":
       			msg = "添加问卷失败！";
       			break; 
       		case "人力外包":
       			msg = "添加问卷失败！";
       			break; 
       			 
       		case "project":
       			msg = "add questionnaire failed ！";
       			break; 
       			 
       		case "staff":
       			msg = "add questionnaire failed ！！";
       			break; 
       			 
       		default: 
       			msg = "添加问卷失败！";
       			break; 
       			 
       }
		  	return ExceptionHelper.handlerException(LOGGER, msg, e);
		}
		 return RestResponseBo.ok();  
	}
	
	@RequestMapping(value = "/export")
    public void surveyExport(@RequestParam String lob 
    		                ,@RequestParam String evalBegDate
    		                ,@RequestParam String evalEndDate
    		                ,HttpServletRequest request
    		                ,HttpServletResponse response) throws IOException {
		
		LOGGER.info("export");
		LOGGER.info("lob is {}", lob);
		List<TSurvey> tSurveys = new ArrayList<TSurvey>();
		String templateFileName = "";
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		WebUtil.getReqInfo(httpReq);
		TUser tUser = WebUtil.getLoginUser(httpReq);
//		try {
			if(null != tUser) {
				String templatePath = "D:\\custSurveryTemplate\\template";
		    	String outputPath = "D:\\custSurveryTemplate\\outputs";
		    	
		    	if(!tUser.getUsertype().equals("集团QA")) {
		    	   lob = tUser.getLob();    	 
		    	}
		    	tSurveys = surveyService.getSurveysByLobEvalDate(lob,evalBegDate, evalEndDate);
		    	
		    	ExcelUtils  excelUtils = new ExcelUtils();
		    	excelUtils.download(templatePath, outputPath, response,tUser, tSurveys);
			}else {
				LOGGER.info("no user info in session");
				throw new TipException("no user info in session, reLogin");
			}
//		}catch(Exception e) {
//			String msg = "下载摘要失败";
//	    	return ExceptionHelper.handlerException(LOGGER, msg, e);
//		}
//		 return RestResponseBo.ok(); 
    }
}
