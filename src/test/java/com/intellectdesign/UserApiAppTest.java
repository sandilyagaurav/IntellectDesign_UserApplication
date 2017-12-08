package com.intellectdesign;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.intellectdesign.springbootstarter.UserApiApp;
import com.intellectdesign.springbootstarter.user.error.Error;
import com.intellectdesign.springbootstarter.user.util.UserUtil;
import com.intellectdesign.springbootstarter.user.util.ValidateUser;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { UserApiApp.class, TestConfiguration.class })
public class UserApiAppTest {
	
	@Autowired
	public UserUtil util;
	@Autowired
	public ValidateUser validateUser;
	@Autowired
	public Error userError;
	@Before
	public void setUp(){
		util = new UserUtil();
		 
	}
	@Before
	public void validation(){
		validateUser = new ValidateUser();
		 
	}
	@Test
	public void negativeTest(){
		String userId = util.createuserId();
		assertNotEquals("12345678",userId);
		
	}
	@Test
	public void positivetest(){
		boolean flag = false;
		Date birthdate = new Date("01-MAR-2016");
		flag = validateUser.validateBirthDate(birthdate);
		assertFalse(flag);
		
	}
	
	
	
	
	

}
