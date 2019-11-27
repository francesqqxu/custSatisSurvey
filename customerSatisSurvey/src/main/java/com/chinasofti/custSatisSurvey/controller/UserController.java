package com.chinasofti.custSatisSurvey.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.custSatisSurvey.controller.helper.ExceptionHelper;
import com.chinasofti.custSatisSurvey.dto.JsonResult;
import com.chinasofti.custSatisSurvey.dto.RestResponseBo;
import com.chinasofti.custSatisSurvey.pojo.TSurvey;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.service.UserService;
import com.chinasofti.custSatisSurvey.util.ShortUUIDUnique8Code;
import com.chinasofti.custSatisSurvey.util.WebUtil;
 
 
 

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService  userService;
	
	@RequestMapping("")
	public String index(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		//TempUtil.getReqInfo(httpReq); 
		return "user";
	}
	
	
	@RequestMapping("getUserListById")
	@ResponseBody
	public Object getUserListById(ServletRequest request){
		
		
		LOGGER.info("getUserList");
		String usertype = "";
		List<TUser> tUserList = new ArrayList<TUser>();
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
	    WebUtil.getReqInfo(httpReq);
		
	  
		httpReq = (HttpServletRequest)request;
		TUser loginUser = WebUtil.getLoginUser(httpReq);
		if(null != loginUser) {
			  LOGGER.info("loginUserId={}",loginUser.getId());
		}else {
			  LOGGER.info("no user info in session");
			  String msg = "session失效，请重新登录！";
	    	  return  RestResponseBo.fail(msg);
		}
		
		usertype = loginUser.getUsername();
		try { 
			   if(usertype=="集团QA") {
				  tUserList = userService.getUserListById(0);
			   }else {
				   tUserList = userService.getUserListById(loginUser.getId());
			   }
			   return   tUserList;
				 
		}catch (Exception e) {
			String msg = "查询摘要失败";
	    	return ExceptionHelper.handlerException(LOGGER, msg, e); 
		}
					
	}
	
	
	
	  @RequestMapping("/add")
	  @ResponseBody
	  public Object userAdd(TUser tUser,ServletRequest request){ 
		  
		  LOGGER.info("add"); 
		  String uniqueCode;
		  List<TUser>  tUsers = new ArrayList<TUser>();
		  
		  HttpServletRequest httpReq = (HttpServletRequest)request;
		  WebUtil.getReqInfo(httpReq);
			
		  
		  httpReq = (HttpServletRequest)request;
		  TUser loginUser = WebUtil.getLoginUser(httpReq);
		  if(null != loginUser) {
			  LOGGER.info("loginUserId={}",loginUser.getId());
		  }else {
			  LOGGER.info("no user info in session");
			  String msg = "session失效，请重新登录！";
	    	  return  RestResponseBo.fail(msg);
		  }
		  try {
			   
//			  tUsers = userService.getUserListByName(tUser.getUsername());
//			  if(tUsers.size()> 0 ) {
//				  String msg = "用户名重复";
//			  	  return  RestResponseBo.fail(msg);
//			  }
			  //if(tUser.getUsertype().equals("客户")) {  //重新获取密码设置用户名为密码
				  //ShortUUIDUnique8Code shortUUIDUnique8Code = ShortUUIDUnique8Code.getInstance();
				  uniqueCode = ShortUUIDUnique8Code.generateShortUuid();
				  //tUser.setCustname(tUser.getUsername());
				  tUser.setUsername(uniqueCode);
				  tUser.setPassword(uniqueCode);
				  tUser.setUsertype("客户");
				 
			  //}
			  if(!loginUser.getUsertype().equals("集团QA")) {
				  tUser.setLob(loginUser.getLob());
			  }
			  tUser.setCreateBy(loginUser.getId());
			   
			  userService.addUser(tUser); 
		  }catch(Exception e){ 
			  String msg = "添加摘要失败";
		  	  return ExceptionHelper.handlerException(LOGGER, msg, e);
		  } 
		  
		  return  RestResponseBo.ok(); 
	  }
	 
	  @RequestMapping("update")
	  @ResponseBody
	  public Object updateUser(TUser tUser,ServletRequest request) {
			
		    LOGGER.info("update");
		    String uniqueCode;
		    HttpServletRequest httpReq = (HttpServletRequest)request;
			TUser loginUser = WebUtil.getLoginUser(httpReq);
			
			if(null != loginUser) {
				  LOGGER.info("loginUserId={}",loginUser.getId());
			}else {
				  LOGGER.info("no user info in session");
				  String msg = "session失效，请重新登录！";
		    	  return  RestResponseBo.fail(msg);
			}
			try {
				//if(tUser.getUsertype().equals("客户")) {  //重新获取密码设置用户名为密码
					  //ShortUUIDUnique8Code shortUUIDUnique8Code = ShortUUIDUnique8Code.getInstance();
					  uniqueCode = ShortUUIDUnique8Code.generateShortUuid();
					  //tUser.setCustname(tUser.getUsername());
					  tUser.setUsername(uniqueCode);
					  tUser.setPassword(uniqueCode);
					  tUser.setUsertype("客户");
				//  }
				userService.updateUser(tUser);
			}catch(Exception e) {
				String msg = "修改摘要失败";
		    	return ExceptionHelper.handlerException(LOGGER, msg, e);
			}
			return RestResponseBo.ok();
	  }
	  
	  @RequestMapping("del")
	  @ResponseBody
	  public Object delUserById(String id) {
			
		    LOGGER.info("delete");
			try {
				userService.delById(id);
			}catch(Exception e) {
				String msg = "删除用户失败";
		    	return ExceptionHelper.handlerException(LOGGER, msg, e);
			}
			return RestResponseBo.ok();
		}
	  
	  @RequestMapping("delByIds")
	  @ResponseBody
	  public Object delUserByIds(String[] ids) {
			
		    LOGGER.info("delUserByIds");
			try {
				userService.delByIds(ids);
			}catch(Exception e) {
				String msg = "删除用户失败";
		    	return ExceptionHelper.handlerException(LOGGER, msg, e);
			}
			return RestResponseBo.ok();
		}
	  
		
}
