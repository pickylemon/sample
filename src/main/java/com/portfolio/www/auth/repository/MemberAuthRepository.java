package com.portfolio.www.auth.repository;

import com.portfolio.www.auth.dto.MemberAuthDto;

public interface MemberAuthRepository {

	public int addAuthInfo(MemberAuthDto dto);
	public MemberAuthDto getMemberAuthDto(String uri);
	public int updateAuthValid(String uri);

}
