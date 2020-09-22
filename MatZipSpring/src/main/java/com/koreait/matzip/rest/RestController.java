package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

@Controller //handlerMapper 랑 비슷함
@RequestMapping("/rest")
public class RestController {
	
	@Autowired
	private RestService service;
	
	@RequestMapping(value="/map")
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE, "지도보기");
		model.addAttribute(Const.VIEW, "rest/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	// 한글 안깨지게 인코딩 설정해줘야함
	// 자동으로 ajax 방식으로 값을 보내줌 (원래는 json 형식(string)으로 바꿔줘야 했음)
	@RequestMapping(value="/ajaxGetList", produces="application/json; charset=utf-8")
	@ResponseBody
	public List<RestDMI> ajaxGetList(RestPARAM param) {
//		System.out.println("sw_lat : " + param.getSw_lat());
//		System.out.println("sw_lng : " + param.getSw_lng());
//		System.out.println("ne_lat : " + param.getNe_lat());
//		System.out.println("ne_lng : " + param.getNe_lng());
		
		return service.selRestList(param);
	}
	
	@RequestMapping(value="/restReg")
	public String restReg(Model model) {
		
		model.addAttribute("categoryList", service.selCategoryList());
		
		model.addAttribute(Const.TITLE, "매장등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/restReg", method = RequestMethod.POST)
	public String restReg(RestPARAM param) {
//		System.out.println("nm : " + param.getNm());
//		System.out.println(("addr : " + param.getAddr()));
//		System.out.println("lat : " + param.getLat());
//		System.out.println("lng : " + param.getLng());
//		System.out.println("cd_category : " + param.getCd_category());
//		System.out.println("i_user : " + param.getI_user());
		
		int result = service.insRest(param);
		
		return "redirect:/rest/map";
	}
	
	@RequestMapping(value="/restDetail", method = RequestMethod.GET)
	public String restDetail(Model model) { 
		model.addAttribute(Const.TITLE, "디테일");
		model.addAttribute(Const.VIEW, "rest/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
//	@RequestMapping(value="/restDetail", method = RequestMethod.GET)
//	public String restDetail(RestPARAM param) { 
//		model.addAttribute(Const.TITLE, "디테일");
//		model.addAttribute(Const.VIEW, "rest/restDetail");
//		return ViewRef.TEMP_MENU_TEMP;
//	}
	
}
