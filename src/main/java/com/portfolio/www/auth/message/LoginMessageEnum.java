package com.portfolio.www.auth.message;

import lombok.Getter;

@Getter
public enum LoginMessageEnum {
	LOGIN_SUCCESS("1", "로그인에 성공했습니다"),
	WRONG_PASSWD("-1", "비밀번호가 틀렸습니다"),
	USER_NOT_REGISTERED("-9", "가입되지 않은 회원입니다."),
	LOGOUT_SUCCESS("2", "성공적으로 로그아웃 되었습니다");
	
	String code;
	String msg;

	LoginMessageEnum(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
}
