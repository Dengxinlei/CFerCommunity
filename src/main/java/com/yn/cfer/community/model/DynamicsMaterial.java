package com.yn.cfer.community.model;

import java.util.Date;

/**
 * @author DXL
 * 动态素材
 */
public class DynamicsMaterial {
	public final static Integer TYPE_PIC = 0;
	public final static Integer TYPE_VIDEO = 1;
	public final static Integer TYPE_TEXT = 2;
	private Integer id;
	/**
	 * 动态Id 外键
	 */
	private Integer dynamicsId;
	/**
	 * 素材URL
	 */
	private String url;
	/**
	 * 素材类型   0=图片 1=视频 2=文本 3=语音...
	 */
	private Integer type;
	/**
	 * 内容 当TYPE=2时素材内容
	 */
	private String content;
	private Date createTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDynamicsId() {
		return dynamicsId;
	}
	public void setDynamicsId(Integer dynamicsId) {
		this.dynamicsId = dynamicsId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
