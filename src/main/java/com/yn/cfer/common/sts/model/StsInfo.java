package com.yn.cfer.common.sts.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author user
 */
public class StsInfo {
	private String expiration;
	@SerializedName("access_key_id")
	private String accessKeyId;
	@SerializedName("access_key_secret")
	private String accessKeySecret;
	@SerializedName("security_token")
	private String securityToken;
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getSecurityToken() {
		return securityToken;
	}
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}
