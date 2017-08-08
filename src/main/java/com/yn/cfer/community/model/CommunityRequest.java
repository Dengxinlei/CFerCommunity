/**
 * 
 */
package com.yn.cfer.community.model;

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
}
