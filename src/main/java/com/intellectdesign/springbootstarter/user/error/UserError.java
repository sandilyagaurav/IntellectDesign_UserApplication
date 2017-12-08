package com.intellectdesign.springbootstarter.user.error;

public class UserError {
	
	public UserError(){
		super();
	}
	
	public UserError(String code , String message){
		super();
		this.code=code;
		this.message=message;
	}
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public final static  UserError INVALID_NAME = new UserError("1001","Name Invalid");
	

}
