package com.intellectdesign.springbootstarter.user.model;

import org.springframework.stereotype.Service;

@Service
public class UserResponse {
	
	private String resMsg;
	private String userId;
	private Object[] valErrors;
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Object[] getValErrors() {
		return valErrors;
	}
	public void setValErrors(Object[] valErrors) {
		this.valErrors = valErrors;
	} 

}
