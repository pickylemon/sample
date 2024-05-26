package com.portfolio.www.auth.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
	private String receiver;
	private String subject;
	private String text;
	
	public static EmailDto createEmailDto(String receiver, HashMap<String, String> mailContent) {
		return new EmailDto(receiver, mailContent.get("subject"), mailContent.get("text"));
	}
	
}
