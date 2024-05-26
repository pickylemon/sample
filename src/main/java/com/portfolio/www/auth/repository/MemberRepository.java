package com.portfolio.www.auth.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import com.portfolio.www.auth.dto.MemberDto;

public interface MemberRepository {
	int save(MemberDto memberDto) throws DataAccessException;
	
	int getMemberSeq(String memberId) throws DataAccessException;
	
	int updateAuthValid(int memberSeq) throws DataAccessException;
	
	//이 메서드는 언제 쓰지..?
	MemberDto findById(String memberId) throws DataRetrievalFailureException;
	MemberDto findBySeq(int memberSeq) throws DataAccessException;
}