package com.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.config.exception.SevenException;
import com.springboot.domain.Privilege;
import com.springboot.domain.User;
import com.springboot.domain.UserDetail;
import com.springboot.service.RedisService;
import com.springboot.service.RolePrivilegeService;
import com.springboot.service.UserDetailService;
import com.springboot.service.UserService;
import com.springboot.utils.Encrypt;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author seven sins
 *
 */
@Controller
public class UserController extends BaseController{
	
	@Autowired
	UserService userService;
	@Autowired
	UserDetailService userDetailService;
	@Autowired
	RedisService redisService;
	@Autowired
	RolePrivilegeService rolePrivilegeService;

    @ApiOperation(value = "用户列表")
	@GetMapping("/user")
	@ResponseBody
	public Object list(User user){
		List<User> dataList = userService.find(user);
		int total = userService.count(user);
		
		return super.resultMap(0, dataList, total);
	}

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
	@PostMapping("/user")
	@ResponseBody
	public Object create(@Valid @RequestBody User user){
		userService.insert(user);
		
		return super.resultMsg(0, "操作成功");
	}

    @ApiOperation(value="修改用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
	@PutMapping("/user/{id}")
	@ResponseBody
	public Object update(@RequestBody User user, @PathVariable("id") int id){
		user.setId(id);
		userService.update(user);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@DeleteMapping("/user/{id}")
	@ResponseBody
	public Object remove(@PathVariable("id") int id){
		userService.deleteById(id);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@GetMapping("/login")
	public Object login(){
		return "views/user/login";
	}

    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "登录成功", response = User.class),
            @ApiResponse(code = 1, message = "用户名或密码错误")
    })
	@PostMapping("/doLogin")
	@ResponseBody
	public Object doLogin(@RequestBody User user, HttpServletRequest request){
    	if(user.getUserName() == null || "".equals(user.getUserName()) || user.getPassWord() == null || "".equals(user.getPassWord())){
    		throw new SevenException(401, "用户参数错误");
    	}
    	UserDetail me = (UserDetail) userDetailService.loadUserByUsername(user.getUserName());
		if(me == null){
			throw new SevenException(401, "用户不存在");
		}
		if(!me.isAccountNonLocked()){
			throw new SevenException(401, "用户被禁用");
		}
		if(!user.getPassWord().equals(me.getPassWord())){
			throw new SevenException(401, "密码不正确");
		}
		Date now = new Date();
		me.setPassWord(Encrypt.MD5(me.getUserName() + String.valueOf(now.getTime())));
		
		// 获取当前用户所拥有权限
		List<Privilege> userPrivilege = rolePrivilegeService.findByRoleId(me.getRoleId());
		me.setPrivileges(userPrivilege);
		// 将用户信息存入redis
		redisService.add("user-" + me.getPassWord(), me);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("code", 0);
		jsonObj.put("token", me.getPassWord());
		
		return jsonObj;
	}
}

