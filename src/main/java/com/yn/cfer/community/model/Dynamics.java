package com.yn.cfer.community.model;

import java.util.Date;
import java.util.List;

/**
 * @author DXL
 * 动态
 */
public class Dynamics {
	private Integer id;
	private Integer userId;
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
	private Integer status;
	private Date createTime;
	private Date updateTime;
	
	private List<DynamicsMaterial> materials;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
}
