package org.witchcraft.model.mail;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.witchcraft.model.support.errorhandling.CriticalException;

/**
 * Utility implementation of MailerTask - classes derived from it must implement
 * createMessage task
 * 
 * @author jsingh
 * 
 */
public abstract class AbstractMailer implements MailerTask {

	private static final Logger logger = Logger.getLogger(AbstractMailer.class);

	@Autowired
	protected MailSender mailSender;

	protected MailMessage mailMessage;

	@Autowired
	protected VelocityEngine velocityEngine;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public MailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bizobjects.MailerTask#sendMessage(java.util.Map)
	 */
	public void sendMessage() {
		try {
			SimpleMailMessage message = (SimpleMailMessage) createMessage();
			mailSender.send(message);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			logger.error("Couldnt send mail", ex);
			throw new CriticalException("Sendim mail failed" + getTemplateName(), ex);
		}
	}

	protected abstract MailMessage createMessage();

	public abstract String getTemplateName();

	protected String createMessageBody(Map mapParams) {

		String result = null;
		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, getTemplateName(), mapParams);
		} catch (VelocityException e) {
			logger.error("Template merging failed" + getTemplateName(), e);
			throw new CriticalException("Template merging failed" + getTemplateName(), e);
		}
		return result;
	}

}
