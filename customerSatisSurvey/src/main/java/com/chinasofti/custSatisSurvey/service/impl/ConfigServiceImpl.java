package com.chinasofti.custSatisSurvey.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinasofti.custSatisSurvey.dao.TConfigMapper;
import com.chinasofti.custSatisSurvey.pojo.TConfig;
import com.chinasofti.custSatisSurvey.pojo.TConfigExample;
import com.chinasofti.custSatisSurvey.pojo.TUser;
import com.chinasofti.custSatisSurvey.pojo.TUserExample;
import com.chinasofti.custSatisSurvey.service.ConfigService;
import com.chinasofti.custSatisSurvey.service.UserService;


@Service
public class ConfigServiceImpl implements ConfigService {
	
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	
	@Autowired
	TConfigMapper  tConfigMapper;
	@Autowired
	UserService userService;
	
	public List<TConfig> selectBySelectId(String selectId,String loginUser,String isAll){
		
		 List<TUser> tUsers = new ArrayList<TUser>();
		   
		 Boolean isGroupQA = false;
		 
		TConfigExample example = new TConfigExample();
		TConfigExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause("id asc");
		criteria.andConfigTypeEqualTo(selectId);
		
		if(null!= loginUser) {
			
			if(!loginUser.equals("")) {
				tUsers = userService.getUserListByName(loginUser);
				LOGGER.info(tUsers.get(0).getUsertype());
				if(null !=  tUsers) {
					if( !tUsers.get(0).getUsertype().equals("集团QA")) {
						
						criteria.andOptionTextEqualTo(tUsers.get(0).getLob());
					}
					else {
						isGroupQA = true;
					}
				}
			}
		}
		List<TConfig> tConfigs  =  tConfigMapper.selectByExample(example);
//		LOGGER.info("isAll={}",isAll);
//		if(isAll.equals("true") && isGroupQA) {
//			
//			TConfig tConfig = new TConfig();
//			tConfig.setId(0);
//			tConfig.setOptionText("所有");
//			tConfigs.add(tConfig);
//		}
//	    
		
		List<TConfig> newTConfigs = new ArrayList<TConfig>();
		if(isAll.equals("true") && isGroupQA) {
			TConfig tConfig =  new TConfig();
			tConfig.setId(0);
			tConfig.setOptionText("所有");
			tConfig.setOptionValue("所有");
			tConfig.setConfigType("lob");
			
			newTConfigs.add(tConfig);
			newTConfigs.addAll(tConfigs);
			return newTConfigs;
		}
		return tConfigs;
	}
	
	public String  selectLobByParentId(String loginUser,String isAll){
		
		 List<TUser> tUsers = new ArrayList<TUser>();
		   
		 Boolean isGroupQA = false;
		 
		TConfigExample example = new TConfigExample();
		TConfigExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause("id asc");
		criteria.andConfigTypeEqualTo("lob");
		
		if(null!= loginUser) {
			
			if(!loginUser.equals("")) {
				tUsers = userService.getUserListByName(loginUser);
				LOGGER.info(tUsers.get(0).getUsertype());
				if(null !=  tUsers) {
					if( !tUsers.get(0).getUsertype().equals("集团QA")) {
						
						criteria.andOptionTextEqualTo(tUsers.get(0).getLob());
					}
					else {
						isGroupQA = true;
					}
				}
			}
		}
		List<TConfig> tConfigs  =  tConfigMapper.selectByExample(example);
		
		List<Node> dataList = initList(tConfigs);
		
		// 节点列表（散列表，用于临时存储节点对象）  
		HashMap<String,Node> nodeList = new HashMap<String,Node>();  
		  // 根节点  
		Node root = new  Node();
		root.id = "0";
		root.text= "根节点";
		root.parentId = "";		
		nodeList.put(root.id, root);
	    // 根据结果集构造节点列表（存入散列表）  
		for (Iterator<Node> it = dataList.iterator(); it.hasNext();) {  
		   Node dataRecord =  it.next();  
		   Node node = new Node();  
		   node.id =  dataRecord.getId();
		   node.text =  dataRecord.getText();
		   node.parentId =  dataRecord.getParentId();
		   nodeList.put(node.id, node);  
		}  
		// 构造无序的多叉树  
		Set<Entry<String, Node>> entrySet = nodeList.entrySet();  
		for (Iterator<Entry<String, Node>> it = entrySet.iterator(); it.hasNext();) {  
			Node node = (Node) ((Map.Entry<String,Node>) it.next()).getValue();  
			if (node.parentId == null || node.parentId.equals("")) {  
			    root = node;  
			} else {  
			    ((Node) nodeList.get(node.parentId)).addChild(node);  
			}  
	    }  
	   // 输出无序的树形菜单的JSON字符串  
		System.out.println(root.toString());     
		return "["+root.toString()+"]";
}
	
