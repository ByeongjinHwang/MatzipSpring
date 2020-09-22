package com.koreait.matzip.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper // dao롤 자동으로 만들고 bean 등록까지 완료
public interface CommonMapper {
	List<CodeVO> selCodeList(CodeVO p);
}
