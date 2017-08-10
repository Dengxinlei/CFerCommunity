package com.yn.cfer.community.dao;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.community.model.DynamicsActionRecord;

/**
 * @author user
 */
public interface DynamicsActionRecordDao {
	public DynamicsActionRecord findByDynamicsIdAndUserId(@Param("dynamicsId") Integer dynamicsId, @Param("userId") Integer userId, @Param("type") Integer type);
	public int add(DynamicsActionRecord actionRecord);
	public int delete(@Param("dynamicsId") Integer dynamicsId, @Param("userId") Integer userId);
}
