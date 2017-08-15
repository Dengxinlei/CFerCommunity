package com.yn.cfer.community.model;

import java.util.Date;

public class MemberAttention {
	public final static Integer STATUS_ONLY_ONE = 0;		// 单方关注
	public final static Integer STATUS_EACH_OTHER = 1;		// 双向关注
	public final static Integer STATUS_CANCEL = 2;			// 取消关注
	private Integer id;
	private Integer memberId;
	private String memberName;
	private String memberHeadUrl;
	private Integer attentionMemberId;
	private String attentionMemberName;
	private String attentionMemberHeadUrl;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberHeadUrl() {
		return memberHeadUrl;
	}
	public void setMemberHeadUrl(String memberHeadUrl) {
		this.memberHeadUrl = memberHeadUrl;
	}
	public String getAttentionMemberName() {
		return attentionMemberName;
	}
	public void setAttentionMemberName(String attentionMemberName) {
		this.attentionMemberName = attentionMemberName;
	}
	public String getAttentionMemberHeadUrl() {
		return attentionMemberHeadUrl;
	}
	public void setAttentionMemberHeadUrl(String attentionMemberHeadUrl) {
		this.attentionMemberHeadUrl = attentionMemberHeadUrl;
	}
}
