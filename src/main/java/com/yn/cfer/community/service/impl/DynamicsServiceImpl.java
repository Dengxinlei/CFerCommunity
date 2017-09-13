/**
 * 
 */
package com.yn.cfer.community.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.cfer.comment.dao.CommentDao;
import com.yn.cfer.comment.model.Comment;
import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.community.dao.CoachDao;
import com.yn.cfer.community.dao.DynamicsActionRecordDao;
import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.dao.DynamicsMaterialDao;
import com.yn.cfer.community.dao.MemberDao;
import com.yn.cfer.community.dao.TokenDao;
import com.yn.cfer.community.dao.UserAttentionDao;
import com.yn.cfer.community.dao.UserDao;
import com.yn.cfer.community.model.Coach;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsActionRecord;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.DynamicsMaterial;
import com.yn.cfer.community.model.FansForClient;
import com.yn.cfer.community.model.Member;
import com.yn.cfer.community.model.Picture;
import com.yn.cfer.community.model.Summary;
import com.yn.cfer.community.model.Token;
import com.yn.cfer.community.model.User;
import com.yn.cfer.community.model.UserAttention;
import com.yn.cfer.community.model.UserDetail;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;

/**
 * @author user
 */
@Service
public class DynamicsServiceImpl implements DynamicsService {
	@Autowired
	private DynamicsDao dynamicsDao;
	@Autowired
	private DynamicsMaterialDao dynamicsMaterialDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TokenDao tokenDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private CoachDao coachDao;
	@Autowired
	private DynamicsActionRecordDao dynamicsActionRecordDao;
	@Autowired
	private UserAttentionDao userAttentionDao;
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer UserId, Integer count) {
		// TODO Auto-generated method stub
		List<Dynamics> dynamicsList = null;
		if(lastId.intValue() == -1) {
			dynamicsList = dynamicsDao.findDefault(count);
		} else {
			if(orientation.intValue() == 1) {
				dynamicsList = dynamicsDao.findLatest(lastId, count);
			} else if(orientation.intValue() == 2) {
				dynamicsList = dynamicsDao.findHistory(lastId, count);
			}
		}
		return buildDynamicsForClientList(dynamicsList, UserId);
	}
	private List<DynamicsForClient> buildDynamicsForClientList(List<Dynamics> dynamicsList, Integer UserId) {
		if(dynamicsList != null && dynamicsList.size() >= 1) {
			List<DynamicsForClient> destination = new ArrayList<DynamicsForClient>();
			DynamicsForClient dyClient = null;
			List<Integer> dynamicsIds = new ArrayList<Integer>();
			for(Dynamics dy : dynamicsList) {
				dyClient = buildDynamicsForClient(dy);
				dynamicsIds.add(dy.getId());
				destination.add(dyClient);
			}
			List<DynamicsActionRecord> actionRecords = dynamicsActionRecordDao.findByDynamicsIdsAndUserId(dynamicsIds, UserId, DynamicsActionRecord.TYPE_PRAISE);
			if(actionRecords != null && actionRecords.size() >= 1) {
				for(int i = 0; i < destination.size(); i++) {
					for(int j = 0; j < actionRecords.size(); j++) {
						if(destination.get(i).getId().intValue() == actionRecords.get(j).getDynamicsId().intValue()) {
							// 已赞
							destination.get(i).setIsPraise(1);		
						}
					}
				}
			}
			return destination;
		}
		return null;
	}
	private DynamicsForClient buildDynamicsForClient(Dynamics dy) {
		if(dy != null) {
			DynamicsForClient dyClient = new DynamicsForClient();
			dyClient.setId(dy.getId());
			dyClient.setLocation(dy.getLocation());
			dyClient.setHeadUrl(dy.getHeadUrl());
			dyClient.setUserId(dy.getUserId());
			dyClient.setOwner(dy.getOwner());
			dyClient.setDescription(dy.getDescription());
			dyClient.setCoverUrl(dy.getMaterials() != null && dy.getMaterials().size() >= 1? dy.getMaterials().get(0).getUrl() : null);
			dyClient.setPraisedCount(dy.getPraisedCount());
			dyClient.setCommentCount(dy.getCommentCount());
			dyClient.setPublishTime(dy.getCreateTime());
			List<String> urls = new ArrayList<String>();
			if(dy.getMaterials() != null && dy.getMaterials().size() >= 1) {
				for(DynamicsMaterial dm : dy.getMaterials()) {
					urls.add(dm.getUrl());
				}
			}
			dyClient.setPicList(urls);
			return dyClient;
		}
		return null;
	}
	@Transactional
	public DynamicsForClient publish(Integer userId, String description, List<String> picUrls)  throws BusinessException {
		UserDetail ud = getUserDetailById(userId);
		Dynamics dy = new Dynamics();
		dy.setDescription(description);
		dy.setHeadUrl(ud.getHeadUrl());
		dy.setStatus(Dynamics.STATUS_NORMAL);
		dy.setUserId(userId);
		dy.setOwner(ud.getNickName());
		dy.setCreateTime(new Date());
		dy.setPhone(ud.getMobile());
		dynamicsDao.add(dy);
		List<DynamicsMaterial> dmList = new ArrayList<DynamicsMaterial>();
		DynamicsMaterial dm = null;
		for(String picUrl : picUrls) {
			dm = new DynamicsMaterial();
			dm.setDynamicsId(dy.getId());
			dm.setUrl(picUrl);
			dm.setType(DynamicsMaterial.TYPE_PIC);
			dmList.add(dm);
		}
		dynamicsMaterialDao.addBatch(dmList);
		dy.setMaterials(dmList);
		return buildDynamicsForClient(dy);
	}
	
	public Map<String, Object> getDetail(Integer dynamicsId, Integer UserId, Integer count) {
		Map<String, Object> map = new HashMap<String, Object>();
		DynamicsForClient dc = buildDynamicsForClient(dynamicsDao.findById(dynamicsId));
		if(dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, UserId, DynamicsActionRecord.TYPE_PRAISE) != null) {
			// 已赞
			dc.setIsPraise(1);
		}
		map.put("dynamics", dc);
		map.put("commentList", buildCommentForClientList(commentDao.findDefault(dynamicsId, count)));
		return map;
	}
	
	private List<CommentForClient> buildCommentForClientList(List<Comment> commentList) {
		if(commentList != null) {
			List<CommentForClient> cfcList = new ArrayList<CommentForClient>();
			CommentForClient cfc = null;
			for(Comment c : commentList) {
				cfc = buildCommentForClient(c);
				cfcList.add(cfc);
			}
			return cfcList;
		}
		return null;
	}
	private CommentForClient buildCommentForClient(Comment comment) {
		if(comment != null) {
			CommentForClient cc = new CommentForClient();
			cc.setAuthor(comment.getUserName());
			cc.setContent(comment.getContent());
			cc.setHeadUrl(comment.getUserHeadUrl());
			cc.setPublishTime(comment.getCreateTime());
			cc.setReply(comment.getReplyUserName());
			cc.setUserId(comment.getUserId());
			return cc;
		}
		return null;
	}
	
	@Transactional
	public Integer praise(Integer dynamicsId, Integer UserId) throws BusinessException {
		Integer type = 1;
		Dynamics dy = dynamicsDao.findById(dynamicsId);
		if(dy == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		DynamicsActionRecord dar = dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, UserId, DynamicsActionRecord.TYPE_PRAISE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("praisedCount", dy.getPraisedCount());
		if(dar != null) {
			type = 2;
			map.put("type", 2);
			dynamicsDao.updateActionCount(map);
			dynamicsActionRecordDao.delete(dynamicsId, UserId);
		} else {
			map.put("type", 1);
			dynamicsDao.updateActionCount(map);
			saveActionRecord(dynamicsId, UserId, DynamicsActionRecord.TYPE_PRAISE);
		}
		return type;
	}
	private int saveActionRecord(Integer dynamicsId, Integer UserId, Integer type) {
		DynamicsActionRecord actionRecord = new DynamicsActionRecord();
		actionRecord.setDynamicsId(dynamicsId);
		actionRecord.setType(type);
		actionRecord.setUserId(UserId);
		return dynamicsActionRecordDao.add(actionRecord);
	}
	@Transactional
	public boolean report(Integer dynamicsId, Integer userId) throws BusinessException {
		Dynamics dy = dynamicsDao.findById(dynamicsId);
		if(dy == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		DynamicsActionRecord dar = dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, userId, DynamicsActionRecord.TYPE_REPORT);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("reportCount", dy.getReportCount());
		if(dar == null) {
			map.put("type", 1);
			dynamicsDao.updateActionCount(map);
			saveActionRecord(dynamicsId, userId, DynamicsActionRecord.TYPE_REPORT);
		} else {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "已经举报过该动态");
		}
		return true;
	}
	public List<FansForClient> getFansList(Integer UserId, Integer lastId, Integer orientation, Integer count) {
		List<UserAttention> maList = null;
		if(lastId.intValue() == -1) {
			maList = userAttentionDao.findFansListDefault(UserId, count);
		} else {
			// 顶部滑动
			if(orientation.intValue() == 1) {
				maList = userAttentionDao.findFansListLatest(UserId, lastId, count);
			} else {
				maList = userAttentionDao.findFansListHistory(UserId, lastId, count);
			}
		}
		return buildFansForClientList(maList, 2);
	}
	public List<FansForClient> getAttentedList(Integer UserId, Integer lastId, Integer orientation, Integer count) {
		List<UserAttention> maList = null;
		if(lastId.intValue() == -1) {
			maList = userAttentionDao.findAttentedListDefault(UserId, count);
		} else {
			// 顶部滑动
			if(orientation.intValue() == 1) {
				maList = userAttentionDao.findAttentedListLatest(UserId, lastId, count);
			} else {
				maList = userAttentionDao.findAttentedHistory(UserId, lastId, count);
			}
		}
		return buildFansForClientList(maList, 1);
	}
	/**
	 * 
	 * @param ma
	 * @param type 1=关注  2=粉丝
	 * @return
	 */
	private FansForClient buildFansForClient(UserAttention ua, Integer type) {
		if(ua != null) {
			FansForClient fc = new FansForClient();
			// 关注
			if(type == 1) {
				fc.setHeadUrl(ua.getAttentionUserHeadUrl());
				fc.setUserId(ua.getAttentionUserId());
				fc.setUserName(ua.getAttentionUserName());
			} else if(type == 2) {	// 粉丝
				fc.setHeadUrl(ua.getUserHeadUrl());
				fc.setUserId(ua.getUserId());
				fc.setUserName(ua.getUserName());
			}
			fc.setId(ua.getId());
			fc.setStatus(ua.getStatus() == null ? -1 : ua.getStatus());
			return fc;
		}
		return null;
	}
	private List<FansForClient> buildFansForClientList(List<UserAttention> maList, Integer type) {
		if(maList != null && maList.size() >= 1) {
			List<FansForClient> ffcList = new ArrayList<FansForClient>();
			FansForClient fc = null;
			for(UserAttention ma : maList) {
				fc = buildFansForClient(ma, type);
				ffcList.add(fc);
			}
			return ffcList;
		}
		return null;
	}
	public List<Map<String, Object>> searchLikeByName(Integer UserId, String name, Integer lastId, Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findLikeByNameDefault(name, count);
		} else {
			dyList = dynamicsDao.findLikeByNameHistory(name, lastId, count);
		}
		
		if(dyList != null && dyList.size() >= 1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Dynamics dy : dyList) {
				// 关注里默认两条评论
				list.add(getDetail(dy.getId(),UserId, 2));
			}
			return list;
		}
		return null;
	}
	public List<Map<String, Object>> attentionList(Integer UserId, Integer lastId, Integer orientation,
			Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findAttentedUserDynamicsListDefault(UserId, count);
		} else {
			if(orientation == 1) {
				dyList = dynamicsDao.findAttentedUserDynamicsListLatest(UserId, lastId, count);
			} else {
				dyList = dynamicsDao.findAttentedUserDynamicsListHistory(UserId, lastId, count);
			}
		}
		if(dyList != null && dyList.size() >= 1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Dynamics dy : dyList) {
				// 关注里默认两条评论
				list.add(getDetail(dy.getId(),UserId, 2));
			}
			return list;
		}
		return null;
	}
	// 由出生日期获得年龄  
    private Integer getAge(Date birthDay) {
    	if(birthDay == null) {
    		return 0;
    	}
        Calendar cal = Calendar.getInstance();  
        if (cal.before(birthDay)) {  
            return 0;  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
        int age = yearNow - yearBirth;  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }  
	public Summary getUserSummary(Integer userId, Integer attentionUserId) throws BusinessException{
		UserDetail ud = getUserDetailById(attentionUserId);
		Summary sy = new Summary();
		sy.setGendar(ud.getSex());
		sy.setAge(getAge(ud.getBirthday()));
		sy.setUserId(attentionUserId);
		sy.setUserName(ud.getNickName());
		sy.setRealName(ud.getName());
		sy.setUserHeadUrl(ud.getHeadUrl());
		sy.setDynamicsCount(dynamicsDao.countByUserId(attentionUserId));
		sy.setFansCount(userAttentionDao.countFansByAttentionUserId(attentionUserId));
		sy.setAttentedCount(userAttentionDao.countAttentedByUserId(attentionUserId));
		UserAttention ma = userAttentionDao.find(userId, attentionUserId);
		if(ma != null) {
			sy.setIsAttented(1);
		}
		return sy;
	}
	public List<Map<String, Object>> getPersonalList(Integer UserId, Integer lastId, Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findByUserIdDefault(UserId, count);
		} else {
			dyList = dynamicsDao.findByUserIdHistory(UserId, lastId, count);
		}
		if(dyList != null && dyList.size() >= 1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Dynamics dy : dyList) {
				// 关注里默认两条评论
				list.add(getDetail(dy.getId(),UserId, 2));
			}
			return list;
		}
		return null;
	}
	public List<Picture> pictureList(Integer UserId, Integer lastId, Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findByUserIdDefault(UserId, count);
		} else {
			dyList = dynamicsDao.findByUserIdHistory(UserId, lastId, count);
		}
		if(dyList != null && dyList.size() >= 1) {
			List<Picture> list = new ArrayList<Picture>();
			Picture pic = null;
			for(Dynamics dy : dyList) {
				pic = new Picture();
				pic.setDynamicsId(dy.getId());
				pic.setUserId(dy.getUserId());
				pic.setUrl(dy.getMaterials().get(0) != null ? dy.getMaterials().get(0).getUrl() : null);
				list.add(pic);
			}
			return list;
		}
		return null;
	}
	
	public List<FansForClient> recommendAttentionList(Integer userId) {
		int dynamicsCount = dynamicsDao.countByUserId(userId);
		int attentedCount = userAttentionDao.countAttentedByUserId(userId);
		if(dynamicsCount == 0 && attentedCount == 0) {
			List<FansForClient> ffcList = buildFansForClientList(userAttentionDao.findFansTop10(userId), 1);
			if(ffcList == null || ffcList.size() == 0) {
				List<Dynamics> dyList =dynamicsDao.findTop10(userId);
				// 构建fansForClient
				UserAttention ua = null;
				List<UserAttention> maList = new ArrayList<UserAttention>();
				if(dyList != null && dyList.size() >= 1) {
					for(Dynamics dy : dyList) {
						ua = new UserAttention();
						ua.setAttentionUserId(dy.getUserId());
						ua.setAttentionUserHeadUrl(dy.getHeadUrl());
						ua.setAttentionUserName(dy.getOwner());
						ua.setStatus(-1);
						maList.add(ua);
					}
				} else {
					// 根据注册时间升序
					List<Member> mbList = memberDao.findTop10(userId);
					for(Member mb : mbList) {
						ua = new UserAttention();
						ua.setAttentionUserId(mb.getId());
						ua.setAttentionUserHeadUrl(mb.getAvatar());
						ua.setAttentionUserName(mb.getName());
						ua.setStatus(-1);
						maList.add(ua);
					}
				}
				return buildFansForClientList(maList, 1);
			} else {
				return ffcList;
			}
		}
		return null;
	}
	public List<FansForClient> searchAttention(Integer userId, String name) {
		List<Member> mbList = memberDao.findByLikeName(userId, name);
		UserAttention ma = null;
		List<UserAttention> maList = new ArrayList<UserAttention>();
		if(mbList != null && mbList.size() >= 1) {
			for(Member mb : mbList) {
				ma = new UserAttention();
				ma.setAttentionUserId(mb.getId());
				ma.setAttentionUserHeadUrl(mb.getAvatar());
				ma.setAttentionUserName(mb.getName());
				ma.setStatus(-1);
				maList.add(ma);
			}
		}
		return buildFansForClientList(maList, 1);
	}
	public Token findTokenByTokenKey(String token) {
		return tokenDao.findByTokenKey(token);
	}
	public User findUserById(Integer userId) {
		return userDao.findById(userId);
	}
	/**
	 * 获取用户名称和头像等信息
	 */
	public UserDetail getUserDetailById(Integer userId)  throws BusinessException {
		User u = userDao.findById(userId);
		if(u == null) {
			 throw new BusinessException(ErrorCode.ERROR_CODE_USER_IS_NOT_EXISTS, "用户不存在");
		}
		UserDetail detail = null;
		if(u.getUserType() == 3) {
			detail = new UserDetail();
			Coach c = coachDao.findById(u.getRelatedId());
			if(c == null) {
				throw new BusinessException(ErrorCode.ERROR_CODE_COACH_IS_NOT_EXISTS, "教练不存在");
			}
			detail = new UserDetail();
			detail.setHeadUrl(c.getAvatar());
			detail.setName(c.getName());
			detail.setMobile(c.getPhone());
			detail.setBirthday(c.getBirthday());
			detail.setSex(c.getSex());
			detail.setNickName(c.getNickName());
		} else if(u.getUserType() == 4) {
			Member m = memberDao.findById(u.getRelatedId());
			if(m == null) {
				throw new BusinessException(ErrorCode.ERROR_CODE_MEMBER_IS_NOT_EXISTS, "会员不存在");
			}
			detail = new UserDetail();
			detail.setHeadUrl(m.getAvatar());
			detail.setName(m.getName());
			detail.setMobile(m.getPhone());
			detail.setBirthday(m.getBirthday());
			detail.setSex(m.getSex());
			detail.setNickName(m.getNickName());
		} else {
			throw new BusinessException(ErrorCode.ERROR_CODE_USER_TYPE_ERROR, "非法用户类型");
		}
		return detail;
	}
	@Transactional
	public boolean updateUserNames(Integer userId) throws BusinessException {
		UserDetail ud = getUserDetailById(userId);
		updateDynamicsUserName(userId, ud);
		updateCommentUserName(userId, ud);
		updateUAUserName(userId, ud);
		return true;
	}
	private int updateDynamicsUserName(Integer userId, UserDetail ud) {
		Dynamics dynamics = new Dynamics();
		dynamics.setUserId(userId);
		dynamics.setOwner(ud.getNickName());
		dynamics.setHeadUrl(ud.getHeadUrl());
		return dynamicsDao.updateByUserId(dynamics);
	}
	private int updateCommentUserName(Integer userId, UserDetail ud) {
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setUserName(ud.getNickName());
		comment.setUserHeadUrl(ud.getHeadUrl());
		comment.setReplyUserId(userId);
		comment.setReplyUserName(ud.getNickName());
		comment.setReplyUserHeadUrl(ud.getHeadUrl());
		commentDao.updateByReplyUserId(comment);
		return commentDao.updateByUserId(comment);
	}
	// 更新用户关注-用户名
	private int updateUAUserName(Integer userId, UserDetail ud) {
		UserAttention ua = new UserAttention();
		ua.setUserId(userId);
		ua.setUserName(ud.getNickName());
		ua.setUserHeadUrl(ud.getHeadUrl());
		ua.setAttentionUserHeadUrl(ud.getHeadUrl());
		ua.setAttentionUserId(userId);
		ua.setAttentionUserName(ud.getNickName());
		userAttentionDao.updateByUserId(ua);
		return userAttentionDao.updateByAttentionUserId(ua);
	}
}
