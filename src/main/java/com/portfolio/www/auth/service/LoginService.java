package com.portfolio.www.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.portfolio.www.auth.dto.MemberDto;
import com.portfolio.www.auth.repository.MemberRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final MemberRepository memberRepository;
	
	
	/**
	 * 사용자 입력 정보로 로그인 검사
	 * 1. 해당 아이디로 가입된(조회되는) 회원 없음 : code = -9
	 * 2. 비밀번호 불일치 : code = -1
	 * 3. 아이디, 비밀번호 일치 : code = memberSeq 
	 * @param memberId
	 * @param passwd
	 * @return
	 */
	public int login(String memberId, String passwd){
		int code = -9;
		MemberDto memberDto = memberRepository.findById(memberId);
		
		//해당 아이디로 조회되는 회원이 없는 경우
		if(ObjectUtils.isEmpty(memberDto)) {
			return code; 
		}
		//아이디로 조회되는 회원이 있으면 
		//비밀번호 일치 검사 (일치 : memberSeq 반환, 불일치 -1)
		code = passwdMatch(passwd, memberDto.getPasswd()) ? memberDto.getMemberSeq() : -1;
		return code;
	}
	
	private boolean passwdMatch(String givenPasswd, String savedPasswd) {
		return BCrypt.verifyer().verify(givenPasswd.toCharArray(), savedPasswd).verified;
	}
}
