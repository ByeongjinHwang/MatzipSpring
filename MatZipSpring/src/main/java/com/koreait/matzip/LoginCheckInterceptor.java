package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.koreait.matzip.user.model.UserPARAM;

// HandlerInterceptorAdapter을 상속받 받음
// 맵핑 되어있는 애들만 인터셉터에 걸림
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	
	//preHandle : 컨트롤러 진입후 view가 렌더링 되기 전 수행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//HttpSession hs = request.getSession();
		//UserPARAM loginUser = (UserPARAM)hs.getAttribute(Const.LOGIN_USER);
		
		
		String uri = request.getRequestURI();
//		System.out.println(uri);
		String[] uriArr = uri.split("/");
		
		
		if(uriArr[1].equals("res")) {	// 리소스(js, css, img)
			return true;
		} else if(uriArr.length < 3) { //주소가 이상한 경우
			return false;
		}
		
//		System.out.println("인터셉터!!");
		boolean isLogout = SecurityUtils.isLogout(request);
		
		switch(uriArr[1]) {
		
		case ViewRef.URI_USER : //user
			switch (uriArr[2]) {
			case "login": case "join":
				if(!isLogout) {	 // 로그인 되어 있는 상태
					response.sendRedirect("/rest/map");
					return false;
				}
			}
			
		case ViewRef.URI_REST : //rest
			switch(uriArr[2]) {
			case "restReg":
				if(isLogout) { // 로그아웃 상태
					response.sendRedirect("/user/login");
					return false;
				}
			}
			
		}
		
		return true;
	}
}
