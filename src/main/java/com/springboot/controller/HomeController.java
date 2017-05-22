package com.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.springboot.base.BaseController;
import com.springboot.config.annotation.Session;
import com.springboot.po.Privilege;
import com.springboot.po.User;
import com.springboot.service.sys.RolePrivilegeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:59:34
 */
@Controller
public class HomeController extends BaseController {
	@Autowired
	RolePrivilegeService rolePrivilegeService;

	@GetMapping("/")
	public String index(){
		return "views/index";
	}
	
	@GetMapping("/main")
	public String list(@Session("user") User user, Model model){
		if(user == null){
			return "redirect:/login";
		}
		List<Privilege> dataList = user.getPrivileges();
		JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(dataList));
		model.addAttribute("privileges", jsonArray);
		
		return "views/main";
	}
	
}
