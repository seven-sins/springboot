package com.springboot.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.po.User;
import com.springboot.service.sys.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:00:11
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	UserService userService;
	@Autowired
	TokenStore tokenStore;

	@ApiOperation(value = "用户列表")
	@GetMapping("/api/user")
	@ResponseBody
	public Object list(User user) {
		List<User> dataList = userService.find(user);
		int total = userService.count(user);

		return super.resultMap(200, dataList, total);
	}

	@ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
	@ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
	@PostMapping("/api/user")
	@ResponseBody
	public Object create(@Valid @RequestBody User user) {
		userService.insert(user);

		return super.resultMsg(200, "操作成功");
	}

	@ApiOperation(value = "修改用户")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
	@PutMapping("/api/user/{id}")
	@ResponseBody
	public Object update(@RequestBody User user, @PathVariable("id") int id) {
		user.setId(id);
		userService.update(user);

		return super.resultMsg(200, "操作成功");
	}

	@DeleteMapping("/api/user/{id}")
	@ResponseBody
	public Object remove(@PathVariable("id") int id) {
		userService.deleteById(id);

		return super.resultMsg(200, "操作成功");
	}

	@GetMapping("/login")
	public Object login() {
		return "views/user/login";
	}

	/**
	 * 登录*********************************************************
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@ApiResponses(value = { @ApiResponse(code = 0, message = "登录成功", response = User.class),
			@ApiResponse(code = 401, message = "用户名或密码错误") })
	@PostMapping("/doLogin")
	@ResponseBody
	public Object doLogin(@RequestBody User user, HttpServletRequest request) {
		if (user.getUserName() == null || "".equals(user.getUserName()) || user.getPassWord() == null
				|| "".equals(user.getPassWord())) {
			return super.resultMsg(401, "用户参数错误");
		}
		// 设置请求头
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, enCode());
		headers.add(HttpHeaders.ACCEPT, "application/json");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
		// 获取token
		String url = "http://127.0.0.1/oauth/token";
		String params = "username=" + user.getUserName() + "&password=" + user.getPassWord()
				+ "&scope=read&grant_type=password";
		//
		JSONObject result = new JSONObject();
		ResponseEntity<?> response;
		try {
			response = restTemplate.exchange(url + "?" + params, HttpMethod.POST, new HttpEntity<>("", headers),
					params.getClass());
			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				return super.resultMsg(401, "登录操作出错.");
			}

			JSONObject jsonObj = JSONObject.parseObject(response.getBody().toString());

			result.put("code", 200);
			result.put("token", jsonObj.get("access_token"));

		} catch (HttpServerErrorException e) {
			result.put("code", 401);
			result.put("message", "用户名或密码错误");
		}

		return result;
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(authToken);
		tokenStore.removeAccessToken(accessToken);

		return SUCCESS;
	}

	private String enCode() {// client_id:client_secret
		return "Basic " + Base64.encodeBase64String(("client_id:client_secret").getBytes());
	}
}
