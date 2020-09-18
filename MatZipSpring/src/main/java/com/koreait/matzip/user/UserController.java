package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserVO;

//@ 붙어있는건 대부분 bean등록이 된다
// 그리고 빈등록은 하나만 되야함
@Controller
@RequestMapping("/user")
public class UserController {
	
	// 현재 주솟값이 없다 Autowired 사용하면 주솟값을 자동으로 잡아준다!!!
	// UserService에서 빈등록이 되어있기때문에
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET) // RequestMethod : get, post, put, delete
	public String login(Model model) {
		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");	
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST) 
	public String login(UserVO param) {
		int result = service.login(param);
		
		if(result == 1) {
			return "redirect:/rest/map";
		}
		
		return "redirect:/user/login?err=" + result;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET) 
	public String join(Model model, @RequestParam int err) {
		// @RequestParam : request.parameter랑 같음
		model.addAttribute(Const.TITLE, "회원가입");
		model.addAttribute(Const.VIEW, "user/join");
		
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST) 
	public String join(UserVO param) {
		// 이름만 같다면  param에 다 값이 담김
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		
		return "redirect:/user/join?err=" + result;
	}
	
	
}
	