	public List<Node> initList(List<TConfig>  tConfigs){
		
		List<Node> dataList = new ArrayList<Node>();  
		Node dataRecord;
		
		for(int i = 0; i < tConfigs.size(); i++) {
			TConfig tConfig =  tConfigs.get(i);
			dataRecord = new Node(); 
			dataRecord.setId(tConfig.getId().toString());
			dataRecord.setText(tConfig.getOptionText());
			String parentID = tConfig.getParent();
			if (StringUtils.isEmpty(parentID)) {
				parentID = "0";
			}
			dataRecord.setParentId(parentID);
			dataList.add(dataRecord);
		}
		return dataList;
	}	

	class  Node {
		private String id;
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public Children getChildren() {
			return children;
		}

		public void setChildren(Children children) {
			this.children = children;
		}

		private String text;
		private String parentId;
		
		/** 
		  * 孩子节点列表 
		  */  
		 private Children children = new Children();  
		   
		 // 先序遍历，拼接JSON字符串  
		 public String toString() {    
		  String result = //"["
		  "{" 
		  + "\"text\":\"" + text + "\","
		  + "\"id\":\"" + id +"\"";  
		  if (children != null && children.getSize() != 0) {
			if (result.contains("nodes")) {
				result += ",";
			}else{
				result += ",\"nodes\":" + children.toString();  
			}
		  }  
		  return result + "}";  
		 }  
		   
		 // 兄弟节点横向排序  
		 public void sortChildren() {  
		  if (children != null && children.getSize() != 0) {  
		   children.sortChildren();  
		  }  
		 }  
		   
		 // 添加孩子节点  
		 public void addChild(Node node) {  
		  this.children.addChild(node);  
		 }  
	}

	 class  Children {
		 
	  
			private List<Node> list = new ArrayList<Node>();
			
			public int getSize(){
				return list.size();
			}
			public void addChild(Node node){
				list.add(node);
			}
			
			 // 拼接孩子节点的JSON字符串  
			 public String toString() {  
			  String result = "[";    
			  for (Iterator<Node> it = list.iterator(); it.hasNext();) {  
			   result += ((Node) it.next()).toString();  
			   result += ",";  
			  }  
			  result = result.substring(0, result.length() - 1);  
			  result += "]";  
			  return result;  
			 }  
			   
			 // 孩子节点排序  
			 @SuppressWarnings("unchecked")
			public void sortChildren() {  
			  // 对本层节点进行排序  
			  // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器  
			  Collections.sort(list, new NodeIDComparator());  
			  // 对每个节点的下一层节点进行排序  
			  for (Iterator<Node> it = list.iterator(); it.hasNext();) {  
			   ((Node) it.next()).sortChildren();  
			  }  
			 }  
			   
			/** 
			 * 节点比较器 
			 */  
			class NodeIDComparator implements Comparator {  
			 // 按照节点编号比较  
				 public int compare(Object o1, Object o2) {  
				  int j1 = Integer.parseInt(((Node)o1).id);  
				     int j2 = Integer.parseInt(((Node)o2).id);  
				     return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));  
				 }   
			}   
		}
 

	public List<TConfig> nextLevel(int id) {
		//return navidao.nextLevel(id);             //通过菜单id查处它的子菜单节点
		return null;
	}

	public List<Map<String,Object>> getTree(List<TConfig> tConfigs) {            //传入的参数是查询出来的所有节点数据 
		
		String name;
		List<Map<String,Object>> listmap= new ArrayList<Map<String,Object>>();	
		List<TConfig> tConfigs_child = new ArrayList<TConfig>();			
		for(TConfig tConfig : tConfigs)
		{
			tConfigs_child=nextLevel(tConfig.getId());
			Map<String, Object> xmap= new HashMap<String, Object>();
			name="\""+tConfig.getOptionText()+"\"";
			String id="\""+tConfig.getId()+"\"";
			xmap.put("text",name);
			xmap.put("id", id);
			if(tConfigs_child.size()>0) {
				xmap.put("nodes", getTree(tConfigs_child));				
			}
			listmap.add(xmap);		
		}
		
		String results = listmap.toString().replace("=",":");
		//JSONObject res = new JSONObject();
		//JSONArray jsonArray = JSONArray.fromObject(results);
		return listmap;
		
	}
 
}