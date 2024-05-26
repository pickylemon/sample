package com.portfolio.www.auth.dto;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component	
@RequiredArgsConstructor
public class EmailUtil {
	//QUESTION
	//mailSender를 빈 등록할때 sender 메일 주소를 setter로 넣어주었기때문에
	//그냥 mailSender에서 그 정보를 꺼내 쓰려한다.
	//(그리고 메일정보를 암호화해둔 상태라 더 일관성 있는듯)
	//다만, JavaMailSender는 인터페이스라서 필드를 가지지 않는다.
	//구현체인 JavaMailSenderImpl 타입으로 주입받아야 필드를 참조할 수 있다.
	//근데..이렇게 구현체 타입으로 빈 주입 받으면 유연성이 떨어지는 것 아닐까.
	private final JavaMailSenderImpl mailSender;
	
	public String sendMail(EmailDto email) {
		return sendMail(email, false);	
	}
	
	
	public String sendMail(EmailDto email, boolean isHtml) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setTo(email.getReceiver());
			messageHelper.setFrom(mailSender.getUsername());
			messageHelper.setSubject(email.getSubject());
			messageHelper.setText(email.getText(), isHtml);
			mailSender.send(message);
			
		} catch(Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return "Success";
	}
}
