package com.yn.cfer.community.dao;
import com.yn.cfer.community.model.Member;
/**
 * @author user
 */
public interface MemberDao {
	public Member findById(Integer memberId);
}
