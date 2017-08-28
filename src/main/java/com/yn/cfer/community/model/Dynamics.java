package com.yn.cfer.community.model;

import java.util.Date;
import java.util.List;

/**
 * @author DXL
 * 动态
 */
public class Dynamics {
	public final static Integer STATUS_NORMAL = 0;				// 正常
	public final static Integer STATUS_CLOSE = 0;				// 屏蔽
	private Integer id;
	private Integer memberId;
	/**
	 * 发布者
	 */
	private String owner;
	/**
	 * 发布者头像
	 */
	private String headUrl;
	/**
	 * 发表动态时文字内容
	 */
	private String description;
	/**
	 * 位置
	 */
	private String location;
	/**
	 * 状态
	 */
	private Integer status = 0;
	/**
	 * 点赞数量
	 */
	private Integer praisedCount = 0;
	/**
	 * 评论数量
	 */
	private Integer commentCount = 0;
	/**
	 * 举报数量
	 */
	private Integer reportCount = 0;
	/**
	 * 用户手机号码
	 */
	private String phone;
	private Date createTime;
	private Date updateTime;
	
	private List<DynamicsMaterial> materials;
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
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public List<DynamicsMaterial> getMaterials() {
		return materials;
	}
	public void setMaterials(List<DynamicsMaterial> materials) {
		this.materials = materials;
	}
	public Integer getPraisedCount() {
		return praisedCount;
	}
	public void setPraisedCount(Integer praisedCount) {
		this.praisedCount = praisedCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getReportCount() {
		return reportCount;
	}
	public void setReportCount(Integer reportCount) {
		this.reportCount = reportCount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
