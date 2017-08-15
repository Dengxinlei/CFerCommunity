package com.yn.cfer.comment.service.impl;
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
import com.yn.cfer.comment.service.CommentService;
import com.yn.cfer.community.dao.DynamicsActionRecordDao;
import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.dao.MemberDao;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsActionRecord;
import com.yn.cfer.community.model.Member;
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
	private MemberDao memberDao;
	@Autowired
	private DynamicsDao dynamicsDao;
	@Autowired
	private DynamicsActionRecordDao dynamicsActionRecordDao;
	public List<CommentForClient> getComments(Integer dynamicsId, Integer lastId, Integer count) {
		return buildCommentForClientList(commentDao.findHistory(dynamicsId, lastId, count));
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
			cc.setAuthor(comment.getMemberName());
			cc.setContent(comment.getContent());
			cc.setHeadUrl(comment.getMemberHeadUrl());
			cc.setPublishTime(comment.getCreateTime());
			cc.setReply(comment.getReplyMemberName());
			cc.setMemberId(comment.getMemberId());
			return cc;
		}
		return null;
	}
	@Transactional
	public boolean create(Integer dynamicsId, Integer memberId, String content, Integer replyMemberId) throws BusinessException{
		Member member = memberDao.findById(memberId);
		if(member == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_MEMBER_IS_NOT_EXISTS, "会员不存在");
		}
		Dynamics dynamics = dynamicsDao.findById(dynamicsId);
		if(dynamics == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		
		Comment create = new Comment();
		create.setContent(content);
		create.setDynamicsId(dynamicsId);
		create.setMemberHeadUrl(member.getAvatar());
		create.setMemberName(member.getName());
		create.setMemberId(memberId);
		create.setType(Comment.TYPE_DYNAMICS);
		if(replyMemberId != null) {
			Member replyMember = memberDao.findById(replyMemberId);
			if(replyMember == null) {
				throw new BusinessException(ErrorCode.ERROR_CODE_MEMBER_IS_NOT_EXISTS, "被回复会员不存在");
			}
			create.setReplyMemberHeadUrl(replyMember.getAvatar());
			create.setReplyMemberId(replyMemberId);
			create.setReplyMemberName(replyMember.getName());
		}
		int result = commentDao.add(create);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("commentCount", dynamics.getCommentCount());
		map.put("type", 1);
		dynamicsDao.updateActionCount(map);
		saveActionRecord(dynamicsId, memberId, DynamicsActionRecord.TYPE_COMMENT);
		return result == 1;
	}
	private int saveActionRecord(Integer dynamicsId, Integer memberId, Integer type) {
		DynamicsActionRecord actionRecord = new DynamicsActionRecord();
		actionRecord.setDynamicsId(dynamicsId);
		actionRecord.setType(type);
		actionRecord.setMemberId(memberId);
		return dynamicsActionRecordDao.add(actionRecord);
	}
}
