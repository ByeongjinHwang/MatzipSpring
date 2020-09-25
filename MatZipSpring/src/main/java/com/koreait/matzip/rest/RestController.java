package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.model.RestFile;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.oreilly.servlet.MultipartRequest;

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
	
	@RequestMapping(value="/reg")
	public String restReg(Model model) {
		
		model.addAttribute("categoryList", service.selCategoryList());
		
		model.addAttribute(Const.TITLE, "매장등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/reg", method = RequestMethod.POST)
	public String restReg(RestPARAM param) {
//		System.out.println("nm : " + param.getNm());
//		System.out.println(("addr : " + param.getAddr()));
//		System.out.println("lat : " + param.getLat());
//		System.out.println("lng : " + param.getLng());
//		System.out.println("cd_category : " + param.getCd_category());
//		System.out.println("i_user : " + param.getI_user());
		
		int result = service.insRest(param);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/recMenus", method=RequestMethod.POST)
	public String recMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
				
		int i_rest = service.insRecMenus(mReq);
		
		ra.addAttribute("i_rest", i_rest);
		return "redirect:/rest/detail";
	}
	
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public String restDetail(RestPARAM param, Model model) { 
		
		RestDMI data = service.selRest(param);
		
		List<RestRecMenuVO> recMenuList = service.selRestRecMenus(param);
		
		List<RestRecMenuVO> menuList = service.selRestMenus(param);
//		model.addAttribute("menuList", menuList);
		
		model.addAttribute("recMenuList", recMenuList);
		model.addAttribute("data", data);
		model.addAttribute("css", new String[] {"restDetail"});
		model.addAttribute(Const.TITLE, data.getNm());
		model.addAttribute(Const.VIEW, "rest/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/ajaxSelMenuList")
	@ResponseBody
	public List<RestRecMenuVO> ajaxSelMenuList(RestPARAM param) { 
		
		
		return service.selRestMenus(param);
	}
	
	@RequestMapping(value="/del", method = RequestMethod.GET)
	public String restDel(RestPARAM param, HttpSession hs) { 
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		int result = 1;
		
		// 보안상 이유로 try catch로 감싸줌 - 에러시에 쿼리문이 안뜨게
		try {
			service.delRestTran(param);
		} catch(Exception e) {
			result = 0;
		}
		
		System.out.println("result : " + result);
		return "redirect:/";
	}
	
	@RequestMapping(value="/ajaxDelRecMenu", produces="application/json; charset=utf-8")
	@ResponseBody
	public int ajaxDelRecMenu(RestPARAM param, HttpSession hs) { 
		
		String path = "/resources/img/rest/" + param.getI_rest() + "/rec_menu/";
		String realPath = hs.getServletContext().getRealPath(path);
		param.setI_user(SecurityUtils.getLoginUserPk(hs)); // 로긴 유저pk 담기
		return service.delRestRecMenu(param, realPath);
	}
	
	@RequestMapping(value="/ajaxDelMenu")
	@ResponseBody
	public int ajaxDelMenu(RestPARAM param) { // i_rest, seq, menu_pic 다 보냄
		return service.delRestMenu(param);
	}
	
	@RequestMapping("/menus")
	public String menus(@ModelAttribute RestFile param, int i_user, MultipartHttpServletRequest mReq) {
		
		for(MultipartFile file : param.getMenu_pic()) {
			System.out.println("fileNm : " + file.getOriginalFilename());
		}
		System.out.println("i_rest : " + param.getI_rest());
		
		service.insMenus(param, i_user, mReq);
		
		return "redirect:/rest/detail?i_rest=" + param.getI_rest();
	}
}
