package com.yn.cfer.community.model;

import java.util.Date;

public class UserAttention {
	public final static Integer STATUS_ONLY_ONE = 0;		// 单方关注
	public final static Integer STATUS_EACH_OTHER = 1;		// 双向关注
	public final static Integer STATUS_CANCEL = 2;			// 取消关注
	private Integer id;
	private Integer userId;
	private String userName;
	private String userHeadUrl;
	private Integer attentionUserId;
	private String attentionUserName;
	private String attentionUserHeadUrl;
	/**
	 * 0=单关注  1=互关注 2=取消关注
	 */
	private Integer status;
	private Date createTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserHeadUrl() {
		return userHeadUrl;
	}
	public void setUserHeadUrl(String userHeadUrl) {
		this.userHeadUrl = userHeadUrl;
	}
	public Integer getAttentionUserId() {
		return attentionUserId;
	}
	public void setAttentionUserId(Integer attentionUserId) {
		this.attentionUserId = attentionUserId;
	}
	public String getAttentionUserName() {
		return attentionUserName;
	}
	public void setAttentionUserName(String attentionUserName) {
		this.attentionUserName = attentionUserName;
	}
	public String getAttentionUserHeadUrl() {
		return attentionUserHeadUrl;
	}
	public void setAttentionUserHeadUrl(String attentionUserHeadUrl) {
		this.attentionUserHeadUrl = attentionUserHeadUrl;
	}
}
