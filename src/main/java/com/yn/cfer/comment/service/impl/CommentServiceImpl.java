package com.yn.cfer.comment.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.cfer.comment.dao.CommentDao;
import com.yn.cfer.comment.model.Comment;
import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.comment.service.CommentService;
import com.yn.cfer.community.dao.DynamicsActionRecordDao;
import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.dao.MemberDao;
import com.yn.cfer.community.dao.UserDao;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsActionRecord;
import com.yn.cfer.community.model.Member;
import com.yn.cfer.community.model.User;
import com.yn.cfer.community.model.UserDetail;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
/**
 * @author user
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DynamicsService dynamicsService;
	@Autowired
	private DynamicsDao dynamicsDao;
	@Autowired
	private DynamicsActionRecordDao dynamicsActionRecordDao;
	public List<CommentForClient> getComments(Integer dynamicsId, Integer lastId, Integer count, Integer orientation) {
		List<Comment> commentList = null;
		if(lastId == -1) {
			commentList = commentDao.findDefault(dynamicsId, count);
		} else {
			if(orientation == 1) {
				commentList = commentDao.findLatest(dynamicsId, lastId, count);
			} else {
				commentList = commentDao.findHistory(dynamicsId, lastId, count);
			}
		}
		return buildCommentForClientList(commentList);
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
			cc.setId(comment.getId());
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
	public CommentForClient create(Integer dynamicsId, Integer userId, String content, Integer replyUserId) throws BusinessException{
		User user = userDao.findById(userId);
		if(user == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_USER_IS_NOT_EXISTS, "用户不存在");
		}
		Dynamics dynamics = dynamicsDao.findById(dynamicsId);
		if(dynamics == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		UserDetail ud = dynamicsService.getUserDetailById(userId);
		Comment create = new Comment();
		create.setContent(content);
		create.setDynamicsId(dynamicsId);
		create.setUserHeadUrl(ud.getHeadUrl());
		create.setUserName(ud.getName());
		create.setUserId(userId);
		create.setType(Comment.TYPE_DYNAMICS);
		if(replyUserId != null && replyUserId.intValue() != -1) {
			User replyUser = userDao.findById(replyUserId);
			if(replyUser == null) {
				throw new BusinessException(ErrorCode.ERROR_CODE_USER_IS_NOT_EXISTS, "被回复用户不存在");
			}
			UserDetail ud2 = dynamicsService.getUserDetailById(replyUserId);
			create.setReplyUserHeadUrl(ud2.getHeadUrl());
			create.setReplyUserId(replyUserId);
			create.setReplyUserName(ud2.getName());
		}
		commentDao.add(create);
		create.setCreateTime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("commentCount", dynamics.getCommentCount());
		map.put("type", 1);
		dynamicsDao.updateActionCount(map);
		saveActionRecord(dynamicsId, userId, DynamicsActionRecord.TYPE_COMMENT);
		return buildCommentForClient(create);
	}
	private int saveActionRecord(Integer dynamicsId, Integer userId, Integer type) {
		DynamicsActionRecord actionRecord = new DynamicsActionRecord();
		actionRecord.setDynamicsId(dynamicsId);
		actionRecord.setType(type);
		actionRecord.setUserId(userId);
		return dynamicsActionRecordDao.add(actionRecord);
	}
}
