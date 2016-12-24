package com.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.domain.User;

@RestController
public class UserController {
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public Object list(){
		User user = new User();
		user.setUserId("1");
		user.setUserName("seven sins");
		user.setPassWord("123");
		
		List<User> dataList = new ArrayList<User>();
		dataList.add(user);
		dataList.add(user);
		
		return dataList;
	}
}
