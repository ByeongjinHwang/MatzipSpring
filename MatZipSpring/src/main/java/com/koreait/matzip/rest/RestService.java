package com.koreait.matzip.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

@Service
public class RestService {
	
	@Autowired
	private RestMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	List<RestDMI> selRestList(RestPARAM param) {
		return mapper.selRestList(param);
	}
	
	int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
	
	List <CodeVO> selCategoryList() {
		CodeVO p = new CodeVO();
		p.setI_m(1); // 음식점 카테고리 코드 = 1
		
		return cMapper.selCodeList(p);
	}
	
	RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}
	
	@Transactional // 트랜잭션 검
	// 디폴트가 오토커밋인데, 그걸 끄고 하나하나 실행해줌, 그리고 에러가 터지면 롤백시킴.
	// try catch가 들어있다고 보면 됨
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}
	
	int delRestRecMenu(RestPARAM param) {
		return mapper.delRestRecMenu(param);
	}
	
	int delRestMenu(RestPARAM param) {
		return mapper.delRestMenu(param);
	}
	
	int delRest(RestPARAM param) {
		return mapper.delRest(param);
	}
}
