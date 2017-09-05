package com.yn.cfer.comment.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 *
 */
public class CommentRequest {
	@SerializedName("last_id")
	private Integer lastId;
	private Integer count;
	@SerializedName("dynamics_id")
	private Integer dynamicsId;
	@SerializedName("reply_user_id")
	private Integer replyUserId;
	private String content;
	private String token;
	/**
	 * 1：上拉  2：下拉 默认2
	 */
	private Integer orientation;
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
	public Integer getDynamicsId() {
		return dynamicsId;
	}
	public void setDynamicsId(Integer dynamicsId) {
		this.dynamicsId = dynamicsId;
	}
	public Integer getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getOrientation() {
		return orientation;
	}
	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
