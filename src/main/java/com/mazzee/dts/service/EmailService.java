/**
 * 
 */
package com.mazzee.dts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 *
 */
@Service
public class EmailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	private MailSender mailSender;

	@Autowired
	public EmailService(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public boolean sendEmail(String receiverEmail, String subject, String emailMessage) {
		boolean isEmailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(receiverEmail);
		mailMessage.setText(emailMessage);
		mailMessage.setSubject(subject);
		try {
			mailSender.send(mailMessage);
			isEmailSent = true;
		} catch (final MailException e) {
			LOGGER.error("Exception occured when sending email to {}", receiverEmail, e);
		}
		return isEmailSent;
	}
}
