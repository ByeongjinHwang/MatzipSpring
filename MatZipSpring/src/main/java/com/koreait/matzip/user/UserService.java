package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Service
@RequestMapping("/user")
public class UserService {

	@Autowired
	private UserMapper mapper;

	// 1인 로그인 성공, 2번 아이디 없음, 3번 비번 틀림
	public int login(UserPARAM param) {

		if (param.getUser_id().equals("")) {
			return Const.NO_ID;
		}

		UserDMI dbUser = mapper.selUser(param);
		if (dbUser == null) {
			return Const.NO_ID;
		}

		String cryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		if (!cryptPw.equals(dbUser.getUser_pw())) {
			return Const.NO_PW;
		}

		param.setUser_pw(null); // 비밀번호 보안 상 이유로 null 처리함
		param.setNm(dbUser.getNm());
		param.setProfile_img(dbUser.getProfile_img());
		param.setI_user(dbUser.getI_user());
		return Const.SUCCESS;
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
