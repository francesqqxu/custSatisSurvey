package com.chinasofti.custSatisSurvey.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinasofti.custSatisSurvey.controller.CustSatisSurveryController;
import com.chinasofti.custSatisSurvey.dao.TSurveyMapper;
import com.chinasofti.custSatisSurvey.pojo.TSurvey;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.service.SurveyService;

@Component
public class ExcelUtils {

	private static final Logger LOGGER =  LoggerFactory.getLogger(ExcelUtils.class);
	
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static List<TSurvey> tSurveys = new ArrayList<TSurvey>();
    
    @Resource
    private SurveyService surveyService;

    //声明一个该工具类的静态的内部对象
    private static ExcelUtils excelUtils;

    //工具类中需要注入service，dao等需要
    //使用注解@PostConstruct把需要使用的service，dao等加载到上面定义的静态内部对象中
    @PostConstruct
    public void init() {
        excelUtils = this;
        excelUtils.surveyService =  this.surveyService;
    }
    
    private List<TSurvey>  getTSurvey(){
    	
    	return surveyService.getSurveys();
    }
    
    public Set<File> writeToExcel(String templatePath,String path,TUser tUser,List<TSurvey> tSurveys) {
        
    	TSurvey tSurvey;
        Set<File> outputFiles = new HashSet<File>();
        String templateFileName = "";
    	String surveyType = "";
    	
    	for (int i = 0; i < tSurveys.size(); i++) {
    		tSurvey = tSurveys.get(i);    
    		surveyType = tSurvey.getSurveytype();
    		LOGGER.info("surveyType is {}",surveyType);
    		templateFileName = AppUtils.getTemplateFile(templatePath, surveyType);
    		templateFileName = templatePath + "\\" + templateFileName;
    		LOGGER.info("templateFileName is {}", templateFileName);
    		File newFile = createNewFile(templateFileName, path,tUser,tSurvey);
      		InputStream is = null;
	        XSSFWorkbook workbook = null;
	        XSSFSheet sheet = null;
	        try {
	            is = new FileInputStream(newFile);// 将excel文件转为输入流
	            workbook = new XSSFWorkbook(is);// 创建个workbook，
	            // 获取第一个sheet
	            sheet = workbook.getSheetAt(0);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	
	        if (sheet != null) {
	            try {
	                // 写数据
	                FileOutputStream fos = new FileOutputStream(newFile);
	               
	                switch(surveyType) {
	                case "项目提交":
	                	  fillProjectChSurvey(tSurvey,sheet);
	                	  break;
	                case "人力外包":
	                	  fillLaborChSurvey(tSurvey,sheet);
	                	  break;
	                case "project":
	                	  fillProjectEnSurvey(tSurvey,sheet);
	                	  break;
	                case "staff":
	                	  fillStaffEnSurvey(tSurvey,sheet);
	                	  break;
	                	  
	                }
	                
	                
	                
	                workbook.write(fos);
	                fos.flush();
	                fos.close();
	                outputFiles.add(newFile);
	                
	            }  catch (Exception e) {
	                e.printStackTrace();
	                return null;
	            } finally {
	                try {
	                    if (null != is) {
	                        is.close();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            
	        }
    	}
    	return outputFiles;
        
    }
    
    public void fillProjectChSurvey(TSurvey tSurvey, XSSFSheet sheet) {
    	//根据excel模板格式写入数据....
        fillDataInCell(tSurvey.getCustName(),  sheet, 0,   2);         //客户名称
        fillDataInCell(tSurvey.getEvalPersonName(),  sheet, 0,   4);   //评价人姓名
        fillDataInCell(tSurvey.getEvalDate(),  sheet, 1,   2);         //调查日期
        fillDataInCell(tSurvey.getEvalPersonDep(),  sheet, 1,   4);    //评价人部门
        fillDataInCell(tSurvey.getComprehensiveEval1(),  sheet, 3,   3);
        fillDataInCell(tSurvey.getComprehensiveEval1Reason(),  sheet, 4,   3);
        fillDataInCell(tSurvey.getSpecialEval11(),  sheet, 8,   3);
        fillDataInCell(tSurvey.getSpecialEval12(),  sheet, 9,   3);
        fillDataInCell(tSurvey.getSpecialEval1Detail(),  sheet, 8,   4);
        fillDataInCell(tSurvey.getSpecialEval21(),  sheet, 10,   3);
        fillDataInCell(tSurvey.getSpecialEval22(),  sheet, 11,   3);
        fillDataInCell(tSurvey.getSpecialEval23(),  sheet, 12,   3);
        fillDataInCell(tSurvey.getSpecialEval2Detail(),  sheet, 10,   4);
        fillDataInCell(tSurvey.getSpecialEval31(),  sheet, 13,   3);
        fillDataInCell(tSurvey.getSpecialEval32(),  sheet, 14,   3);
        fillDataInCell(tSurvey.getSpecialEval3Detail(),  sheet, 13,   4);
        fillDataInCell(tSurvey.getSpecialEval41(),  sheet, 15,   3);
        fillDataInCell(tSurvey.getSpecialEval42(),  sheet, 16,   3);
        fillDataInCell(tSurvey.getSpecialEval4Detail(),  sheet, 15,   4);
        fillDataInCell(tSurvey.getSpecialEval51(),  sheet, 17,   3);
        fillDataInCell(tSurvey.getSpecialEval52(),  sheet, 18,   3);
        fillDataInCell(tSurvey.getSpecialEval5Detail(),  sheet, 17,   4);
        fillDataInCell(tSurvey.getSpecialEval61(),  sheet, 19,   3);
        fillDataInCell(tSurvey.getSpecialEval62(),  sheet, 20,   3);
        fillDataInCell(tSurvey.getSpecialEval6Detail(),  sheet, 19,   4);
        fillDataInCell(tSurvey.getSpecialEval71(),  sheet, 21,   3);
        fillDataInCell(tSurvey.getSpecialEval72(),  sheet, 22,   3);
        fillDataInCell(tSurvey.getSpecialEval7Detail(),  sheet, 21,   4);
        fillDataInCell(tSurvey.getSpecialEval8(),  sheet, 23,   2);
        fillDataInCell(tSurvey.getSpecialEval9(),  sheet, 24,   2);
         
    }
    
    public void fillLaborChSurvey(TSurvey tSurvey, XSSFSheet sheet) {
    	//根据excel模板格式写入数据....
    	 fillDataInCell(tSurvey.getCustName(),  sheet, 0,   2);         //客户名称
         fillDataInCell(tSurvey.getEvalPersonName(),  sheet, 0,   4);   //评价人姓名
         fillDataInCell(tSurvey.getEvalDate(),  sheet, 1,   2);         //调查日期
         fillDataInCell(tSurvey.getEvalPersonDep(),  sheet, 1,   4);    //评价人部门
         fillDataInCell(tSurvey.getComprehensiveEval1(),  sheet, 3,   3);
         fillDataInCell(tSurvey.getComprehensiveEval1Reason(),  sheet, 4,   3);
         fillDataInCell(tSurvey.getSpecialEval11(),  sheet, 8,   3);
         fillDataInCell(tSurvey.getSpecialEval12(),  sheet, 9,   3);
         fillDataInCell(tSurvey.getSpecialEval1Detail(),  sheet, 8,   4);
         fillDataInCell(tSurvey.getSpecialEval21(),  sheet, 10,   3);
         fillDataInCell(tSurvey.getSpecialEval22(),  sheet, 11,   3);
         fillDataInCell(tSurvey.getSpecialEval2Detail(),  sheet, 10,   4);
         fillDataInCell(tSurvey.getSpecialEval31(),  sheet, 12,   3);
         fillDataInCell(tSurvey.getSpecialEval32(),  sheet, 13,   3);
         fillDataInCell(tSurvey.getSpecialEval3Detail(),  sheet, 12,   4);
         fillDataInCell(tSurvey.getSpecialEval41(),  sheet, 14,   3);
         fillDataInCell(tSurvey.getSpecialEval42(),  sheet, 15,   3);
         fillDataInCell(tSurvey.getSpecialEval4Detail(),  sheet, 14,   4);
         fillDataInCell(tSurvey.getSpecialEval5(),  sheet, 16,   2);
         fillDataInCell(tSurvey.getSpecialEval6(),  sheet, 17,   2);
         
        
    }
    
    
    public void fillProjectEnSurvey(TSurvey tSurvey, XSSFSheet sheet) {
    	//根据excel模板格式写入数据....
    	 fillDataInCell(tSurvey.getCustName(),  sheet, 0,   2);         //客户名称
         fillDataInCell(tSurvey.getEvalPersonName(),  sheet, 0,   4);   //评价人姓名
         fillDataInCell(tSurvey.getEvalDate(),  sheet, 1,   2);         //调查日期
         fillDataInCell(tSurvey.getEvalPersonDep(),  sheet, 1,   4);    //评价人部门
         fillDataInCell(tSurvey.getComprehensiveEval1(),  sheet, 3,   3);
         fillDataInCell(tSurvey.getComprehensiveEval1Reason(),  sheet, 4,   3);
         fillDataInCell(tSurvey.getSpecialEval11(),  sheet, 8,   3);
         fillDataInCell(tSurvey.getSpecialEval12(),  sheet, 9,   3);
         fillDataInCell(tSurvey.getSpecialEval1Detail(),  sheet, 8,   4);
         fillDataInCell(tSurvey.getSpecialEval21(),  sheet, 10,   3);
         fillDataInCell(tSurvey.getSpecialEval22(),  sheet, 11,   3);
         fillDataInCell(tSurvey.getSpecialEval23(),  sheet, 12,   3);
         fillDataInCell(tSurvey.getSpecialEval24(),  sheet, 13,   3);
         fillDataInCell(tSurvey.getSpecialEval2Detail(),  sheet, 10,   4);
         fillDataInCell(tSurvey.getSpecialEval31(),  sheet, 14,   3);
         fillDataInCell(tSurvey.getSpecialEval32(),  sheet, 15,   3);
         fillDataInCell(tSurvey.getSpecialEval33(),  sheet, 16,   3);
         fillDataInCell(tSurvey.getSpecialEval34(),  sheet, 17,   3);
         fillDataInCell(tSurvey.getSpecialEval35(),  sheet, 18,   3);
         fillDataInCell(tSurvey.getSpecialEval3Detail(),  sheet, 14,   4);
         fillDataInCell(tSurvey.getSpecialEval41(),  sheet, 19,   3);
         fillDataInCell(tSurvey.getSpecialEval42(),  sheet, 20,   3);
         fillDataInCell(tSurvey.getSpecialEval43(),  sheet, 21,   3);
         fillDataInCell(tSurvey.getSpecialEval44(),  sheet, 22,   3);
         fillDataInCell(tSurvey.getSpecialEval4Detail(),  sheet, 19,   4);
         fillDataInCell(tSurvey.getSpecialEval3(),  sheet, 23,   2);
         fillDataInCell(tSurvey.getSpecialEval4(),  sheet, 24,   2);
        
    }
    
    
    public void fillStaffEnSurvey(TSurvey tSurvey, XSSFSheet sheet) {
    	//根据excel模板格式写入数据....
    	 fillDataInCell(tSurvey.getCustName(),  sheet, 0,   2);         //客户名称
         fillDataInCell(tSurvey.getEvalPersonName(),  sheet, 0,   4);   //评价人姓名
         fillDataInCell(tSurvey.getEvalDate(),  sheet, 1,   2);         //调查日期
         fillDataInCell(tSurvey.getEvalPersonDep(),  sheet, 1,   4);    //评价人部门
         fillDataInCell(tSurvey.getComprehensiveEval1(),  sheet, 3,   3);
         fillDataInCell(tSurvey.getComprehensiveEval1Reason(),  sheet, 4,   3);
         fillDataInCell(tSurvey.getSpecialEval11(),  sheet, 8,   3);
         fillDataInCell(tSurvey.getSpecialEval12(),  sheet, 9,   3);
         fillDataInCell(tSurvey.getSpecialEval1Detail(),  sheet, 8,   4);
         fillDataInCell(tSurvey.getSpecialEval21(),  sheet, 10,   3);
         fillDataInCell(tSurvey.getSpecialEval22(),  sheet, 11,   3);
         fillDataInCell(tSurvey.getSpecialEval2Detail(),  sheet, 10,   4);
         fillDataInCell(tSurvey.getSpecialEval31(),  sheet, 12,   3);
         fillDataInCell(tSurvey.getSpecialEval3Detail(),  sheet, 12,   4);
         fillDataInCell(tSurvey.getSpecialEval41(),  sheet, 13,   3);
         fillDataInCell(tSurvey.getSpecialEval4Detail(),  sheet, 13,   4);
         fillDataInCell(tSurvey.getSpecialEval3(),  sheet, 14,   2);
         fillDataInCell(tSurvey.getSpecialEval4(),  sheet, 15,   2);
        
    }
  
	public void download(String templatePath, String outputPath, HttpServletResponse response,TUser tUser,List<TSurvey> tSurveys) throws IOException {
		
		File file = new File(outputPath);
		if (!file.exists())
			file.mkdirs();
		Set<File> srcfile = writeToExcel(templatePath, outputPath, tUser, tSurveys);
		
		file=new File(outputPath + "\\survey.zip");
		FileOutputStream outStream=new FileOutputStream(file);
		ZipOutputStream toClient=new ZipOutputStream(outStream);
		zipFile(srcfile, toClient);
		toClient.close();
		outStream.close();
		downloadFile(file, response, false);
		//for(int i=0;i<srcfile.size();i++) {
	    Iterator<File> it = srcfile.iterator();
	    while (it.hasNext()) {
			File f= (File) it.next();
			//f.delete();
		}
		 
	}

	public static void zipFile(Set<File> files, ZipOutputStream outputStream) throws IOException{
		try {
			int size = files.size();
			// 压缩列表中的文件
			//for (int i = 0; i < size; i++) {
			Iterator<File> it = files.iterator();
			while(it.hasNext()) {
				File file = (File) it.next();
				zipFile(file, outputStream);
			}
		} catch (IOException e) {
			throw e;
		}
	}
 
	public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException {
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);
 
					final int MAX_BYTE = 50 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据
 
					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量
 
					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); 
					bInStream.close(); // 关闭
					inStream.close();
				}
			} else {
				//throw new ServletException("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		}
	}
 
	public void downloadFile(File file,HttpServletResponse response,boolean isDelete) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if(isDelete)
            {
                file.delete();        //是否将生成的服务器端文件删除
            }
         } 
         catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	


