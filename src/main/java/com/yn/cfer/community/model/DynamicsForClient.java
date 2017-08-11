/**
 * 
 */
package com.yn.cfer.community.model;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 */
public class DynamicsForClient {
	private Integer id;
	@SerializedName("member_id")
	private Integer memberId;
	@SerializedName("cover_url")
	private String coverUrl;
	private String description;
	@SerializedName("head_url")
	private String headUrl;
	private String owner;
	@SerializedName("praised_count")
	private Integer praisedCount = 0;
	@SerializedName("comment_count")
	private Integer commentCount = 0;
	/**
	 * 1： 已赞    0： 未赞   默认未赞
	 */
	@SerializedName("is_praise")
	private Integer isPraise = 0;
	private String location;
	@SerializedName("pic_list")
	private List<String> picList;
	@SerializedName("publish_time")
	private Date publishTime;
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
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
	public Integer getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(Integer isPraise) {
		this.isPraise = isPraise;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<String> getPicList() {
		return picList;
	}
	public void setPicList(List<String> picList) {
		this.picList = picList;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
