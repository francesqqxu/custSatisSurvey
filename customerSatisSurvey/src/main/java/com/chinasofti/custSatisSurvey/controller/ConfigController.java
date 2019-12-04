package com.chinasofti.custSatisSurvey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.custSatisSurvey.dto.JsonResult;
import com.chinasofti.custSatisSurvey.pojo.TConfig;
import com.chinasofti.custSatisSurvey.service.ConfigService;
 

@Controller
@RequestMapping("/config")
public class ConfigController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);
	
	@Autowired
	private ConfigService  configService;
	
	@RequestMapping("/{selectId}")
	@ResponseBody
	public JsonResult<TConfig> selectBySelectId(@PathVariable("selectId") String selectId
			, @RequestParam("loginUser") String loginUser, @RequestParam("isAll") String isAll) {
		
		return JsonResult.success(configService.selectBySelectId(selectId, loginUser,isAll));
	}
	
	@RequestMapping("/tree/lob")
	@ResponseBody
	public String selectByParentId( @RequestParam("loginUser") String loginUser, @RequestParam("isAll") String isAll) {
		
		  return configService.selectLobByParentId(loginUser,isAll);
	}
	
	
	
	
	

}
