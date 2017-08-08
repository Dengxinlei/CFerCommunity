/**
 * 
 */
package com.yn.cfer.community.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 *
 */
public class CommunityRequest {
	@SerializedName("last_id")
	private Integer lastId;
	private Integer count;
	private Integer orientation;
	
	private String description;
	@SerializedName("user_id")
	private Integer userId;
	@SerializedName("pic_urls")
	private List<String> picUrls;
	public Integer getLastId() {
		return lastId;
	}
	public void setLastId(Integer lastId) {
		this.lastId = lastId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getOrientation() {
		return orientation;
	}
	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<String> getPicUrls() {
		return picUrls;
	}
	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}
}
