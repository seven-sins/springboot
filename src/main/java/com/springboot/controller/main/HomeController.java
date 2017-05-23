package com.springboot.controller.main;

import com.alibaba.fastjson.JSONArray;
import com.springboot.base.BaseController;
import com.springboot.po.Privilege;
import com.springboot.service.assist.RedisService;
import com.springboot.service.sys.RolePrivilegeService;
import com.springboot.vo.OauthUser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	@Autowired
	RedisService redisService;

	@GetMapping("/")
	public String index(){
		return "views/index";
	}
	
	@GetMapping("/main")
	public String list(Model model, HttpServletRequest request){
		String token = String.valueOf(request.getSession().getAttribute("token"));
		if(token == null || "null".equals(token)){
			return "redirect:/login";
		}
		Object userObj = redisService.get(token);
		if(userObj == null){
			return "redirect:/login";
		}
		OauthUser oauthUser = (OauthUser) userObj;
		List<Privilege> dataList = oauthUser.getPrivileges();
		JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(dataList));
		model.addAttribute("privileges", jsonArray);
		
		return "views/main";
	}
}
