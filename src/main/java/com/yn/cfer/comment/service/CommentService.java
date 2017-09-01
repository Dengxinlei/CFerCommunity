package com.yn.cfer.comment.service;

import java.util.List;

import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.web.exceptions.BusinessException;

/**
 * @author user
 */
public interface CommentService {
	public List<CommentForClient> getComments(Integer dynamicsId, Integer lastId, Integer count, Integer orientation);
	public CommentForClient create(Integer dynamicsId, Integer memberId, String content, Integer replyMemberId) throws BusinessException;
}
