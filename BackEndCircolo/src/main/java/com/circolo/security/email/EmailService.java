package com.circolo.security.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService{
	

	EmailResponse sendEmail(EmailRequest emailRequest);

}
