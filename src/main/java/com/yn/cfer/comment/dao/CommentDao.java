package com.yn.cfer.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.comment.model.Comment;

/**
 * @author user
 */
public interface CommentDao {
	public List<Comment> findDefault(@Param("dynamicsId") Integer dynamicsId, @Param("count") Integer count);
	public List<Comment> findHistory(@Param("dynamicsId") Integer dynamicsId, @Param("lastId") Integer lastId, @Param("count") Integer count);
}
