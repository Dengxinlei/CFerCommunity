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
	
	public int add(MemberAttention memberAttention);
	
	public MemberAttention find(@Param("memberId") Integer memberId, @Param("attentionMemberId") Integer attentionMemberId);
	
	public int updateById(MemberAttention memberAttention);
}
