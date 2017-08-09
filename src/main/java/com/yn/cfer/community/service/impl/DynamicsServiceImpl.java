/**
 * 
 */
package com.yn.cfer.community.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.cfer.comment.dao.CommentDao;
import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.dao.DynamicsMaterialDao;
import com.yn.cfer.community.dao.UserDao;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.DynamicsMaterial;
import com.yn.cfer.community.model.User;
import com.yn.cfer.community.service.DynamicsService;

/**
 * @author user
 */
@Service
public class DynamicsServiceImpl implements DynamicsService {
	@Autowired
	private DynamicsDao dynamicsDao;
	@Autowired
	private DynamicsMaterialDao dynamicsMaterialDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CommentDao commentDao;
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer count) {
		// TODO Auto-generated method stub
		List<Dynamics> dynamicsList = null;
		if(lastId.intValue() == -1) {
			dynamicsList = dynamicsDao.findDefault(count);
		} else {
			if(orientation.intValue() == 1) {
				dynamicsList = dynamicsDao.findLatest(lastId, count);
			} else if(orientation.intValue() == 2) {
				dynamicsList = dynamicsDao.findHistory(lastId, count);
			}
		}
		return buildDynamicsForClientList(dynamicsList);
	}
	private List<DynamicsForClient> buildDynamicsForClientList(List<Dynamics> dynamicsList) {
		if(dynamicsList != null && dynamicsList.size() >= 1) {
			List<DynamicsForClient> destination = new ArrayList<DynamicsForClient>();
			DynamicsForClient dyClient = null;
			for(Dynamics dy : dynamicsList) {
				dyClient = buildDynamicsForClient(dy);
				destination.add(dyClient);
			}
			return destination;
		}
		return null;
	}
	private DynamicsForClient buildDynamicsForClient(Dynamics dy) {
		if(dy != null) {
			DynamicsForClient dyClient = new DynamicsForClient();
			dyClient.setId(dy.getId());
			dyClient.setLocation(dy.getLocation());
			dyClient.setHeadUrl(dy.getHeadUrl());
			dyClient.setUserId(dy.getUserId());
			dyClient.setOwner(dy.getOwner());
			dyClient.setDescription(dy.getDescription());
			dyClient.setCoverUrl(dy.getMaterials() != null && dy.getMaterials().size() >= 1? dy.getMaterials().get(0).getUrl() : null);
			dyClient.setPraisedCount(dy.getPraisedCount());
			dyClient.setCommentCount(dy.getCommentCount());
			dyClient.setPublishTime(dy.getCreateTime());
			List<String> urls = new ArrayList<String>();
			for(DynamicsMaterial dm : dy.getMaterials()) {
				urls.add(dm.getUrl());
			}
			dyClient.setPicList(urls);
			return dyClient;
		}
		return null;
	}
	@Transactional
	public boolean publish(Integer userId, String description, List<String> picUrls) {
		// 根据用户Id查询用户
		User user = userDao.findById(userId);
		Dynamics dy = new Dynamics();
		dy.setDescription(description);
		dy.setHeadUrl(user.getHeadUrl());
		dy.setStatus(Dynamics.STATUS_NORMAL);
		dy.setUserId(userId);
		dy.setOwner(user.getUserName());
		dynamicsDao.add(dy);
		List<DynamicsMaterial> dmList = new ArrayList<DynamicsMaterial>();
		DynamicsMaterial dm = null;
		for(String picUrl : picUrls) {
			dm = new DynamicsMaterial();
			dm.setDynamicsId(dy.getId());
			dm.setUrl(picUrl);
			dm.setType(DynamicsMaterial.TYPE_PIC);
			dmList.add(dm);
		}
		dynamicsMaterialDao.addBatch(dmList);
		return true;
	}
	
	public Map<String, Object> getDetail(Integer dynamicsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamics", buildDynamicsForClient(dynamicsDao.findById(dynamicsId)));
		map.put("commentList", commentDao.findDefault(dynamicsId, 20));
		return map;
	}
}
