/**
 * 
 */
package com.yn.cfer.community.service;

import java.util.List;

import com.yn.cfer.community.model.DynamicsForClient;

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
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer count);
}
