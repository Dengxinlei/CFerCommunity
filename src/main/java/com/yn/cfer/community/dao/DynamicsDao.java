/**
 * 
 */
package com.yn.cfer.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.community.model.Dynamics;

/**
 * @author user
 */
public interface DynamicsDao {
	public int add(Dynamics dynamics);
	/**
	 * 查询20条数据， 不满20则全部返回
	 * @return
	 */
	public List<Dynamics> findDefault(@Param("count")Integer count);
	/**
	 * 根据lastId，顶部拉去最新的数据
	 * 查询20条数据， 不满20则全部返回
	 * @param LastId
	 * @return
	 */
	public List<Dynamics> findLatest(@Param("lastId") Integer lastId, @Param("count")Integer count);
	/**
	 * 根据lastId，底部拉去最新的数据
	 * 查询20条数据， 不满20则全部返回
	 * @param LastId
	 * @return
	 */
	public List<Dynamics> findHistory(@Param("lastId") Integer lastId, @Param("count")Integer count);
	public Dynamics findById(Integer id);
	public List<Dynamics> findByUserIdDefault(@Param("userId") Integer userId, @Param("count") Integer count);
	public List<Dynamics> findByUserIdHistory(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public int updateActionCount(Map<String, Object> map);
	public int updateByUserId(Dynamics dynamics);
	public List<Dynamics> findLikeByNameDefault(@Param("owner") String owner, @Param("count") Integer count);
	public List<Dynamics> findLikeByNameHistory(@Param("owner") String owner,@Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<Dynamics> findAttentedUserDynamicsListDefault(@Param("userId") Integer userId, @Param("count") Integer count);
	public List<Dynamics> findAttentedUserDynamicsListLatest(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<Dynamics> findAttentedUserDynamicsListHistory(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public int countByUserId(Integer userId);
	public List<Dynamics> findTop10(Integer userId);
}

