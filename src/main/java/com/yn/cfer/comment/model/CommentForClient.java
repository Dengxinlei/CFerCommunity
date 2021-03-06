package com.yn.cfer.comment.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 */
public class CommentForClient {
	private Integer id;
	private String author;
	@SerializedName("user_id")
	private Integer userId;
	@SerializedName("head_url")
	private String headUrl;
	private String content;
	@SerializedName("publish_time")
	private Date publishTime;
	private String reply;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
