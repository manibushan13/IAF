package com.innominds.itaf.frameworkengine;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Orchestrates Send Email class.
 * 
 * @author Praveen Gaddam
 * 
 */
public class SendEmail {
	private static final Logger LOGGER = Logger.getAnonymousLogger();
	private static String SMTP_SERVER;
	private static String SMTP_SERVER_PORT;
	private static String USER_NAME;
	private static String PASSWORD;
	private String from;
	private String to;
	private String subject;

	/** The message content. */
	@SuppressWarnings("unused")
	private final String messageContent = "Hi,\n\nAttached is the Automation TestSuite Report for Test application.\n\nRegards,\nAutomation Team";

	/**
	 * Send email report.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void sendEmailReport() throws InterruptedException {
		SMTP_SERVER = ConfigTestFixtures.getGmailSendEmailHost();
		SMTP_SERVER_PORT = ConfigTestFixtures.getSendEmailPort();
		USER_NAME = ConfigTestFixtures.getSendEmailFrom();
		PASSWORD = ConfigTestFixtures.getSendEmailPwd();
		from = ConfigTestFixtures.getSendEmailFrom();
		to = ConfigTestFixtures.getSendEmailTo();
		subject = "Automation Summary Report";

		final Session session = Session.getInstance(this.getEmailProperties(),
				new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USER_NAME, PASSWORD);
					}

				});

		try {
			final Message message = new MimeMessage(session);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			message.setSentDate(new Date());
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart
					.setText("Hi,\n\nAttached is the Automation Test Suite Report of Cirrus application.\n\nRegards,\nAutomation Team");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			Thread.sleep(15000);
			System.out.println("Report file path = "
					+ Constants.REPORTS_FILE_PATH);
			DataSource source = new FileDataSource(Constants.REPORTS_FILE_PATH);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ICT_Web_Automation_Reports.html");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			System.out.println("Sending email report...........");
			Transport.send(message);
			System.out.println("Report sent...........");
		} catch (final MessagingException ex) {
			LOGGER.log(Level.WARNING, "Error Message: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Gets the email properties.
	 * 
	 * @return the email properties
	 */
	public Properties getEmailProperties() {
		final Properties config = new Properties();
		config.put("mail.smtp.auth", "true");
		config.put("mail.smtp.starttls.enable", "true");
		config.put("mail.smtp.host", SMTP_SERVER);
		config.put("mail.smtp.port", SMTP_SERVER_PORT);
		return config;
	}
}