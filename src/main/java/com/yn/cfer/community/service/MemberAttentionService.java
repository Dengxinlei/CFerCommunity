package com.yn.cfer.community.service;

import com.yn.cfer.web.exceptions.BusinessException;

public interface MemberAttentionService {
	public Integer attention(Integer memberId, Integer attentionMemberId, Integer type) throws BusinessException;
}
