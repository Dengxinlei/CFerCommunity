/**
 * 
 */
package com.yn.cfer.community.service;

import java.util.List;
import java.util.Map;

import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.FansForClient;
import com.yn.cfer.web.exceptions.BusinessException;

/**
 * @author user
 */
public interface DynamicsService {
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
	public DynamicsForClient publish(Integer memberId, String description, List<String> picUrls);
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
	 * @return
	 * @throws BusinessException
	 */
	public boolean praise(Integer dynamicsId, Integer memberId) throws BusinessException;
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
}
