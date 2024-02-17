package com.infina.lifotradeapi.service.concretes;

import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.service.abstracts.EmailSendService;

import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class EmailSendManager implements EmailSendService {
	private final JavaMailSender mailSender;

	@Override
	public void sendPasswordResetMail(String to, String temporaryPassword) {
		String subject = "Parola Sıfırlama";
		String body = "Parolanızı sıfırlamak için geçici şifreniz: " + temporaryPassword;
		sendMail(to, subject, body);

	}

	private void sendMail(String to, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("E-mail gönderilemedi"));
		}

	}

}
