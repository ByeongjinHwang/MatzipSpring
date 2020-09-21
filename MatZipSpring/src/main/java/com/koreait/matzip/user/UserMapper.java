package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Mapper // UserMapper.xml(mybatis)와 연결
public interface UserMapper {
	//가입
	public int insUser(UserVO p);
	//로그인
	public UserDMI selUser(UserPARAM p);
}
