package com.yn.cfer.community.model;

import java.util.Date;

/**
 * @author user
 */
public class Member {
	private Integer Id;
	private String avatar;
	private Integer boxId;
	private Integer height;
	private Integer memberDays;
	private Date memberStartTime;
	private Date memberEndTime;
	private Integer memberRemainNum;
	private Integer memberType;
	private String name;
	private String phone;
	private String pinCode;
	private Integer sex;
	private Integer status;
	private Integer weight;
	private Date birthday;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getBoxId() {
		return boxId;
	}
	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getMemberDays() {
		return memberDays;
	}
	public void setMemberDays(Integer memberDays) {
		this.memberDays = memberDays;
	}
	public Date getMemberStartTime() {
		return memberStartTime;
	}
	public void setMemberStartTime(Date memberStartTime) {
		this.memberStartTime = memberStartTime;
	}
	public Date getMemberEndTime() {
		return memberEndTime;
	}
	public void setMemberEndTime(Date memberEndTime) {
		this.memberEndTime = memberEndTime;
	}
	public Integer getMemberRemainNum() {
		return memberRemainNum;
	}
	public void setMemberRemainNum(Integer memberRemainNum) {
		this.memberRemainNum = memberRemainNum;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
