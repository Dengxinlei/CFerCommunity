/**
 * 
 */
package com.yn.cfer.community.service;

import java.util.List;
import java.util.Map;

import com.yn.cfer.community.model.DynamicsForClient;
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
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer userId, Integer count);
	/**
	 * 发布动态
	 * @param userId
	 * @param description
	 * @param picUrls
	 * @return
	 */
	public boolean publish(Integer userId, String description, List<String> picUrls);
	/**
	 * 获取动态详情
	 * @param dynamicsId
	 * @return
	 */
	public Map<String, Object> getDetail(Integer dynamicsId, Integer userId);
	/**
	 * 动态-点赞
	 * @param dynamicsId
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public boolean praise(Integer dynamicsId, Integer userId) throws BusinessException;
	/**
	 * 动态-举报
	 * @param dynamicsId
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public boolean report(Integer dynamicsId, Integer userId) throws BusinessException;
}
