package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
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
	public String login(UserDTO param) {
		int result = service.login(param);
		
		// 1인 로그인 성공, 2번 아이디 없음, 3번 비번 틀림
		if(result == 1) {
			return "redirect:/rest/map";
		}
		
		if(result == 2) {
			
		}
		
		return "redirect:/user/login?err=" + result;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET) 
	public String join(Model model, @RequestParam(required=false, defaultValue = "0") int err) {
		// @RequestParam : request.parameter랑 같음
		// required = true 면 반드시 err 값이 있어야함. (err 값이 필수가된다) (기본값이 true)
		// defaultValue 넣어줘야함. 근데  defaultValue 있으면 required가 필요없다
		//System.out.println("err : " + err);
		
		if(err > 0 ) {
			model.addAttribute("msg", "에러가 발생하였습니다");
		}
		
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
	

