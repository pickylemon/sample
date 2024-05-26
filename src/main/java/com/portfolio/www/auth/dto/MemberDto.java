package com.portfolio.www.auth.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("MemberDto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
	private int memberSeq;
	@NotNull(message = "아이디는 필수값입니다.")
	@Pattern(regexp = "^[0-9a-z]{5,8}$", message = "아이디는 영어 소문자와 숫자의 구성으로 5~8자리 내로 입력해주세요.")
	private String memberId;
	@NotNull
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
	message = "비밀번호는 특수문자와 영어대소문자, 숫자를 조합해 8~15자리 내로 입력해주세요.")
	//영어대소문자, 특수문자 8~15자리
	private String passwd;
	@NotNull @NotBlank
	private String memberNm;
	@NotNull @NotBlank @Email
	private String email;
	private String authYn;
	private String pwdChngDtm;
	private String joinDtm;
	
	public MemberDto(MemberDto memberDto) {
		this(memberDto.getMemberSeq(), memberDto.getMemberId(), memberDto.getPasswd()
				, memberDto.getMemberNm(), memberDto.getEmail(), memberDto.getAuthYn()
				, memberDto.getPwdChngDtm(), memberDto.getJoinDtm());

	}
	
}