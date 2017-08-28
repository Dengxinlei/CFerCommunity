package com.yn.cfer.community.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.cfer.community.model.Member;
/**
 * @author user
 */
public interface MemberDao {
	public Member findById(Integer memberId);
	public List<Member> findTop10(Integer memberId);
	public List<Member> findByLikeName(@Param("id") Integer memberId, @Param("name") String name);
}
