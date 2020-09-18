package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserVO;

@Service
@RequestMapping("/user")
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	public int login(UserVO param) {
		return 2;
	}
	
	public int join(UserVO param) {
		String pw = param.getUser_pw();
		System.out.println("pw : " + pw);
		String salt = SecurityUtils.generateSalt();
		System.out.println("salt : " + salt);
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.insUser(param);
		
	}
	
}
