/**
 * 
 */
package com.yn.cfer.community.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.FansForClient;
import com.yn.cfer.community.model.Picture;
import com.yn.cfer.community.model.Summary;
import com.yn.cfer.community.model.Token;
import com.yn.cfer.community.model.User;
import com.yn.cfer.community.model.UserDetail;
import com.yn.cfer.web.exceptions.BusinessException;

/**
 * @author user
 */
public interface DynamicsService {
	public UserDetail getUserDetailById(Integer userId) throws BusinessException;
	/**
	 * 获取热门列表
	 * @param lastId
	 * @param count
	 * @return
	 */
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer memberId, Integer count);
	/**
	 * 发布动态
	 * @param memberId
	 * @param description
	 * @param picUrls
	 * @return
	 */
	public DynamicsForClient publish(Integer memberId, String description, List<String> picUrls)  throws BusinessException;
	/**
	 * 获取动态详情
	 * @param dynamicsId
	 * @return
	 */
	public Map<String, Object> getDetail(Integer dynamicsId, Integer memberId, Integer count);
	/**
	 * 根据姓名模糊搜索动态
	 * @return
	 */
	public List<Map<String, Object>> searchLikeByName(Integer memberId, String name,Integer lastId, Integer count);
	/**
	 * 动态-点赞
	 * @param dynamicsId
	 * @param memberId
	 * @return 1 赞  2 取消赞
	 * @throws BusinessException
	 */
	public Integer praise(Integer dynamicsId, Integer memberId) throws BusinessException;
	/**
	 * 动态-举报
	 * @param dynamicsId
	 * @param memberId
	 * @return
	 * @throws BusinessException
	 */
	public boolean report(Integer dynamicsId, Integer memberId) throws BusinessException;
	/**
	 * 获取粉丝列表
	 * @param memberId
	 * @return
	 */
	public List<FansForClient> getFansList(Integer memberId, Integer lastId, Integer orientation, Integer count);
	/**
	 * 获取已关注列表
	 * @param memberId
	 * @return
	 */
	public List<FansForClient> getAttentedList(Integer memberId, Integer lastId, Integer orientation, Integer count);
	/**
	 * 获取已关注会员的动态列表
	 * @param memberId
	 * @param lastId
	 * @param orientation
	 * @param count
	 * @return
	 */
	public List<Map<String, Object>> attentionList(Integer memberId, Integer lastId, Integer orientation, Integer count);
	/**
	 * 获取会员信息总览
	 * @param memberId
	 * @return
	 */
	public Summary getUserSummary(Integer memberId, Integer attentionMemberId)  throws BusinessException;
	/**
	 * 获取个人发布的动态列表
	 * @param memberId
	 * @param lastId
	 * @param count
	 * @return
	 */
	public List<Map<String, Object>> getPersonalList(Integer memberId, Integer lastId, Integer count);
	
	public List<Picture> pictureList(Integer memberId, Integer lastId, Integer count);
	
	public List<FansForClient> recommendAttentionList(Integer memberId);
	
	public List<FansForClient> searchAttention(Integer memberId, String name);
	
	public Token findTokenByTokenKey(String token);
	public User findUserById(Integer userId);
	/**
	 * 更新用户昵称
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public boolean updateUserNames(Integer userId) throws BusinessException;
	/**
	 * 获取广告列表
	 * @return
	 */
	public List<JSONObject> getAdList();
}
