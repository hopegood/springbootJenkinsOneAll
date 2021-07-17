package com.diandian.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.diandian.HttpResult;
import com.diandian.component.JwtAuthenticatioToken;
import com.diandian.modal.SysUser;
import com.diandian.util.SecurityUtils;

@RestController
public class LoginController {
	/**
	 * 登录接口
	 */
	@PostMapping(value = "/loginTest")
	public HttpResult login(/*@RequestBody LoginBean loginBean, */HttpServletRequest request) throws IOException {
		String username = "sqx";//loginBean.getAccount();
		String password = "123456";//loginBean.getPassword();
		// 用户信息
		SysUser user = new SysUser(username,password);//sysUserService.findByName(username);

		// 账号不存在、密码错误
		if (user == null) {
			return HttpResult.error("账号错误");
		}
		
		/*if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
			return HttpResult.error("账号错误");
		}

		// 账号锁定
		  if (user.getStatus() == 0) { return HttpResult.error("账号已被锁定,请联系管理员"); }
		 */

		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
		JSONObject result = new JSONObject();
		result.put("token", token);
		result.put("user", user);
		return HttpResult.ok(result);
	}
	
	@Autowired
	private AuthenticationManager authenticationManager;

}
