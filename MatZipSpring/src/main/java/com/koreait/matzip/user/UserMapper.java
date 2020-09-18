package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserVO;

@Mapper // UserMapper.xml(mybatis)와 연결
public interface UserMapper {
	public int insUser(UserVO p);
}
