package com.yn.cfer.comment.service;

import java.util.List;

import com.yn.cfer.comment.model.CommentForClient;

/**
 * @author user
 */
public interface CommentService {
	public List<CommentForClient> getComments(Integer dynamicsId, Integer lastId, Integer count);
}
