package com.yn.cfer.community.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 *
 */
public class FansForClient {
	private Integer id;
	@SerializedName("user_id")
	private Integer userId;
	@SerializedName("user_name")
	private String userName;
	@SerializedName("head_url")
	private String headUrl;
	/**
	 * 0=单关注  1=双向关注  2=取消
	 */
	private Integer status;
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
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
