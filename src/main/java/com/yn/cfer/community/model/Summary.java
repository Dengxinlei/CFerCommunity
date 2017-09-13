/**
 * 
 */
package com.yn.cfer.community.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author 总览
 */
public class Summary {
	@SerializedName("user_id")
	private Integer userId;
	@SerializedName("user_name")
	private String userName;
	@SerializedName("real_name")
	private String realName;
	@SerializedName("user_head_url")
	private String userHeadUrl;
	private Integer age;
	/**
	 * 0: 男   1： 女
	 */
	private Integer gendar;
	@SerializedName("fans_count")
	private Integer fansCount;
	@SerializedName("attented_count")
	private Integer attentedCount;
	@SerializedName("dynamics_count")
	private Integer dynamicsCount;
	/**
	 * 是否已关注  0：未关注   1：已关注
	 */
	@SerializedName("is_attented")
	private Integer isAttented = 0;
	
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
	public Integer getFansCount() {
		return fansCount;
	}
	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}
	public Integer getAttentedCount() {
		return attentedCount;
	}
	public void setAttentedCount(Integer attentedCount) {
		this.attentedCount = attentedCount;
	}
	public Integer getDynamicsCount() {
		return dynamicsCount;
	}
	public void setDynamicsCount(Integer dynamicsCount) {
		this.dynamicsCount = dynamicsCount;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getGendar() {
		return gendar;
	}
	public void setGendar(Integer gendar) {
		this.gendar = gendar;
	}
	public Integer getIsAttented() {
		return isAttented;
	}
	public void setIsAttented(Integer isAttented) {
		this.isAttented = isAttented;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
}
