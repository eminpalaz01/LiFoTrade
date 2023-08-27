package com.infina.lifotradeapi.service.abstracts;

public interface EmailSendService {
	
	void sendPasswordResetMail(String to, String temporaryPassword);
	
}
