package com.koreait.matzip.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //handlerMapper 랑 비슷함
@RequestMapping("/rest")
public class RestController {
	
	@RequestMapping(value="/restMap")
	public String restMap() {
		return null;
	}
}
