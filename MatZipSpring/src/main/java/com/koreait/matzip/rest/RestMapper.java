package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.model.RestFile;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Mapper
public interface RestMapper {
	// 인터페이스는 굳이 public abstract가 생략되어있음
	List<RestDMI> selRestList(RestPARAM param);
	List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	List<RestRecMenuVO> selRestMenus(RestPARAM param);
	
	int insRest(RestPARAM param);
	RestDMI selRest(RestPARAM param);
	int delRestRecMenu(RestPARAM param);
	int delRestMenu(RestPARAM param);
	int delRest(RestPARAM param);
	int insRestRecMenu(RestRecMenuVO param);
	
	int insMenus(RestRecMenuVO mParam);
	int selRestChkUser(int i_rest);
	

	
}
