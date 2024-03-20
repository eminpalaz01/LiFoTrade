package com.infina.lifotradeapi.service.concretes;

import com.infina.lifotradeapi.service.abstracts.EmailSendService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class TestEmailSendManager implements EmailSendService {

	@Override
	public void sendPasswordResetMail(String to, String temporaryPassword) {
		String subject = "Parola Sıfırlama";
		String body = "Parolanızı sıfırlamak için geçici şifreniz: " + temporaryPassword;
		sendMail(to, subject, body);

	}

	private void sendMail(String to, String subject, String body) {
		// just simulate
		System.out.println(body);
	}

}
