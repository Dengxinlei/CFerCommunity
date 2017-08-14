package com.yn.cfer.comment.model;

import java.util.Date;
/**
 * @author user
 */
public class Comment {
	public final static Integer TYPE_DYNAMICS = 0;
	public final static Integer TYPE_COMMENT = 1;
	private Integer id;
	private Integer dynamicsId;
	private Integer memberId;
	private String memberName;
	private String memberHeadUrl;
	private String content;
	private Integer type;
	private Integer replyMemberId;
	private String replyMemberName;
	private String replyMemberHeadUrl;
	private Date createTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDynamicsId() {
		return dynamicsId;
	}
	public void setDynamicsId(Integer dynamicsId) {
		this.dynamicsId = dynamicsId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	public Integer getReplyMemberId() {
		return replyMemberId;
	}
	public void setReplyMemberId(Integer replyMemberId) {
		this.replyMemberId = replyMemberId;
	}
	public String getReplyMemberName() {
		return replyMemberName;
	}
	public void setReplyMemberName(String replyMemberName) {
		this.replyMemberName = replyMemberName;
	}
	public String getReplyMemberHeadUrl() {
		return replyMemberHeadUrl;
	}
	public void setReplyMemberHeadUrl(String replyMemberHeadUrl) {
		this.replyMemberHeadUrl = replyMemberHeadUrl;
	}
}
