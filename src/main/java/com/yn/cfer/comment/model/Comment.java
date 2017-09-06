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
	private Integer userId;
	private String userName;
	private String userHeadUrl;
	private String content;
	private Integer type;
	private Integer replyUserId;
	private String replyUserName;
	private String replyUserHeadUrl;
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
	public Integer getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getReplyUserHeadUrl() {
		return replyUserHeadUrl;
	}
	public void setReplyUserHeadUrl(String replyUserHeadUrl) {
		this.replyUserHeadUrl = replyUserHeadUrl;
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
