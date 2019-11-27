package com.chinasofti.custSatisSurvey.controller;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinasofti.custSatisSurvey.constant.WebConst;
import com.chinasofti.custSatisSurvey.dto.RestResponseBo;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.service.UserService;
import com.chinasofti.custSatisSurvey.util.WebUtil;




@Controller
public class HomeController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
//	@RequestMapping("/custSatisSurvey")
//	public String indexCust(ServletRequest request,ServletResponse response) {
//		
//		HttpServletRequest httpReq = (HttpServletRequest)request;
//		
//		return "loginCust";
//	}
	
	@RequestMapping("/custSatisSurvey/manager")
	public String index(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		return "login";
	}
	
	@RequestMapping("/welcome")
	public String welcome(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		return "welcome";
	}
	
	@RequestMapping("/surveyExport")
	public String surveyExport(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		WebUtil.getReqInfo(httpReq);
		 
		return "surveyExport";
	}
	
	@RequestMapping("/endMessage")
	public String endMessage(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		return "endMessage";
	}
	
	@RequestMapping("/endMessageEn")
	public String endMessageEn(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		return "endMessageEn";
	}
	
	@RequestMapping("/login")
	public String doLoginGet(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		return "login";
	}
	
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public String  doLogin(@RequestParam String username,
			               @RequestParam String password,
			               Model model,
			               HttpServletRequest request,
			               HttpServletResponse response) {
		
		LOGGER.info("cust username:={}",username);
		LOGGER.info("cust password:={}",password);
		
		TUser tUser = userService.login(username,password);
		if(null != tUser) {
           if(tUser.getUsertype().equals("客户")) {
        	   model.addAttribute("message","用户类型错误！");
   			   return "login"; 
           }
			
		   request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, tUser);
           model.addAttribute("user",tUser);
           model.addAttribute("message","登录成功");
           return "mainbroad";
		}
		else {
			//request.getSession().setAttribute("message", "用户或密码错误！");
		    model.addAttribute("message","用户或密码错误！");
			return "login";
		}
		
	}
	
//	@RequestMapping(value="/custSatisSurvey/login",method= RequestMethod.POST)
//	public String  doLogin_1(@RequestParam String username,
//			               @RequestParam String password,
//			               Model model,
//			               HttpServletRequest request,
//			               HttpServletResponse response) {
//		
//		LOGGER.info("cust username:={}",username);
//		LOGGER.info("cust password:={}",password);
//		
//		TUser tUser = userService.login(username,password);
//		if(null != tUser) {
//           request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, tUser);
//           model.addAttribute("user",tUser);
//           model.addAttribute("message","登录成功");
//           return "mainbroad";
//		}
//		else {
//			//request.getSession().setAttribute("message", "用户或密码错误！");
//		    model.addAttribute("message","用户或密码错误！");
//			return "login";
//		}
//		
//	}
	
	@RequestMapping(value="/loginCust",method=RequestMethod.GET)
	public String doLoginCustGet(ServletRequest request,ServletResponse response) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		return "loginCust";
	}
 	
	@RequestMapping(value="/loginCust",method=RequestMethod.POST  )
	public String  doLoginCust(@RequestParam("password") String password,
			                   @RequestParam(value="language", required=false,defaultValue="zh_CN") String language,
			                    Model model,
								HttpServletRequest request,
								HttpServletResponse response) {
		
		LOGGER.info("cust password:={}",password);
	    LOGGER.info("language:={}",language);
		String surveytype = "";
		
		TUser tUser = userService.custLogin(password);
		if(null != tUser) {
           request.getSession().setAttribute(WebConst.LOGINCUST_SESSION_KEY, tUser);
           //return "redirect:/custSatisSurvey/projectCh";
           model.addAttribute("user",tUser);
           surveytype = tUser.getSurveytype();
           LOGGER.info(surveytype);
           model.addAttribute("user",tUser);
           switch(surveytype) {
           		case "项目提交":
           			return "custGreeting";
           			 
           		case "人力外包":
           			return "custGreeting";
           			 
           		case "project":
           			return "custGreetingEn";
           			 
           		case "staff":
           			return "custGreetingEn";
           			 
           		default: 
           			return "custGreeting";
           			 
           }
            
	}else {
			//request.getSession().setAttribute("message", "用户或密码错误！");
		if(language.equals("zh_CN")) {
		    model.addAttribute("message","登录密码错误！");
		}else {
			model.addAttribute("message","login password incorrect!");
		}
		return "loginCust";
	
	}
}
	@RequestMapping("/surveyNav")
	public String  doSurveyNav( Model model,
								HttpServletRequest request,
								HttpServletResponse response) {
		
		String surveytype = "";
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		TUser tUser = WebUtil.getLoginCustUser(httpReq);
		
		if(null != tUser) {
	           surveytype = tUser.getSurveytype();
	           LOGGER.info(surveytype);
	           model.addAttribute("user",tUser);
	           switch(surveytype) {
	           		case "项目提交":
	           			return "questionnaireProjectCh";
	           			 
	           		case "人力外包":
	           			return "questionnaireLaborCh";
	           			 
	           		case "project":
	           			return "questionnaireProjectEn";
	           			 
	           		case "staff":
	           			return "questionnaireStaffEn";
	           			 
	           		default: 
	           			return "questionnaireProjectCh";
	           			 
	           }
	            
		}else {
			LOGGER.info("no user info in session");
			model.addAttribute("message","session失效，请重新登录！");
			return  "loginCust";
		}
		 

		
	
	}
	
	
	@RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        try {
            response.sendRedirect("/custSatisSurvey/manager");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("注销失败", e);
        }
    }
}
