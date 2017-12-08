package com.intellectdesign.springbootstarter.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intellectdesign.springbootstarter.user.model.User;
import com.intellectdesign.springbootstarter.user.model.UserResponse;
import com.intellectdesign.springbootstarter.user.service.UserService;
import com.intellectdesign.springbootstarter.user.util.ValidateUser;

@RestController
public class UserController {

	@Autowired
	public UserService userService;
	@Autowired
	public UserResponse response;

	@Autowired
	public ValidateUser userValidation;


	//for testing only
	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}



	@RequestMapping(method=RequestMethod.POST , value="/user/createuser")
	public UserResponse addUser(@RequestBody User user) throws NoSuchFieldException, SecurityException {
		if(user!=null){
			response = userValidation.validateAddUser(user,response);
		}
		System.out.println("response :"+response+"error msg :"+response.getResMsg());
		if(response!=null && response.getResMsg()==null){
			response = userService.addUser(user,response);
		}
		return response;
	}

	@RequestMapping(method=RequestMethod.PUT , value="/user/{id}")
	public UserResponse updateTopic(@RequestBody User user , @PathVariable String id) {
		response = new UserResponse();
		response = userService.updateUser(id , user, response);
		return response;
	}

	@RequestMapping(method=RequestMethod.DELETE , value="/user/{id}")
	public UserResponse deleteTopic(@PathVariable String id) {
		response = new UserResponse();
		response = userService.deleteUser(id,response);
		return response;
	}


}
