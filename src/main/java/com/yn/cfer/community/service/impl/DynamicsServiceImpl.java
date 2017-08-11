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
import com.yn.cfer.comment.model.Comment;
import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.community.dao.DynamicsActionRecordDao;
import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.dao.DynamicsMaterialDao;
import com.yn.cfer.community.dao.UserDao;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsActionRecord;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.DynamicsMaterial;
import com.yn.cfer.community.model.User;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;

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
	@Autowired
	private DynamicsActionRecordDao dynamicsActionRecordDao;
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer userId, Integer count) {
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
		return buildDynamicsForClientList(dynamicsList, userId);
	}
	private List<DynamicsForClient> buildDynamicsForClientList(List<Dynamics> dynamicsList, Integer userId) {
		if(dynamicsList != null && dynamicsList.size() >= 1) {
			List<DynamicsForClient> destination = new ArrayList<DynamicsForClient>();
			DynamicsForClient dyClient = null;
			List<Integer> dynamicsIds = new ArrayList<Integer>();
			for(Dynamics dy : dynamicsList) {
				dyClient = buildDynamicsForClient(dy);
				dynamicsIds.add(dy.getId());
				destination.add(dyClient);
			}
			List<DynamicsActionRecord> actionRecords = dynamicsActionRecordDao.findByDynamicsIdsAndUserId(dynamicsIds, userId, DynamicsActionRecord.TYPE_PRAISE);
			if(actionRecords != null && actionRecords.size() >= 1) {
				for(int i = 0; i < destination.size(); i++) {
					for(int j = 0; j < actionRecords.size(); j++) {
						if(destination.get(i).getId().intValue() == actionRecords.get(j).getDynamicsId().intValue()) {
							// 已赞
							destination.get(i).setIsPraise(1);		
						}
					}
				}
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
	
	public Map<String, Object> getDetail(Integer dynamicsId, Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		DynamicsForClient dc = buildDynamicsForClient(dynamicsDao.findById(dynamicsId));
		if(dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, userId, DynamicsActionRecord.TYPE_PRAISE) != null) {
			// 已赞
			dc.setIsPraise(1);
		}
		map.put("dynamics", dc);
		map.put("commentList", buildCommentForClientList(commentDao.findDefault(dynamicsId, 20)));
		return map;
	}
	
	private List<CommentForClient> buildCommentForClientList(List<Comment> commentList) {
		if(commentList != null) {
			List<CommentForClient> cfcList = new ArrayList<CommentForClient>();
			CommentForClient cfc = null;
			for(Comment c : commentList) {
				cfc = buildCommentForClient(c);
				cfcList.add(cfc);
			}
			return cfcList;
		}
		return null;
	}
	private CommentForClient buildCommentForClient(Comment comment) {
		if(comment != null) {
			CommentForClient cc = new CommentForClient();
			cc.setAuthor(comment.getUserName());
			cc.setContent(comment.getContent());
			cc.setHeadUrl(comment.getUserHeadUrl());
			cc.setPublishTime(comment.getCreateTime());
			cc.setReply(comment.getReplyUserName());
			cc.setUserId(comment.getUserId());
			return cc;
		}
		return null;
	}
	
	@Transactional
	public boolean praise(Integer dynamicsId, Integer userId) throws BusinessException {
		Dynamics dy = dynamicsDao.findById(dynamicsId);
		if(dy == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		DynamicsActionRecord dar = dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, userId, DynamicsActionRecord.TYPE_PRAISE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("praisedCount", dy.getPraisedCount());
		if(dar != null) {
			map.put("type", 2);
			dynamicsDao.updateActionCount(map);
			dynamicsActionRecordDao.delete(dynamicsId, userId);
		} else {
			map.put("type", 1);
			dynamicsDao.updateActionCount(map);
			saveActionRecord(dynamicsId, userId, DynamicsActionRecord.TYPE_PRAISE);
		}
		return true;
	}
	private int saveActionRecord(Integer dynamicsId, Integer userId, Integer type) {
		DynamicsActionRecord actionRecord = new DynamicsActionRecord();
		actionRecord.setDynamicsId(dynamicsId);
		actionRecord.setType(type);
		actionRecord.setUserId(userId);
		dynamicsActionRecordDao.add(actionRecord);
		return 0;
	}
	@Transactional
	public boolean report(Integer dynamicsId, Integer userId) throws BusinessException {
		Dynamics dy = dynamicsDao.findById(dynamicsId);
		if(dy == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		DynamicsActionRecord dar = dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, userId, DynamicsActionRecord.TYPE_REPORT);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("reportCount", dy.getReportCount());
		if(dar == null) {
			map.put("type", 1);
			dynamicsDao.updateActionCount(map);
			saveActionRecord(dynamicsId, userId, DynamicsActionRecord.TYPE_REPORT);
		} else {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已经举报过该动态");
		}
		return true;
	}
}
