package com.yn.cfer.community.dao;

import com.yn.cfer.community.model.User;
/**
 * @author user
 */
public interface TokenDao {
	public User findById(Integer userId);
}
