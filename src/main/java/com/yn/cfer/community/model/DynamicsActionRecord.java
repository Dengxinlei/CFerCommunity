package com.yn.cfer.community.model;

import java.util.Date;

/**
 * @author user
 */
public class DynamicsActionRecord {
	public final static Integer TYPE_PRAISE = 0;
	public final static Integer TYPE_COMMENT = 1;
	public final static Integer TYPE_REPORT = 2;
	private Integer id;
	private Integer dynamicsId;
	private Integer memberId;
	/**
	 * 0=点赞 1=评论  2=举报
	 */
	private Integer type;
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
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
}
