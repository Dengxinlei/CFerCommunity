package com.yn.cfer.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.cfer.community.dao.MemberAttentionDao;
import com.yn.cfer.community.model.MemberAttention;
import com.yn.cfer.community.service.MemberAttentionService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
@Service
public class MemberAttentionServiceImpl implements MemberAttentionService {
	@Autowired
	private MemberAttentionDao memberAttentionDao;
	public boolean attention(Integer memberId, Integer attentionMemberId) throws BusinessException {
		MemberAttention dbMa = memberAttentionDao.find(memberId, attentionMemberId);
		if(dbMa != null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已添加关注");
		}
		MemberAttention ma = new MemberAttention();
		ma.setAttentionMemberId(attentionMemberId);
		ma.setMemberId(memberId);
		MemberAttention dbMa2 = memberAttentionDao.find(attentionMemberId, memberId);
		if(dbMa2 != null) {
			// 将状态更新为互关注
			dbMa2.setStatus(MemberAttention.STATUS_EACH_OTHER);
			memberAttentionDao.updateById(dbMa2);
			ma.setStatus(MemberAttention.STATUS_EACH_OTHER);
		} else {
			ma.setStatus(MemberAttention.STATUS_ONLY_ONE);
		}
		memberAttentionDao.add(ma);
		return true;
	}
	

}
