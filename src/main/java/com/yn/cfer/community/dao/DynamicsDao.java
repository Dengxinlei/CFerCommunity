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
	public List<Dynamics> findByMemberIdDefault(@Param("memberId") Integer memberId, @Param("count") Integer count);
	public List<Dynamics> findByMemberIdHistory(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public int updateActionCount(Map<String, Object> map);
	public List<Dynamics> findLikeByNameDefault(@Param("owner") String owner, @Param("count") Integer count);
	public List<Dynamics> findLikeByNameHistory(@Param("owner") String owner,@Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<Dynamics> findAttentedMemberDynamicsListDefault(@Param("memberId") Integer memberId, @Param("count") Integer count);
	public List<Dynamics> findAttentedMemberDynamicsListLatest(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<Dynamics> findAttentedMemberDynamicsListHistory(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public int countByMemberId(Integer memberId);
	public List<Dynamics> findTop10(Integer memberId);
}

