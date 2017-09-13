package com.yn.cfer.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.community.model.UserAttention;

public interface UserAttentionDao {
	/**
	 * 查询已关注列表
	 * @param userId
	 * @return
	 */
	public List<UserAttention> findByUserId(Integer userId);
	
	/**
	 * 查询粉丝列表
	 * @param userId
	 * @return
	 */
	public List<UserAttention> findByAttentionUserId(Integer userId);
	public List<UserAttention> findFansListDefault(@Param("userId") Integer userId, @Param("count") Integer count);
	public List<UserAttention> findFansListLatest(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<UserAttention> findFansListHistory(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	
	public List<UserAttention> findAttentedListDefault(@Param("userId") Integer userId, @Param("count") Integer count);
	public List<UserAttention> findAttentedListLatest(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<UserAttention> findAttentedHistory(@Param("userId") Integer userId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public int add(UserAttention memberAttention);
	
	public UserAttention find(@Param("userId") Integer userId, @Param("attentionUserId") Integer attentionUserId);
	
	public int updateById(UserAttention userAttention);
	public int updateByUserId(UserAttention userAttention);
	public int updateByAttentionUserId(UserAttention userAttention);
	public int countFansByAttentionUserId(Integer userId);
	public int countAttentedByUserId(Integer userId);
	public List<UserAttention> findFansTop10(Integer userId);
}