    /**
     *tempPath 模板文件路径
     *path 文件路径
     *list 集合数据
     */
    public void exportExcel(String tempPath, String path, HttpServletResponse response, List<TSurvey> tSurveys) {
       
    	File newFile  = new File(tempPath);
    	
    	for (int i = 0; i < tSurveys.size(); i++) {
    		
    	    try {
//        InputStream is = null;
//        XSSFWorkbook workbook = null;
//        XSSFSheet sheet = null;
//        try {
//            is = new FileInputStream(newFile);// 将excel文件转为输入流
//            workbook = new XSSFWorkbook(is);// 创建个workbook，
//            // 获取第一个sheet
//            sheet = workbook.getSheetAt(0);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        if (sheet != null) {
//            try {
//                // 写数据
//                FileOutputStream fos = new FileOutputStream(newFile);
//                XSSFRow row = sheet.getRow(0);
//                if (row == null) {
//                    row = sheet.createRow(0);
//                }
//                XSSFCell cell = row.getCell(0);
//                if (cell == null) {
//                    cell = row.createCell(0);
//                }
//                cell.setCellValue("我是标题");
//
//                for (int i = 0; i < list.size(); i++) {
//                	TSurvey tSurvey = list.get(i);
//                    row = sheet.createRow(i+2); //从第三行开始
//
//                    //这里就可以使用sysUserMapper，做相应的操作
//                    //User user = excelUtils.sysUserMapper.selectByPrimaryKey(vo.getId());                  
//
//                    //根据excel模板格式写入数据....
////                    createRowAndCell(tSurvey.getTaobaoOrderId(), row, cell, 0);
////                    createRowAndCell(tSurvey.getOrderInfo(), row, cell, 1);
////                    createRowAndCell(tSurvey.getLy(), row, cell, 2);
////                    createRowAndCell(format.format(DateFormat.getDateInstance().parse(vo.getCreateTime())), row, cell, 3);
////                    createRowAndCell(vo.getTotal(), row, cell, 4);
////                    createRowAndCell(getOrderSource(vo.getSourceId()), row, cell, 5);
//                    //.....
//                }
//                workbook.write(fos);
//                fos.flush();
//                fos.close();

                // 下载
                InputStream fis = new BufferedInputStream(new FileInputStream(
                        newFile));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/msexcel");
                String newName = URLEncoder.encode(
                        "订单" + System.currentTimeMillis() + ".xlsx",
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + newFile.length());
                toClient.write(buffer);
                toClient.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } 
    	 // 删除创建的新文件
            this.deleteFile(newFile);
        }
        
    }

