package com.chinasofti.custSatisSurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasofti.custSatisSurvey.Exception.TipException;
import com.chinasofti.custSatisSurvey.dao.TUserMapper;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.pojo.TUserExample;
import com.chinasofti.custSatisSurvey.service.UserService;
 

 
@Service
public class UserServiceImpl implements UserService {
 
	private static final Logger LOGGER =  LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	@Autowired
	TUserMapper tUserMapper;
	
	public List<TUser>getUserList(){
		
		TUserExample example = new TUserExample();
		example.setOrderByClause("id asc");
		return tUserMapper.selectByExample(example);
		
	}
	
	public List<TUser>getUserListById(Integer id){
		
		TUserExample example = new TUserExample();
		TUserExample.Criteria criteria = example.createCriteria();
		if(id == 0 ) {
			example.setOrderByClause("id asc");
		}else {
			criteria.andCreateByEqualTo(id);
		}
		return tUserMapper.selectByExample(example);
		
	}
	
	
	public List<TUser> getUserListByName(String username){
		
		TUserExample example = new TUserExample();
		TUserExample.Criteria criteria = example.createCriteria();
		if(null !=  username ) {
        	criteria.andUsernameEqualTo(username);
		}
		return tUserMapper.selectByExample(example);
		
	}
	
	public void  addUser(TUser tUser) {
		
		LOGGER.info("tUser.username:={}", tUser.getUsername());
		 
		
		//tProjdigestMapper.insertSelective2(tProjdigest);
		tUserMapper.insert3(tUser);
		 
	}
	
	public void updateUser(TUser tUser) {
		
	    LOGGER.info("tUser.username:={}", tUser.getUsername());
		List<TUser>  tempUsers  = new ArrayList<TUser>();
			 
		
		//String projOutcomeId = tProjdigest.getProjOutcomeId().trim();
		Integer id = tUser.getId();
		
		TUserExample tUserExample = new TUserExample();
		TUserExample.Criteria criteria = tUserExample.createCriteria();
		 
		 
		if(null != id)  {
			  criteria.andIdEqualTo(id);
		} 
		 
//		if(null !=userId) {
//			if(!userId.equals(admin)) {
//				if(""!=userId) {
//					criteria.andUserIdEqualTo(userId);
//				}
//			}
//		}
		tempUsers = tUserMapper.selectByExample(tUserExample);
        if(tempUsers.size() == 1) {
        	tUserMapper.updateByPrimaryKeySelective(tUser);
        }
        else {
        	throw new TipException("项目信息不是本人录入，没有权限修改！");
        }
		
	}
	
	public void delById(String id) {
		
		//List<TUser>  tempUsers  = new ArrayList<TUser>();
		
		TUserExample  tUserExample = new TUserExample();
		TUserExample.Criteria criteria = tUserExample.createCriteria();
		 
		if(null !=id ) {
			criteria.andIdEqualTo(Integer.parseInt(id));
        }
		
//		tempProjdigests = tProjdigestMapper.selectByExample(example);
//	    if(tempProjdigests.size() == 0) {
//	    	throw new TipException("项目信息不是本人录入，没有权限删除！");
//	    }
	    
		tUserMapper.deleteByExample(tUserExample);
	    
	    
	}
	
	public void delByIds(String[] ids) {
		
		//List<TUser>  tempUsers  = new ArrayList<TUser>();
		List<Integer> IntIds = new ArrayList<Integer>();
		
		TUserExample  tUserExample = new TUserExample();
		TUserExample.Criteria criteria = tUserExample.createCriteria();
		
		for(String id:  ids) {
			IntIds.add(Integer.valueOf(id));
		}
		if(null !=ids ) {
			 criteria.andIdIn(IntIds);
        }
		
//		tempProjdigests = tProjdigestMapper.selectByExample(example);
//	    if(tempProjdigests.size() == 0) {
//	    	throw new TipException("项目信息不是本人录入，没有权限删除！");
//	    }
	    
		tUserMapper.deleteByExample(tUserExample);
	    
	    
	}
	
	public TUser custLogin(String password) { 
		
		List<TUser> tUsers = new ArrayList<TUser>();
		
		LOGGER.info("cust password:={}",password);
		TUserExample tUserexample = new TUserExample();
		TUserExample.Criteria criteria = tUserexample.createCriteria();
		
		if(null != password)  {
			  criteria.andUsernameEqualTo(password);
		}
		
		 tUsers = tUserMapper.selectByExample(tUserexample);
		 if(tUsers.size() == 1 ) {
			 return tUsers.get(0);
		 }
		 else {
			 return null;
		 }
		
	}
	
	public TUser login(String username,String password) { 
		
		List<TUser> tUsers = new ArrayList<TUser>();
		
		LOGGER.info("cust username:={}",username);
		LOGGER.info("cust password:={}",password);
		
		TUserExample tUserexample = new TUserExample();
		TUserExample.Criteria criteria = tUserexample.createCriteria();
		
		if(null != username)  {
			  criteria.andUsernameEqualTo(username);
		}
		
		if(null != password)  {
			  criteria.andPasswordEqualTo(password);
		}
		
		 tUsers = tUserMapper.selectByExample(tUserexample);
		 if(tUsers.size() == 1 ) {
			 return tUsers.get(0);
		 }
		 else {
			 return null;
		 }
		
	}
	

}
