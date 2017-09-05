package com.yn.cfer.community.service;

import com.yn.cfer.web.exceptions.BusinessException;

public interface UserAttentionService {
	public Integer attention(Integer memberId, Integer attentionMemberId, Integer type) throws BusinessException;
	public int attentionSelf(Integer memberId) throws BusinessException;
}
