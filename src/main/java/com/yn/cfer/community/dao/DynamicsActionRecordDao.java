package com.yn.cfer.community.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.community.model.DynamicsActionRecord;
/**
 * @author user
 */
public interface DynamicsActionRecordDao {
	public DynamicsActionRecord findByDynamicsIdAndUserId(@Param("dynamicsId") Integer dynamicsId, @Param("userId") Integer userId, @Param("type") Integer type);
	public int add(DynamicsActionRecord actionRecord);
	public int delete(@Param("dynamicsId") Integer dynamicsId, @Param("userId") Integer userId);
	public List<DynamicsActionRecord> findByDynamicsIdsAndUserId(@Param("dynamicsIds") List<Integer> dynamicsIds, @Param("userId") Integer userId, @Param("type") Integer type);
	
}
