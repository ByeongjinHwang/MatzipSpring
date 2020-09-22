package com.koreait.matzip.rest.model;

public class RestDMI extends RestVO {
	private String cd_category_nm;
	private String user_nm;
	private int cnt_Favorite;
	
	
	public int getCnt_Favorite() {
		return cnt_Favorite;
	}
	public void setCnt_Favorite(int cnt_Favorite) {
		this.cnt_Favorite = cnt_Favorite;
	}
	public String getCd_category_nm() {
		return cd_category_nm;
	}
	public void setCd_category_nm(String cd_category_nm) {
		this.cd_category_nm = cd_category_nm;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
}
