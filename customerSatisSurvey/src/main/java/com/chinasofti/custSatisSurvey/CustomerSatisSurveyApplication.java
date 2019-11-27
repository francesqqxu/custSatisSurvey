package com.chinasofti.custSatisSurvey;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Repository;

//定义Spring Boot扫描包路径
@SpringBootApplication(scanBasePackages= {"com.chinasofti.custSatisSurvey"})
//定义扫描filter包
@ServletComponentScan
//定义实体Bean扫描路径
@EntityScan(basePackages="com.chinasofti.custSatisSurvey.pojo")
//定义MyBatis的扫描
@MapperScan(
	//定义扫描包
	basePackages="com.chinasofti.custSatisSurvey.*",
	//指定SqlSessionFactory, 如果SqlSessionTemplate被指定， 则作废
	sqlSessionFactoryRef="sqlSessionFactory",
	//指定sqlSessionTemplate,将忽略sqlSessionFactory配置
	sqlSessionTemplateRef = "sqlSessionTemplate",
	//markerInterface = Class.class,
	annotationClass=Repository.class
	
	
)
public class CustomerSatisSurveyApplication {
    
	@Autowired
	SqlSessionFactory sqlSessionFactory = null;
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerSatisSurveyApplication.class, args);
	}

}
