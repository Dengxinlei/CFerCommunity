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
	/**
	 * 1：上拉  2：下拉 默认2
	 */
	private Integer orientation;
	private String description;
	@SerializedName("dest_user_id")
	private Integer destUserId;
	@SerializedName("attention_user_id")
	private Integer attentionUserId;
	@SerializedName("pic_urls")
	private List<String> picUrls;
	@SerializedName("dynamics_id")
	private Integer dynamicsId;
	private String name;
	private Integer type;
	private String token;
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
	public List<String> getPicUrls() {
		return picUrls;
	}
	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}
	public Integer getDynamicsId() {
		return dynamicsId;
	}
	public void setDynamicsId(Integer dynamicsId) {
		this.dynamicsId = dynamicsId;
	}
	public Integer getAttentionUserId() {
		return attentionUserId;
	}
	public void setAttentionUserId(Integer attentionUserId) {
		this.attentionUserId = attentionUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDestUserId() {
		return destUserId;
	}
	public void setDestUserId(Integer destUserId) {
		this.destUserId = destUserId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
