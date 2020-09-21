package com.koreait.matzip.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;

@Controller //handlerMapper 랑 비슷함
@RequestMapping("/rest")
public class RestController {
	
	@RequestMapping(value="/map")
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE, "지도보기");
		model.addAttribute(Const.VIEW, "rest/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
}
