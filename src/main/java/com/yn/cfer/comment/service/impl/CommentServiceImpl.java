package com.yn.cfer.comment.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.cfer.comment.dao.CommentDao;
import com.yn.cfer.comment.model.Comment;
import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.comment.service.CommentService;
/**
 * @author user
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
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
}
