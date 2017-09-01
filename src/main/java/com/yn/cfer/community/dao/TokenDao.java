package com.yn.cfer.community.dao;

import com.yn.cfer.community.model.Token;
/**
 * @author user
 */
public interface TokenDao {
	public Token findByTokenKey(String token);
}
