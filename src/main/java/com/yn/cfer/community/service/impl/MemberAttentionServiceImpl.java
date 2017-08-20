package com.yn.cfer.community.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.cfer.community.dao.MemberAttentionDao;
import com.yn.cfer.community.dao.MemberDao;
import com.yn.cfer.community.model.Member;
import com.yn.cfer.community.model.MemberAttention;
import com.yn.cfer.community.service.MemberAttentionService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
@Service
public class MemberAttentionServiceImpl implements MemberAttentionService {
	@Autowired
	private MemberAttentionDao memberAttentionDao;
	@Autowired
	private MemberDao memberDao;
	public boolean attention(Integer memberId, Integer attentionMemberId) throws BusinessException {
		Member m = memberDao.findById(memberId);
		if(m == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "会员不存在");
		}
		Member m2 = memberDao.findById(attentionMemberId);
		if(m2 == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "被关注会员不存在");
		}
		MemberAttention dbMa = memberAttentionDao.find(memberId, attentionMemberId);
		if(dbMa != null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已添加关注");
		}
		Date now = new Date();
		MemberAttention self = memberAttentionDao.find(memberId, memberId);
		// 没有关注自己,则关注自己
		if(self == null) {
			self = new MemberAttention();
			self.setAttentionMemberId(memberId);
			self.setMemberId(memberId);
			self.setMemberName(m.getName());
			self.setMemberHeadUrl(m.getAvatar());
			self.setAttentionMemberName(m.getName());
			self.setAttentionMemberHeadUrl(m.getAvatar());
			self.setCreateTime(now);
			self.setStatus(MemberAttention.STATUS_ONLY_ONE);
			memberAttentionDao.add(self);
		}
		
		MemberAttention ma = new MemberAttention();
		ma.setAttentionMemberId(attentionMemberId);
		ma.setMemberId(memberId);
		ma.setMemberName(m.getName());
		ma.setMemberHeadUrl(m.getAvatar());
		ma.setAttentionMemberName(m2.getName());
		ma.setAttentionMemberHeadUrl(m2.getAvatar());
		ma.setCreateTime(now);
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
