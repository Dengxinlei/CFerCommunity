package com.yn.cfer.community.dao;
import com.yn.cfer.community.model.Box;
/**
 * @author user
 */
public interface BoxDao {
	public Box findByUserId(Integer userId);
}
