package com.yn.cfer.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.community.model.MemberAttention;

public interface MemberAttentionDao {
	/**
	 * 查询已关注列表
	 * @param memberId
	 * @return
	 */
	public List<MemberAttention> findByMemberId(Integer memberId);
	
	/**
	 * 查询粉丝列表
	 * @param memberId
	 * @return
	 */
	public List<MemberAttention> findByAttentionMemberId(Integer memberId);
	public List<MemberAttention> findFansListDefault(@Param("memberId") Integer memberId, @Param("count") Integer count);
	public List<MemberAttention> findFansListLatest(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<MemberAttention> findFansListHistory(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	
	public List<MemberAttention> findAttentedListDefault(@Param("memberId") Integer memberId, @Param("count") Integer count);
	public List<MemberAttention> findAttentedListLatest(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public List<MemberAttention> findAttentedHistory(@Param("memberId") Integer memberId, @Param("lastId") Integer lastId, @Param("count") Integer count);
	public int add(MemberAttention memberAttention);
	
	public MemberAttention find(@Param("memberId") Integer memberId, @Param("attentionMemberId") Integer attentionMemberId);
	
	public int updateById(MemberAttention memberAttention);
	public int countFansByAttentionMemberId(Integer memberId);
	public int countAttentedByMemberId(Integer memberId);
	public List<MemberAttention> findFansTop10(Integer memberId);
}
