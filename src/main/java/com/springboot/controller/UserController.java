package com.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.data.Admin;
import com.springboot.domain.User;
import com.springboot.exception.SevenException;

@Controller
public class UserController {
	
	@Value("${author.name}")
	private String author;
	@Autowired
	private Admin admin;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String list(Model model){
		User user = new User();
		user.setUserId("1");
		user.setUserName("seven sins");
		user.setPassWord("123");
		
		List<User> dataList = new ArrayList<User>();
		dataList.add(user);
		dataList.add(user);
		
		model.addAttribute("dataList", dataList);
		
		System.out.print(author + "\n");
		
		System.out.print("admin: \n");
		System.out.print("userName: " + admin.getUserName() + "\n");
		System.out.print("passWord: " + admin.getPassWord() + "\n");
		
		return "views/user/list";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String create(@RequestBody User user){
		if("0".equals(user.getUserId())){
			throw new SevenException(1, "参数错误");
		}
		
		return "redirect:/user";
	}
}
