/**
 * 
 */
package com.yn.cfer.community.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author 总览
 */
public class Summary {
	@SerializedName("member_id")
	private Integer memberId;
	@SerializedName("member_name")
	private String memberName;
	@SerializedName("member_head_url")
	private String memberHeadUrl;
	@SerializedName("fans_count")
	private Integer fansCount;
	@SerializedName("attented_count")
	private Integer attentedCount;
	@SerializedName("dynamics_count")
	private Integer dynamicsCount;
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
	public String getMemberHeadUrl() {
		return memberHeadUrl;
	}
	public void setMemberHeadUrl(String memberHeadUrl) {
		this.memberHeadUrl = memberHeadUrl;
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
}
