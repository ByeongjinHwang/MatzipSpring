package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Service
@RequestMapping("/user")
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	// 1인 로그인 성공, 2번 아이디 없음, 3번 비번 틀림
	public int login(UserDTO param) {
		if(param.getUser_id().equals("")) {
			return Const.NO_ID;
		}
		
		UserDMI dbUser = mapper.selUser(param);
		System.out.println("pw : " + dbUser.getUser_pw());
		
		if(dbUser.getUser_id().equals(param.getUser_id())) {
			if(dbUser.getUser_pw().equals(param.getUser_pw())) {
				return 1;
			}
			return 3;
		} else {
			return 2;
		}
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
