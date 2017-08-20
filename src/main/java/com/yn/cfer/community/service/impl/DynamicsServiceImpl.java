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
import com.yn.cfer.community.dao.DynamicsActionRecordDao;
import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.dao.DynamicsMaterialDao;
import com.yn.cfer.community.dao.MemberAttentionDao;
import com.yn.cfer.community.dao.MemberDao;
import com.yn.cfer.community.dao.UserDao;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsActionRecord;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.DynamicsMaterial;
import com.yn.cfer.community.model.FansForClient;
import com.yn.cfer.community.model.Member;
import com.yn.cfer.community.model.MemberAttention;
import com.yn.cfer.community.model.Picture;
import com.yn.cfer.community.model.Summary;
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
	private CommentDao commentDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private DynamicsActionRecordDao dynamicsActionRecordDao;
	@Autowired
	private MemberAttentionDao memberAttentionDao;
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer memberId, Integer count) {
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
		return buildDynamicsForClientList(dynamicsList, memberId);
	}
	private List<DynamicsForClient> buildDynamicsForClientList(List<Dynamics> dynamicsList, Integer memberId) {
		if(dynamicsList != null && dynamicsList.size() >= 1) {
			List<DynamicsForClient> destination = new ArrayList<DynamicsForClient>();
			DynamicsForClient dyClient = null;
			List<Integer> dynamicsIds = new ArrayList<Integer>();
			for(Dynamics dy : dynamicsList) {
				dyClient = buildDynamicsForClient(dy);
				dynamicsIds.add(dy.getId());
				destination.add(dyClient);
			}
			List<DynamicsActionRecord> actionRecords = dynamicsActionRecordDao.findByDynamicsIdsAndUserId(dynamicsIds, memberId, DynamicsActionRecord.TYPE_PRAISE);
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
			dyClient.setMemberId(dy.getMemberId());
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
	public DynamicsForClient publish(Integer memberId, String description, List<String> picUrls)  throws BusinessException {
		// 根据memberId查询会员
		Member member = memberDao.findById(memberId);
		if(member == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "会员不存在");
		}
		Dynamics dy = new Dynamics();
		dy.setDescription(description);
		dy.setHeadUrl(member.getAvatar());
		dy.setStatus(Dynamics.STATUS_NORMAL);
		dy.setMemberId(memberId);
		dy.setOwner(member.getName());
		dy.setCreateTime(new Date());
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
	
	public Map<String, Object> getDetail(Integer dynamicsId, Integer memberId, Integer count) {
		Map<String, Object> map = new HashMap<String, Object>();
		DynamicsForClient dc = buildDynamicsForClient(dynamicsDao.findById(dynamicsId));
		if(dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, memberId, DynamicsActionRecord.TYPE_PRAISE) != null) {
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
			cc.setAuthor(comment.getMemberName());
			cc.setContent(comment.getContent());
			cc.setHeadUrl(comment.getMemberHeadUrl());
			cc.setPublishTime(comment.getCreateTime());
			cc.setReply(comment.getReplyMemberName());
			cc.setMemberId(comment.getMemberId());
			return cc;
		}
		return null;
	}
	
	@Transactional
	public Integer praise(Integer dynamicsId, Integer memberId) throws BusinessException {
		Integer type = 1;
		Dynamics dy = dynamicsDao.findById(dynamicsId);
		if(dy == null) {
			throw new BusinessException(ErrorCode.ERROR_CODE_FAILURE, "动态不存在");
		}
		DynamicsActionRecord dar = dynamicsActionRecordDao.findByDynamicsIdAndUserId(dynamicsId, memberId, DynamicsActionRecord.TYPE_PRAISE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicsId", dynamicsId);
		map.put("praisedCount", dy.getPraisedCount());
		if(dar != null) {
			type = 2;
			map.put("type", 2);
			dynamicsDao.updateActionCount(map);
			dynamicsActionRecordDao.delete(dynamicsId, memberId);
		} else {
			map.put("type", 1);
			dynamicsDao.updateActionCount(map);
			saveActionRecord(dynamicsId, memberId, DynamicsActionRecord.TYPE_PRAISE);
		}
		return type;
	}
	private int saveActionRecord(Integer dynamicsId, Integer memberId, Integer type) {
		DynamicsActionRecord actionRecord = new DynamicsActionRecord();
		actionRecord.setDynamicsId(dynamicsId);
		actionRecord.setType(type);
		actionRecord.setMemberId(memberId);
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
	public List<FansForClient> getFansList(Integer memberId, Integer lastId, Integer orientation, Integer count) {
		List<MemberAttention> maList = null;
		if(lastId.intValue() == -1) {
			maList = memberAttentionDao.findFansListDefault(memberId, count);
		} else {
			// 顶部滑动
			if(orientation.intValue() == 1) {
				maList = memberAttentionDao.findFansListLatest(memberId, lastId, count);
			} else {
				maList = memberAttentionDao.findFansListHistory(memberId, lastId, count);
			}
		}
		return buildFansForClientList(maList, 2);
	}
	public List<FansForClient> getAttentedList(Integer memberId, Integer lastId, Integer orientation, Integer count) {
		List<MemberAttention> maList = null;
		if(lastId.intValue() == -1) {
			maList = memberAttentionDao.findAttentedListDefault(memberId, count);
		} else {
			// 顶部滑动
			if(orientation.intValue() == 1) {
				maList = memberAttentionDao.findAttentedListLatest(memberId, lastId, count);
			} else {
				maList = memberAttentionDao.findAttentedHistory(memberId, lastId, count);
			}
		}
		return buildFansForClientList(maList, 1);
	}
	
	private FansForClient buildFansForClient(MemberAttention ma, Integer type) {
		if(ma != null) {
			FansForClient fc = new FansForClient();
			// 关注
			if(type == 1) {
				fc.setHeadUrl(ma.getAttentionMemberHeadUrl());
				fc.setMemberId(ma.getAttentionMemberId());
				fc.setMemberName(ma.getAttentionMemberName());
			} else if(type == 2) {	// 粉丝
				fc.setHeadUrl(ma.getMemberHeadUrl());
				fc.setMemberId(ma.getMemberId());
				fc.setMemberName(ma.getMemberName());
			}
			fc.setId(ma.getId());
			fc.setStatus(ma.getStatus());
			return fc;
		}
		return null;
	}
	private List<FansForClient> buildFansForClientList(List<MemberAttention> maList, Integer type) {
		if(maList != null && maList.size() >= 1) {
			List<FansForClient> ffcList = new ArrayList<FansForClient>();
			FansForClient fc = null;
			for(MemberAttention ma : maList) {
				fc = buildFansForClient(ma, type);
				ffcList.add(fc);
			}
			return ffcList;
		}
		return null;
	}
	public List<Map<String, Object>> searchLikeByName(Integer memberId, String name, Integer lastId, Integer count) {
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
				list.add(getDetail(dy.getId(),memberId, 2));
			}
			return list;
		}
		return null;
	}
	public List<Map<String, Object>> attentionList(Integer memberId, Integer lastId, Integer orientation,
			Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findAttentedMemberDynamicsListDefault(memberId, count);
		} else {
			if(orientation == 1) {
				dyList = dynamicsDao.findAttentedMemberDynamicsListLatest(memberId, lastId, count);
			} else {
				dyList = dynamicsDao.findAttentedMemberDynamicsListHistory(memberId, lastId, count);
			}
		}
		if(dyList != null && dyList.size() >= 1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Dynamics dy : dyList) {
				// 关注里默认两条评论
				list.add(getDetail(dy.getId(),memberId, 2));
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
	public Summary getMemberSummary(Integer memberId) {
		Member member = memberDao.findById(memberId);
		if(member == null) {
			return null;
		}
		Summary sy = new Summary();
		sy.setGendar(member.getSex());
		sy.setAge(getAge(member.getBirthday()));
		sy.setMemberId(memberId);
		sy.setMemberName(member.getName());
		sy.setMemberHeadUrl(member.getAvatar());
		sy.setDynamicsCount(dynamicsDao.countByMemberId(memberId));
		sy.setFansCount(memberAttentionDao.countFansByAttentionMemberId(memberId));
		sy.setAttentedCount(memberAttentionDao.countAttentedByMemberId(memberId));
		return sy;
	}
	public List<Map<String, Object>> getPersonalList(Integer memberId, Integer lastId, Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findByMemberIdDefault(memberId, count);
		} else {
			dyList = dynamicsDao.findByMemberIdHistory(memberId, lastId, count);
		}
		if(dyList != null && dyList.size() >= 1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Dynamics dy : dyList) {
				// 关注里默认两条评论
				list.add(getDetail(dy.getId(),memberId, 2));
			}
			return list;
		}
		return null;
	}
	public List<Picture> pictureList(Integer memberId, Integer lastId, Integer count) {
		List<Dynamics> dyList = null;
		if(lastId == -1) {
			dyList = dynamicsDao.findByMemberIdDefault(memberId, count);
		} else {
			dyList = dynamicsDao.findByMemberIdHistory(memberId, lastId, count);
		}
		if(dyList != null && dyList.size() >= 1) {
			List<Picture> list = new ArrayList<Picture>();
			Picture pic = null;
			for(Dynamics dy : dyList) {
				pic = new Picture();
				pic.setDynamicsId(dy.getId());
				pic.setMemberId(dy.getMemberId());
				pic.setUrl(dy.getMaterials().get(0) != null ? dy.getMaterials().get(0).getUrl() : null);
				list.add(pic);
			}
			return list;
		}
		return null;
	}
}
