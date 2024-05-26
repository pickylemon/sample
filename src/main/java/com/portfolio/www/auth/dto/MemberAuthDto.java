package com.portfolio.www.auth.dto;

import java.util.Calendar;
import java.util.UUID;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Alias("MemberAuthDto")
public class MemberAuthDto {
	private int authSeq;
	private int memberSeq;
	private String authNum;
	private String authUri;
	private Long regDtm;
	private Long expireDtm;
	private String authYn;
	
	
	public static MemberAuthDto createMemberAuthDto(int memberSeq) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 3);
		
		String authUri = UUID.randomUUID().toString().replaceAll("-","");
		Long regDtm = Calendar.getInstance().getTimeInMillis();
		Long expireDtm = cal.getTimeInMillis();
		
		return new MemberAuthDto(memberSeq, authUri, regDtm, expireDtm);
	}
	
	public MemberAuthDto(int memberSeq, String authUri, Long regDtm, Long expireDtm) {
		this.memberSeq = memberSeq;
		this.authUri = authUri;
		this.regDtm = regDtm;
		this.expireDtm = expireDtm;
	}
	
	

}