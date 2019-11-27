package com.chinasofti.custSatisSurvey.util;

import java.io.File;

public class AppUtils {

	
	public static String  getTemplateFile(String templatePath,String surveyType) {
		
		File file=new File(templatePath);
		File[] fileList = file.listFiles();
		String fileName = "";
		  System.out.println("该目录下对象个数："+fileList.length);
		  for (int i = 0; i < fileList.length; i++) {
		   if (fileList[i].isFile()) {
			    System.out.println("文     件："+fileList[i]);
			    fileName = fileList[i].getName();
			    if(fileList[i].getName().contains(surveyType)) {
			    	return fileName;
			    }
		   }
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		String templatePath = "D:\\custSurveryTemplate\\template";
		
		String fileName = AppUtils.getTemplateFile(templatePath, "项目提交");
		fileName =  AppUtils.getTemplateFile(templatePath, "人力外包");
		fileName =  AppUtils.getTemplateFile(templatePath, "Questionnaire");
	}
}
