package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.base.BaseController;
import com.springboot.domain.User;
import com.springboot.exception.SevenException;
import com.springboot.service.UserService;

@Controller
public class UserController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String list(Model model){
		List<User> dataList = userService.find(new User());
		model.addAttribute("dataList", dataList);
		
		return "views/user/list";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String create(@RequestBody User user){
		if("0".equals(user.getId())){
			throw new SevenException(1, "参数错误");
		}
		userService.insert(user);
		
		return "redirect:/user";
	}
}