    /**
     *根据当前row行，来创建index标记的列数,并赋值数据
     */
    private void fillDataInCell(Object obj, XSSFSheet sheet, int rowIndex, int cellIndex) {
    	XSSFRow row =  sheet.getRow(rowIndex);
    	XSSFCell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }

        if (obj != null)
            cell.setCellValue(obj.toString());
        else 
            cell.setCellValue("");
    }

    /**
     * 复制文件
     * 
     * @param s
     *            源文件
     * @param t
     *            复制到的新文件
     */

    public void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     * 
     * @return
     */
    public File createNewFile(String tempPath, String rPath,TUser tUser,TSurvey tSurvey) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
        String path = (tempPath);
        File file = new File(path);
        // 保存文件的路径
        String realPath = rPath;
        // 新的文件名
        String newFileName =  "中软国际TPG客户满意度调查问卷(" + tSurvey.getSurveytype() + ")-" 
        		              + tSurvey.getCustName() + "-" + tSurvey.getEvalPersonDep() + "-"
        		              + tSurvey.getEvalPersonName() + ".xlsx";
        
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
            fileChannelCopy(file, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 下载成功后删除
     * 
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    
    public static void main(String[] args) {
    	
    	String tempPath = "D:\\custSurveryTemplate\\template\\附件2.1：中软国际TPG客户满意度调查问卷(项目提交).xlsx";
    	String path = "D:\\custSurveryTemplate\\outputs";
    	
    	ExcelUtils  excelUtils = new ExcelUtils();
    	tSurveys = excelUtils.getTSurvey();
    	
    	//excelUtils.writeExcel(tempPath, path);
    }
}