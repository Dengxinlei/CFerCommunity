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
	@SerializedName("member_id")
	private Integer memberId;
	@SerializedName("reply_member_id")
	private Integer replyMemberId;
	private String content;
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
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getReplyMemberId() {
		return replyMemberId;
	}
	public void setReplyMemberId(Integer replyMemberId) {
		this.replyMemberId = replyMemberId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
