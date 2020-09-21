package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserPARAM;
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
	public String login(UserPARAM param, HttpSession hs, RedirectAttributes ra) {
		int result = service.login(param);
		
		// 1:로그인 성공, 2:아이디 없음, 3:비번 틀림
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/rest/map";
		}
		
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해주세요.";
		} else if(result == Const.NO_PW) {
			msg = "비밀번호를 확인해주세요";
		}
		
		param.setMsg(msg);
		// addAttribute : 쿼리문을 날린다
		// addFlashAttribute : 세션에 값을 넣고 지운다 (유지가 안됨)
		ra.addFlashAttribute("data", param);
		return "redirect:/user/login";
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
	public String join(UserVO param, RedirectAttributes ra) {
		// 이름만 같다면  param에 다 값이 담김
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		
		ra.addFlashAttribute("err", result);
		return "redirect:/user/join";
	}
	
	@RequestMapping(value="/ajaxIdChk", method = RequestMethod.POST)
	// @ResponseBody 없었다면 jsp 파일을 찾았을것이다. 이거 자체가 응답이다
	@ResponseBody
	public String ajaxIdChk(@RequestBody UserPARAM param) {
		System.out.println("user_id : " + param.getUser_id());
		int result = service.login(param);
		return String.valueOf(result);
	}
	
	
}
	

