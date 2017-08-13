package com.yn.cfer.community.model;

import java.util.Date;

public class MemberAttention {
	public final static Integer STATUS_ONLY_ONE = 0;		// 单方关注
	public final static Integer STATUS_EACH_OTHER = 1;		// 双向关注
	public final static Integer STATUS_CANCEL = 2;			// 取消关注
	private Integer id;
	private Integer memberId;
	private Integer attentionMemberId;
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
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getAttentionMemberId() {
		return attentionMemberId;
	}
	public void setAttentionMemberId(Integer attentionMemberId) {
		this.attentionMemberId = attentionMemberId;
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
}
