package com.chinasofti.custSatisSurvey.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	  
	  
	  @RequestMapping(value="/exportTemplate",method=RequestMethod.GET) 
	   public void exportTemplate(HttpServletResponse response) throws IOException{
	             // 声明一个工作薄        
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //创建一个Excel表单,参数为sheet的名字
	            HSSFSheet sheet = workbook.createSheet("模板表");
	            //创建表头
	            setTitle(workbook, sheet);
	           
	            String fileName = "userTemplate.xls";
	            //清空response  
	            response.reset();  
	            //设置response的Header  
	            response.addHeader("Content-Disposition", "attachment;filename="+ fileName);  
	            OutputStream os = new BufferedOutputStream(response.getOutputStream());  
	            response.setContentType("application/vnd.ms-excel;charset=gb2312"); 
	            //将excel写入到输出流中
	            workbook.write(os);
	            os.flush();
	            os.close();
	  }

	    // 创建表头
	  private void setTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        // 设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        //sheet.setColumnWidth(8, 60 * 256);
        // 设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        //导出的Excel头部
        String[] headers = { "客户名称", "业务线", "问卷类型", "客户评价人部门", "客户评价人姓名"};
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 16);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);
        }
	 }
	 
	// 导入Excel
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(MultipartFile file) {
	        
		   String msg = "";
	       List<TUser> userList = new ArrayList<TUser>();
	       String fileName = file.getOriginalFilename();
		       
	 	   try {
	 		    HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
  
	            // 有多少个sheet
	            int sheets = workbook.getNumberOfSheets();
	            for (int i = 0; i < sheets; i++) {
	                HSSFSheet sheet = workbook.getSheetAt(i);
	                // 获取多少行
	                int rows = sheet.getPhysicalNumberOfRows();
	                TUser tUser = null;
	                // 遍历每一行，注意：第 0 行为标题
	                for (int j = 1; j < rows; j++) {
	                     tUser = new TUser();
	                    // 获得第 j 行
	                    HSSFRow row = sheet.getRow(j);
	                    tUser.setCustname(row.getCell(0).toString());   	//客户名称
	                    tUser.setLob(row.getCell(1).toString());         	//业务线
	                    tUser.setSurveytype(row.getCell(2).toString());		//问卷类型
	                    tUser.setEvalPersonDep(row.getCell(3).toString());	//客户评价人部门
	                    tUser.setEvalPersonName(row.getCell(4).toString());	//客户评价人姓名
	                    userList.add(tUser);
	                }
	            }
	            userService.saveBatch(userList);
	        } catch (IOException e) {
	        	return ExceptionHelper.handlerException(LOGGER, msg, e);
	        }
	 	   		return RestResponseBo.ok();
	    }
	 
		
}
