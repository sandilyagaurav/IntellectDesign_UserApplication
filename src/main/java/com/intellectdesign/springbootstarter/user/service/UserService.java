package com.intellectdesign.springbootstarter.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellectdesign.springbootstarter.user.error.Error;
import com.intellectdesign.springbootstarter.user.model.User;
import com.intellectdesign.springbootstarter.user.model.UserResponse;
import com.intellectdesign.springbootstarter.user.util.UserUtil;
import com.intellectdesign.springbootstarter.user.util.ValidateUser;

@Service
public class UserService {

	@Autowired
	public UserUtil util;
	//@Autowired
	//public UserResponse response;
	@Autowired
	public Error error;
	@Autowired
	public ValidateUser validateUser;

	private List<User> userList = new ArrayList<User>();

	public List<User> getUserList() {
		return userList;
	}

	public UserResponse addUser(User user, UserResponse response) {
		System.out.println("inside AddUser");
		String newId = null;
		newId = util.createuserId();
		user.setId(newId);
		user.setActive(true);
		userList.add(user);
		response.setResMsg("User Created Successfully");
		response.setUserId(newId);
		response.setValErrors(Arrays.asList(new String("No data validation Errors")).toArray());
		return response;
	}

	public UserResponse updateUser(String id, User user, UserResponse response) {
		List<Error> errorList = new ArrayList<>();
		boolean status = false;
		if(id==null || id.equalsIgnoreCase("")){
			error = new Error();
			error.setCode("105");
			error.setMessage("Id is invalid");
			error.setField("id");
			errorList.add(error);
			status = true;
			
		}else{
			List<User> userList = getAllUsers();
			boolean idExists = false;
			for(User userdata : userList){
				if(userdata.getId()!=null && userdata.getId().equals(id)){
					idExists= true;
					break;
				}
			}
			System.out.println("id exists :"+idExists);
			if(!idExists){
				error = new Error();
				error.setCode("106");
				error.setMessage("Id does not exist");
				error.setField("id");
				errorList.add(error);
				status = true;
				response.setResMsg("User update failed");
				response.setUserId(id);
				response.setValErrors(errorList.toArray());
				return response;
			}
		}
		if(user!=null && user.getPinCode()!=null){
			String zipCodePattern = "^[1-9][0-9]{5}$";
			if(!(String.valueOf(user.getPinCode()).matches(zipCodePattern))){
				error = new Error();
				error.setCode("105");
				error.setMessage("Pin is invalid");
				error.setField("pinCode");
				errorList.add(error);
				status = true;
			}
		}else{
			error = new Error();
			error.setCode("105");
			error.setMessage("Pin is invalid");
			error.setField("pinCode");
			errorList.add(error);
			status = true;
		}
		if(user!=null && user.getBirthDate()!=null){
			boolean futureDate = false;
			futureDate = validateUser.validateBirthDate(user.getBirthDate());
			if(futureDate){
				error = new Error();
				error.setCode("107");
				error.setMessage("Birth date cant be future date");
				error.setField("birthDate");
				errorList.add(error);
				status = true;
			}
		}
		else{
			error = new Error();
			error.setCode("107");
			error.setMessage("Birth date cant be empty");
			error.setField("birthDate");
			errorList.add(error);
			status = true;
		}
		if(!status){
			if(id!=null && !id.equalsIgnoreCase("")){
				List<User> userList = getAllUsers();
				for(User userData : userList){
					if(userData.getId()!=null && userData.getId().equalsIgnoreCase(id)){
						userData.setPinCode(user.getPinCode());
						userData.setBirthDate(user.getBirthDate());
					}
				}
			}
			response.setResMsg("User update Success");
			response.setUserId(id);
			response.setValErrors(Arrays.asList(new String("No data validation error")).toArray());
		}else{
			response.setResMsg("User update failed");
			response.setUserId(id);
			response.setValErrors(errorList.toArray());
		}
		return response;
	}

	public UserResponse deleteUser(String id, UserResponse response) {
		List<Error> errorList = new ArrayList<>();
		boolean status = false;
		if(id==null || id.equalsIgnoreCase("")){
			error = new Error();
			error.setCode("105");
			error.setMessage("Id is invalid");
			error.setField("id");
			errorList.add(error);
			status = true;
			
		}else{
			List<User> userList = getAllUsers();
			boolean idExists = false;
			for(User userdata : userList){
				if(userdata.getId()!=null && userdata.getId().equals(id)){
					idExists= true;
					break;
				}
			}
			System.out.println("id exists :"+idExists);
			if(!idExists){
				error = new Error();
				error.setCode("106");
				error.setMessage("Id does not exist");
				error.setField("id");
				errorList.add(error);
				status = true;
				response.setResMsg("User update failed");
				response.setUserId(id);
				response.setValErrors(errorList.toArray());
				return response;
			}
		}
		if(!status){
			if(id!=null && !id.equalsIgnoreCase("")){
				List<User> userList = getAllUsers();
				for(User userData : userList){
					if(userData.getId()!=null && userData.getId().equalsIgnoreCase(id)){
						userData.setActive(false);
					}
				}
			}
			response.setResMsg("User Deactivated Successfully");
			response.setUserId(id);
			response.setValErrors(Arrays.asList(new String("No data validation error")).toArray());
		}else{
			response.setResMsg("User deactivation failed");
			response.setUserId(id);
			response.setValErrors(errorList.toArray());
		}
		return response;
	}

	//for testing only
	public List<User> getAllUsers(){
		System.out.println("get all list");
		return userList;

	}

}
