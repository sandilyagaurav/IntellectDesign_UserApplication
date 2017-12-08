package com.intellectdesign.springbootstarter.user.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellectdesign.springbootstarter.user.error.Error;
import com.intellectdesign.springbootstarter.user.model.User;
import com.intellectdesign.springbootstarter.user.model.UserResponse;
import com.intellectdesign.springbootstarter.user.service.UserService;

@Service
public class ValidateUser {


	@Autowired
	public Error error;
	@Autowired
	public UserService userService;

	public UserResponse validateAddUser(User user, UserResponse response) throws NoSuchFieldException, SecurityException {
		List<Error> errorList = new ArrayList<>();
		boolean status = false;
		if(user!=null && (user.getId()==null || user.getId().equalsIgnoreCase(""))){
			error = new Error();
			error.setCode("101");
			error.setMessage("Id is invalid");
			error.setField("id");
			errorList.add(error);
			status = true;
		}
		if(user!=null && (user.getfName()==null) || user.getfName().equalsIgnoreCase("")){
			error = new Error();
			error.setCode("102");
			error.setMessage("First name is invalid");
			error.setField("fName");
			errorList.add(error);
			status = true;
		}
		if(user!=null && (user.getlName()==null) || user.getlName().equalsIgnoreCase("")){
			error = new Error();
			error.setCode("103");
			error.setMessage("Last name is invalid");
			error.setField("lName");
			errorList.add(error);
			status = true;
		}
		if(user!=null && (user.getEmail()==null) || user.getEmail().equalsIgnoreCase("")){
			error = new Error();
			error.setCode("104");
			error.setMessage("Email is invalid");
			error.setField("email");
			errorList.add(error);
			status = true;
		}else{
			error = new Error();
			error = validateEmail(user.getEmail());
			System.out.println("error code :"+error.getCode()+" error msg :"+error.getMessage());
			if(error.getCode()!=null || error.getMessage()!=null){
				errorList.add(error);
				status = true;
			}
		}
		if(user!=null && user.getPinCode()==null){
			error = new Error();
			error.setCode("105");
			error.setMessage("Pin code is invalid");
			error.setField("pinCode");
			errorList.add(error);
			status = true;
		}else{
			String zipCodePattern = "^[1-9][0-9]{5}$";
			if(!(String.valueOf(user.getPinCode()).matches(zipCodePattern))){
				error = new Error();
				error.setCode("105");
				error.setMessage("Pin code is invalid");
				error.setField("pinCode");
				errorList.add(error);
			}
		}
		if(user!=null && user.getBirthDate()==null){
			error = new Error();
			error.setCode("105");
			error.setMessage("Birth date is invalid");
			error.setField("birthDate");
			errorList.add(error);
			status = true;
		}
		else{
			System.out.println("call validate date");
			boolean futureDate = false;
			futureDate = validateBirthDate(user.getBirthDate());
			if(futureDate){
				error = new Error();
				error.setCode("107");
				error.setMessage("Birth date cant be future date");
				error.setField("birthDate");
				errorList.add(error);
				status = true;
			}
		}

		if(status){
			response.setResMsg("User Creation failed");
			response.setUserId("Not Created");
			response.setValErrors(errorList.toArray());
		}else{
			response.setResMsg(null);
			response.setValErrors(null);
		}
		return response;
	}

	public boolean validateBirthDate(Date date) {
		boolean futuredate = false;
		Calendar cal = Calendar.getInstance(); 
		Date curdate = cal.getTime();
		if(date.after(curdate)){
			futuredate = true;
		}
		return futuredate;
	}

	public Error validateEmail(String email) {
		final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
		Matcher match = emailPattern.matcher(email);
		if(match.matches()){
			List<User>userList = userService.getAllUsers();
			for(User user : userList){
				if(user.getEmail()!=null && user.getEmail().equalsIgnoreCase(email)){
					System.out.println("is active user :"+user.isActive());
					if(user.isActive()){
						error = new Error();
						error.setCode("104");
						error.setMessage("User with same Email is Active , please provide new Email");
						error.setField("email");

					}
				}
			}
		}else{
			error = new Error();
			error.setCode("104");
			error.setMessage("Email Pattern is invalid");
			error.setField("email");
		}
		return error;
	}

}
