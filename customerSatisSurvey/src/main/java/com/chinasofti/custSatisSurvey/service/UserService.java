package com.chinasofti.custSatisSurvey.service;

import java.util.List;

import com.chinasofti.custSatisSurvey.pojo.TTest;
import com.chinasofti.custSatisSurvey.pojo.TUser;


public interface UserService {
	
	public List<TUser> getUserList();
	
	public List<TUser> getUserListById(Integer id);
	
	public List<TUser> getUserListByName(String username);
	
	public void addUser(TUser tUser);
	
	public void updateUser(TUser tUser);
	
	public void delById(String id);
	
	public void delByIds(String[] ids);
	
	public void insertBatch(List<TUser> list);
	
	public void insertBatchTest(List<TTest> list);
	
	public TUser custLogin(String password);
	
	public TUser login(String username,String password);

}
