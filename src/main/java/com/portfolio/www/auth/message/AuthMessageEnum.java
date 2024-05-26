package com.portfolio.www.auth.message;

import lombok.Getter;

@Getter
public enum AuthMessageEnum {
	SUCCESS("000", "인증 메일이 발송되었습니다."), 
	ALREADY_EXISTS("001", "이미 존재하는 아이디입니다."),
	JOIN_FAIL("002", "회원가입에 실패했습니다"),
	
	AUTH_MAIL_SUCCESS("100", "이메일 인증에 성공했습니다. 회원 가입을 축하드립니다"),
	INVALID_AUTH_TIME("101", "인증 시간이 초과되었습니다."),
	AUTH_MAIL_FAIL("102", "이메일 인증에 실패했습니다.");
	
	private AuthMessageEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	String code;
	String msg;
}
