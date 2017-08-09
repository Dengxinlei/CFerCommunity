package com.yn.cfer.comment.model;

import java.util.List;

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
}
