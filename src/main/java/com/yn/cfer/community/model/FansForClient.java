package com.yn.cfer.community.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 *
 */
public class FansForClient {
	private Integer id;
	@SerializedName("member_id")
	private Integer memberId;
	@SerializedName("member_name")
	private String memberName;
	@SerializedName("head_url")
	private String headUrl;
	/**
	 * 0=单关注  1=双向关注  2=取消
	 */
	private Integer status;
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
