package com.circolo.security.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImplementation implements EmailService {
	
	private final JavaMailSender mailSender;

	public EmailServiceImplementation(JavaMailSender mailSender){ 
		
		this.mailSender = mailSender;
	}
	
	@Override
	public EmailResponse sendEmail(EmailRequest emailRequest) {
		// TODO Auto-generated method stub
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("raul01597@gmail.com");
		simpleMailMessage.setTo(emailRequest.getTo());
		simpleMailMessage.setSubject(emailRequest.getSubject());
		simpleMailMessage.setText(emailRequest.getMessage());
		this.mailSender.send(simpleMailMessage);
		
		return EmailResponse.builder()
				.successo("success")
				.build();
	}
	
	
	/*
	 * 
	 * 	public void sendEmail(String to, String subject, String text) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("raul01597@gmail.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		
		this.mailSender.send(simpleMailMessage);
	}
	 */
//	  public EmailResponse email(EmailRequest richiesta) {
//    
//  return EmailResponse.builder()
//     .successo(true);
//     .build();
//}
	
	
}
