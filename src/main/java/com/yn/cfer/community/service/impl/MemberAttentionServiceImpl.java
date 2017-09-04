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
	public Integer attention(Integer memberId, Integer attentionMemberId, Integer type) throws BusinessException {
		type = type == null ? 1 : type; 
		Member m = memberDao.findById(memberId);
		if(m == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "会员不存在");
		}
		Member m2 = memberDao.findById(attentionMemberId);
		if(m2 == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "被关注会员不存在");
		}
		
		MemberAttention dbMa = memberAttentionDao.find(memberId, attentionMemberId);
		// 取消关注
		if(type != null && type.intValue() == 2) {
			if(dbMa == null) {
				throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "未添加过关注,无法取消");
			}
			if(dbMa.getStatus().intValue() == MemberAttention.STATUS_CANCEL) {
				throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已取消关注");
			}
			MemberAttention updateMa = new MemberAttention();
			updateMa.setId(dbMa.getId());
			updateMa.setStatus(MemberAttention.STATUS_CANCEL);
			memberAttentionDao.updateById(updateMa);
		} else {
			if(dbMa != null && dbMa.getStatus().intValue() != MemberAttention.STATUS_CANCEL) {
				throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已添加关注");
			} 
			Date now = new Date();
			MemberAttention dbMa2 = memberAttentionDao.find(attentionMemberId, memberId);
			if(dbMa != null && dbMa.getStatus().intValue() == 2) {
				if(dbMa2 != null) {
					dbMa.setStatus(MemberAttention.STATUS_EACH_OTHER);
				} else {
					dbMa.setStatus(MemberAttention.STATUS_ONLY_ONE);
				}
				memberAttentionDao.updateById(dbMa);
			} else {
				MemberAttention ma = new MemberAttention();
				ma.setAttentionMemberId(attentionMemberId);
				ma.setMemberId(memberId);
				ma.setMemberName(m.getName());
				ma.setMemberHeadUrl(m.getAvatar());
				ma.setAttentionMemberName(m2.getName());
				ma.setAttentionMemberHeadUrl(m2.getAvatar());
				ma.setCreateTime(now);
				if(dbMa2 != null) {
					// 将状态更新为互关注
					dbMa2.setStatus(MemberAttention.STATUS_EACH_OTHER);
					memberAttentionDao.updateById(dbMa2);
					ma.setStatus(MemberAttention.STATUS_EACH_OTHER);
				} else {
					ma.setStatus(MemberAttention.STATUS_ONLY_ONE);
				}
				memberAttentionDao.add(ma);
			}
		}
		return type;
	}
	public int attentionSelf(Integer memberId) throws BusinessException {
		Member m = memberDao.findById(memberId);
		if(m == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "会员不存在");
		}
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
			self.setCreateTime(new Date());
			self.setStatus(MemberAttention.STATUS_ONLY_ONE);
			return memberAttentionDao.add(self);
		} else {
			return 1;
		}
	}
	

}
