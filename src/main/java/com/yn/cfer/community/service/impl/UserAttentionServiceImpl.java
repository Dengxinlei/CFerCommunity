package com.yn.cfer.community.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.cfer.common.sts.utils.LockUtils;
import com.yn.cfer.community.dao.UserAttentionDao;
import com.yn.cfer.community.dao.UserDao;
import com.yn.cfer.community.model.User;
import com.yn.cfer.community.model.UserAttention;
import com.yn.cfer.community.model.UserDetail;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.community.service.UserAttentionService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
@Service
public class UserAttentionServiceImpl implements UserAttentionService {
	@Autowired
	private DynamicsService dynamicsService;
	@Autowired
	private UserAttentionDao userAttentionDao;
	@Autowired
	private UserDao userDao;
	public Integer attention(Integer userId, Integer attentionUserId, Integer type) throws BusinessException {
		type = type == null ? 1 : type;
		User u = userDao.findById(userId);
		if(u == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "用户不存在");
		}
		User u2 = userDao.findById(attentionUserId);
		if(u2 == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "被关注用户不存在");
		}
		UserAttention dbUa = userAttentionDao.find(userId, attentionUserId);
		// 取消关注
		if(type != null && type.intValue() == 2) {
			if(dbUa == null) {
				throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "未添加过关注,无法取消");
			}
			if(dbUa.getStatus().intValue() == UserAttention.STATUS_CANCEL) {
				throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已取消关注");
			}
			UserAttention updateUa = new UserAttention();
			updateUa.setId(dbUa.getId());
			updateUa.setStatus(UserAttention.STATUS_CANCEL);
			userAttentionDao.updateById(updateUa);
			UserAttention dbUa2 = userAttentionDao.find(attentionUserId, userId);
			if(dbUa2 != null && dbUa2.getStatus().intValue() == UserAttention.STATUS_EACH_OTHER) {
				updateUa = new UserAttention();
				updateUa.setId(dbUa2.getId());
				updateUa.setStatus(UserAttention.STATUS_ONLY_ONE);
				userAttentionDao.updateById(updateUa);
			}
		} else {
			if(dbUa != null && dbUa.getStatus().intValue() != UserAttention.STATUS_CANCEL) {
				throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已添加关注");
			} 
			Date now = new Date();
			UserAttention dbUa2 = userAttentionDao.find(attentionUserId, userId);
			if(dbUa != null && dbUa.getStatus().intValue() == UserAttention.STATUS_CANCEL) {
				if(dbUa2 != null && dbUa2.getStatus().intValue() == UserAttention.STATUS_ONLY_ONE) {
					dbUa.setStatus(UserAttention.STATUS_EACH_OTHER);
					dbUa2.setStatus(UserAttention.STATUS_EACH_OTHER);
					userAttentionDao.updateById(dbUa2);
				} else {
					dbUa.setStatus(UserAttention.STATUS_ONLY_ONE);
				}
				userAttentionDao.updateById(dbUa);
			} else {
				UserDetail ud1 = dynamicsService.getUserDetailById(userId);
				UserDetail ud2 = dynamicsService.getUserDetailById(attentionUserId);
				UserAttention ma = new UserAttention();
				ma.setAttentionUserId(attentionUserId);
				ma.setUserId(userId);
				ma.setUserName(ud1.getNickName());
				ma.setUserHeadUrl(ud1.getHeadUrl());
				ma.setAttentionUserName(ud2.getNickName());
				ma.setAttentionUserHeadUrl(ud2.getHeadUrl());
				ma.setCreateTime(now);
				if(dbUa2 != null) {
					// 将状态更新为互关注
					dbUa2.setStatus(UserAttention.STATUS_EACH_OTHER);
					userAttentionDao.updateById(dbUa2);
					ma.setStatus(UserAttention.STATUS_EACH_OTHER);
				} else {
					ma.setStatus(UserAttention.STATUS_ONLY_ONE);
				}
				userAttentionDao.add(ma);
			}
		}
		return type;
	}
	public int attentionSelf(Integer userId) throws BusinessException {
		String lockName = "attentionSelf_"+userId;
		int status = LockUtils.getLock(lockName, "锁定");
		if(status == 1) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "操作锁定中");
		}
		User m = userDao.findById(userId);
		if(m == null) {
			LockUtils.releaseLock(lockName);
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "用户不存在");
		}
		UserAttention self = userAttentionDao.find(userId, userId);
		// 没有关注自己,则关注自己
		if(self == null) {
			UserDetail ud = dynamicsService.getUserDetailById(userId);
			self = new UserAttention();
			self.setAttentionUserId(userId);
			self.setUserId(userId);
			self.setUserName(ud.getName());
			self.setUserHeadUrl(ud.getHeadUrl());
			self.setAttentionUserName(ud.getName());
			self.setAttentionUserHeadUrl(ud.getHeadUrl());
			self.setCreateTime(new Date());
			self.setStatus(UserAttention.STATUS_ONLY_ONE);
			int saveResult = userAttentionDao.add(self);
			LockUtils.releaseLock(lockName);
			return saveResult;
		} else {
			LockUtils.releaseLock(lockName);
			return 1;
		}
	}
	

}
