
package com.wiz.test.demo.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wiz.test.demo.data.dao.TUserMapper;
import com.wiz.test.demo.data.entity.TUser;
import com.wiz.test.demo.data.entity.TUserExample;
import com.wiz.test.demo.web.form.LoginForm;
import com.wiz.test.demo.web.model.LoginResult;

@Controller
@RequestMapping("/api")
public class TestApiController {

	@RequestMapping(value="/login", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LoginResult login(@RequestBody LoginForm loginForm) {
		LoginResult result = new LoginResult();
		if (loginForm.getPassword().equals("333333")) {
			String token = UUID.randomUUID().toString() + "." + loginForm.getCompanyCode() + "." + loginForm.getPersonNumber();
			result.setToken(token);
			result.setStatus(true);
		} else {
			result.setStatus(false);
			result.setMessage("Password Verified Failed.");
		}
		return result;
	}

	@Autowired
	private TUserMapper tUserMapper = null;

	@RequestMapping(value="/hi", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TUser> hi() {
		List<TUser> users = tUserMapper.selectByExample((new TUserExample()).or().andUserNameBetween("test1", "test2").example().orderBy("phone"));
		return users;
	}
}