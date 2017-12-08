package com.intellectdesign.springbootstarter.user.util;

import org.springframework.stereotype.Service;

@Service
public class UserUtil {
	
	public String createuserId(){
		String newId = Long.toString(System.currentTimeMillis());
		System.out.println("newId :"+newId);
		return newId;
	}

}
