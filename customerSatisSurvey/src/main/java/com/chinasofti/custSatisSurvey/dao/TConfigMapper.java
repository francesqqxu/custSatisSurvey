package com.chinasofti.custSatisSurvey.dao;

import com.chinasofti.custSatisSurvey.pojo.TConfig;
import com.chinasofti.custSatisSurvey.pojo.TConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TConfigMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	long countByExample(TConfigExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int deleteByExample(TConfigExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int insert(TConfig record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int insertSelective(TConfig record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	List<TConfig> selectByExample(TConfigExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	TConfig selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") TConfig record, @Param("example") TConfigExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") TConfig record, @Param("example") TConfigExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(TConfig record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_config
	 * @mbg.generated
	 */
	int updateByPrimaryKey(TConfig record);
}