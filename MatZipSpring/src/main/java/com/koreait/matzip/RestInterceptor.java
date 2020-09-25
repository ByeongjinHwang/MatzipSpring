package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.koreait.matzip.rest.RestMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

public class RestInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private RestMapper mapper;
	
	@Override // 컨테이너 가기전에 preHandle에서 처리해줌
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
			
		System.out.println("rest - interceptor");
		
		String uri = request.getRequestURI();
//		System.out.println(uri);
		String[] uriArr = uri.split("/");
		
		String[] checkKeywords = {"del", "Del", "upd", "Upd"};
		for (String keyword: checkKeywords) {
			if(uriArr[2].contains(keyword)) {
				int i_rest = CommonUtils.getIntParameter(request, "i_rest");
				if(i_rest == 0) {
					return false;
				}
				
				int i_user = SecurityUtils.getLoginUserPk(request);	// 로그인 한 사람의 i_user
				
				boolean result = _autoSuccess(i_rest, i_user);
				System.out.println("==== auth result : " + result);
				return result;
				}
		}
		System.out.println("==== auth result : " + true);
		return true;
	}
	
	private boolean _autoSuccess(int i_rest, int i_user) {
		return i_user == mapper.selRestChkUser(i_rest);
	}
}