package com.innominds.itaf.frameworkengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.testng.Assert;

import com.innominds.itaf.utils.PropertyFileUtils;

/**
 * The Class VerifyEmail.
 * 
 * @author Praveen Gaddam and Kiran Kumar Cherukuri
 */
public class VerifyEmail {
	public static String confirmationEmailUrl;
	public static String confirmationEmailUrlChangePassword;
	public static String emailIdFromConfirmationEmail;
	public static boolean errorFound = false;
	public static boolean contentFound;
	public static boolean errorFoundNew = false;
	
	public static String errorMessage;

	/**
	 * Email verification.
	 *
	 * @param emailId the email id
	 * @param password the password
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void emailVerification(String emailId, String password) throws MessagingException, IOException {
		String error = "noerror";
		errorMessage = "";
		errorFound = false;
		int time = 0;
		try {
			OUTERLOOP: for (int k = 1; k <= 10; k++) {
				time = time + 15;
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Email verification in progress........");
				Properties pros = System.getProperties();
				Session session = Session.getDefaultInstance(pros, null);
				Store store = session.getStore("imaps");
				System.out.println("Email ID is === " + emailId);
				String[] emailIdSplit = emailId.split("@");
				String emailDomain = emailIdSplit[1];
				// System.out.println("Email Domain is === " + emailDomain);
				if (emailDomain.equalsIgnoreCase("gmail")) {
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "gmail.email.host"), emailId, password);
				} else if(emailDomain.equalsIgnoreCase("outlook"))
				{
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "outlook.email.host"), emailId, password);
				}else
				{
					System.out.println("Email id is NOT valid");
				}
				Folder folder = store.getFolder("Inbox");
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.getMessages();
				for (int i = 1; i <= 5; i++) {
					Message message2 = messages[messages.length - i];
					System.out.println("message subject ===> " + message2.getSubject());
					if (message2.getSubject().equalsIgnoreCase("Validate Report for Location")) {
						try {
							if (message2.getContent().toString().contains("Error Details")) {
								errorFound = true;
								error = message2.getContent().toString();
								System.out.println("Email received with error");
								break OUTERLOOP;
							} else {
								System.out.println("Email received and verified.......");
								break OUTERLOOP;
							}
						} catch (IOException ex) {
							System.out.println(ex);
						}
					}
				}
				folder.close(false);
				store.close();
				if (k == 10) {
					Assert.fail(
							"Validate Report for Location Mail not received even after waiting for 2 min 30sec, hence failing the test case");
				}
			}
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
		}
		if (!error.equalsIgnoreCase("noerror")) {
			errorMessage = formatErrorMessage(error);
		}
	}

	/**
	 * Format error message.
	 *
	 * @param bodycontent the bodycontent
	 * @return the string
	 */
	public static String formatErrorMessage(String bodycontent) {
		String extractErrors = bodycontent.replaceAll("<[^>]*>", "").trim();
		String errorMessage = StringUtils.substringBetween(extractErrors, "Error Details: ", "The ON!Track Team");
		String finalErrorMessage = errorMessage.trim();
		return finalErrorMessage;
	}
	/**
	 * Email verification.
	 * 
	 * @param finalFileName
	 *            the final file name
	 * @param emailId
	 *            the email id
	 * @param password
	 *            the password
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void emailVerification(String finalFileName, String emailId,
			String password) throws MessagingException, IOException {
		errorFound = false;
		int time = 0;
		try {
			OUTERLOOP: for (int k = 1; k <= 10; k++) {
				time = time + 15;
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}

				System.out.println("Email verification in progress........");
				Properties pros = System.getProperties();
				Session session = Session.getDefaultInstance(pros, null);
				Store store = session.getStore("imaps");
				// System.out.println("Email ID is === " + emailId);
				String[] emailIdSplit = emailId.split("@");
				String emailDomain = emailIdSplit[1];
				// System.out.println("Email Domain is === " + emailDomain);

				/*
				 * Based on the email id mentioned under .property files this
				 * code works. If we use email id with
				 * innominds,com/gmail.com based on the domains the
				 * email verification works in the respected mail accounts.
				 */
				if (emailDomain.equalsIgnoreCase("outlook")) {
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "outlook.email.host"),	emailId, password);
				} else if (emailDomain.equalsIgnoreCase("gmail")) {
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "gmail.email.host"), emailId, password);
				} else {
					System.out.println("Email id is NOT valid");
				}

				// IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
				Folder folder = store.getFolder("Inbox");
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.getMessages();
				for (int i = 1; i <= 20; i++) {
					Message message2 = messages[messages.length - i];

					if (message2.getSubject().equalsIgnoreCase(
							"Uploaded File Matrix")
							&& message2.getContent().toString().trim()
									.contains(finalFileName)) {
						try {
							if (message2.getContent().toString().trim()
									.contains("Error Details")) {
								errorFound = true;
								// System.out.println("Email received with error");
								/*
								 * System.out .println(
								 * "Total time taken for receiving Uploaded File Matrix Mail is: "
								 * + time);
								 */
								break OUTERLOOP;
							} else {
								System.out
										.println("Email received and verified.......");
								errorFound = false;
								/*
								 * System.out .println(
								 * "Total time taken for receiving Uploaded File Matrix Mail is: "
								 * + time);
								 */
								break OUTERLOOP;
							}
						} catch (IOException ex) {
							System.out.println(ex);
						}
					}
				}
				folder.close(false);
				store.close();
				if (k == 10) {
					Assert.fail("Upload File Matrix Mail not received even after waiting for 2 min 30sec, hence failing the test case");
				}
			}
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
			// e.printStackTrace();
		}
	}

	/**
	 * Gets the confirmation email.
	 * 
	 * @param FirstName
	 *            the first name
	 * @param LastName
	 *            the last name
	 * @param emailId
	 *            the email id
	 * @param password
	 *            the password
	 * @return the confirmation email
	 * @throws FileNotFoundException 
	 */
	public static void getConfirmationEmail(String FirstName, String LastName,
			String emailId, String password) throws FileNotFoundException {
		int time = 0;
		System.out.println();
		System.out
				.println("ON!Track Activation Mail Email verification started......");
		System.out.println();
		errorFoundNew=false;
		try {
			OUTERLOOP: for (int k = 1; k <= 10; k++) {
				try {
					time = time + 15;
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}
				confirmationEmailUrl = "";
				System.out.println("Email verification in progress........");
				Properties pros = System.getProperties();
				Session session = Session.getDefaultInstance(pros, null);
				Store store = session.getStore("imaps");

				String[] emailIdSplit = emailId.split("@");
				String emailDomain = emailIdSplit[1];
				// System.out.println("Email Domain is === " + emailDomain);

				/*
				 * Based on the email id mentioned under .property files this
				 * code works. If we use email id with
				 * innominds,com/gmail.com based on the domains the
				 * email verification works in the respected mail accounts.
				 */
				if (emailDomain.equalsIgnoreCase("outlook")) {
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "outlook.email.host"),995, emailId, password);
				} else if (emailDomain.equalsIgnoreCase("gmail")) {
					store.connect("imap.gmail.com", emailId, password);
				} else {
					System.out.println("Email id is NOT valid");
				}

				Folder folder = store.getFolder("Inbox");
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.getMessages();
				for (int i = 1; i <= 20; i++) {
					Message message2 = messages[messages.length - i];
					Object content = null;
					try {
						content = message2.getContent();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (message2.getSubject().equalsIgnoreCase(
							"ON!Track Activation Mail")
							&& content.toString().trim().contains(FirstName)
							&& content.toString().trim().contains(LastName)) {
						System.out.println();
						System.out
								.println("ON!Track Activation Email Mail Received and Verified!!!");
						System.out.println();
						String contentStr = content.toString().trim();
						org.jsoup.nodes.Document doc = Jsoup.parse(contentStr);
						org.jsoup.nodes.Element link = doc.select("a").first();
						confirmationEmailUrl = link.attr("href");
						System.out
								.println("Link from the mail is =============> "
										+ confirmationEmailUrl);
						String readMailIdFromConfirmationEmail = readMailid(contentStr);
						System.out
								.println("User id from the mail is =============> "
										+ readMailIdFromConfirmationEmail);
						System.out.println();
						emailIdFromConfirmationEmail = readMailIdFromConfirmationEmail;
						/*
						 * System.out .println(
						 * "Total time taken for receiving ON!Track Activation Mail is: "
						 * + time);
						 */
						break OUTERLOOP;
					}
				}
				folder.close(false);
				store.close();
				if (k == 10) {
					if (confirmationEmailUrl.isEmpty()) {
						System.out
								.println("Expected ON!Track Activation Mail not received even after waiting for 2 min 30 sec, so confirmation link will be Empty");
						errorFoundNew=true;
						Assert.fail("Expected ON!Track Activation Mail not received even after waiting for 2 min 30 sec, so confirmation link will be Empty");
						
						break OUTERLOOP;
					} else {
						System.out
								.println("Expected ON!Track Activation Mail not received even after waiting for 2 min 30 sec, hence failing the test case");
						errorFoundNew=true;
						Assert.fail("Expected ON!Track Activation Mail not received even after waiting for 2 min 30 sec, hence failing the test case");
						break OUTERLOOP;
					}
				}
			}
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Gets the email change confirmation email.
	 * 
	 * @param FirstName
	 *            the first name
	 * @param LastName
	 *            the last name
	 * @param newemailid
	 *            the newemailid
	 * @param emailId
	 *            the email id
	 * @param password
	 *            the password
	 * @return the email change confirmation email
	 * @throws FileNotFoundException 
	 */
	public static void getEmailChangeConfirmationEmail(String FirstName,
			String LastName, String newemailid, String emailId, String password) throws FileNotFoundException {
		int time = 0;
		String old_email_emailchangecontent = "Your ON!Track email was changed to "
				+ newemailid + ".";
		contentFound = false;
		System.out.println();
		System.out
				.println("ON!Track Notification Mail Email verification started......");
		System.out.println();
		try {
			OUTERLOOP: for (int k = 1; k <= 7; k++) {
				try {
					time = time + 15;
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}
				confirmationEmailUrl = "";
				System.out.println("Email verification in progress........");
				Properties pros = System.getProperties();
				Session session = Session.getDefaultInstance(pros, null);
				Store store = session.getStore("imaps");

				String[] emailIdSplit = emailId.split("@");
				String emailDomain = emailIdSplit[1];
				// System.out.println("Email Domain is === " + emailDomain);

				/*
				 * Based on the email id mentioned under .property files this
				 * code works. If we use email id with
				 * innominds,com/gmail.com based on the domains the
				 * email verification works in the respected mail accounts.
				 */
				if (emailDomain.equalsIgnoreCase("outlook")) {
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "outlook.email.host"),	995, emailId, password);
				} else if (emailDomain.equalsIgnoreCase("gmail")) {
					store.connect("imap.gmail.com", emailId, password);
				} else {
					System.out.println("Email id is NOT valid");
				}

				Folder folder = store.getFolder("Inbox");
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.getMessages();
				for (int i = 1; i <= 20; i++) {
					Message message2 = messages[messages.length - i];
					Object content = null;
					try {
						content = message2.getContent();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (message2.getSubject().equalsIgnoreCase(
							"ON!Track Notification Mail")
							&& content.toString().trim().contains(FirstName)
							&& content.toString().trim().contains(LastName)
							&& content.toString().trim()
									.contains(old_email_emailchangecontent)) {
						contentFound = true;
						System.out.println();
						System.out
								.println("ON!Track Notification Mail Email Received and Verified!!!");
						System.out.println();
						System.out
								.println("ON!Track Notification Mail Received to old Email ID \""
										+ emailId
										+ "\" and has Expected new \""
										+ newemailid + "\" details");
						break OUTERLOOP;
					}
				}
				folder.close(false);
				store.close();
				if (k == 7) {
					System.out.println("Expected Content \""
							+ old_email_emailchangecontent
							+ "\" Not Received to old Email ID \"" + emailId
							+ "\" even after waiting for 2 minutes");
					Assert.fail("Expected Content \""
							+ old_email_emailchangecontent
							+ "\" Not Received to old Email ID \"" + emailId
							+ "\" even after waiting for 2 minutes");
					break OUTERLOOP;
				}
			}
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Gets the confirmation email change password.
	 * 
	 * @param FirstName
	 *            the first name
	 * @param LastName
	 *            the last name
	 * @param emailId
	 *            the email id
	 * @param password
	 *            the password
	 * @return the confirmation email change password
	 * @throws FileNotFoundException 
	 */
	public static void getConfirmationEmailChangePassword(String FirstName,
			String LastName, String emailId, String password) throws FileNotFoundException {
		int time = 0;
		try {
			OUTERLOOP: for (int k = 1; k <= 10; k++) {
				try {
					time = time + 15;
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				confirmationEmailUrl = "";
				System.out.println("Email verification in progress........");
				Properties pros = System.getProperties();
				Session session = Session.getDefaultInstance(pros, null);
				Store store = session.getStore("imaps");

				String[] emailIdSplit = emailId.split("@");
				String emailDomain = emailIdSplit[1];

				/*
				 * Based on the email id mentioned under .property files this
				 * code works. If we use email id with
				 * innominds,com/gmail.com based on the domains the
				 * email verification works in the respected mail accounts.
				 */
				if (emailDomain.equalsIgnoreCase("outlook")) {
					store.connect(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "outlook.email.host"), 995, emailId, password);
				} else if (emailDomain.equalsIgnoreCase("gmail")) {
					store.connect("imap.gmail.com", emailId, password);
				} else {
					System.out.println("Email id is NOT valid");
				}

				Folder folder = store.getFolder("Inbox");
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.getMessages();
				for (int i = 1; i <= 20; i++) {
					Message message2 = messages[messages.length - i];
					Object content = null;
					try {
						content = message2.getContent();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (message2.getSubject().equalsIgnoreCase(
							"ON!Track - your new password")
							&& content.toString().trim().contains(FirstName)
							&& content.toString().trim().contains(LastName)) {
						System.out
								.println("Email received and verified.......");
						String contentStr = content.toString().trim();
						org.jsoup.nodes.Document doc = Jsoup.parse(contentStr);
						org.jsoup.nodes.Element link = doc.select("a").first();
						confirmationEmailUrlChangePassword = link.attr("href");
						System.out
								.println("Link from the mail is ===========================================> "
										+ confirmationEmailUrl);
						String readMailIdFromConfirmationEmail = readMailid(contentStr);
						System.out
								.println("User id from the mail is ===========================================> "
										+ readMailIdFromConfirmationEmail);
						emailIdFromConfirmationEmail = readMailIdFromConfirmationEmail;
						/*
						 * System.out .println(
						 * "Total time taken for receiving ON!Track Activation Mail is: "
						 * + time);
						 */
						break OUTERLOOP;
					}
				}
				folder.close(false);
				store.close();
				if (k == 10) {
					if (confirmationEmailUrl.isEmpty()) {
						System.out
								.println("ON!Track Activation Mail not received even after waiting for 2 min 30 sec, so confirmation link will be Empty");
						Assert.fail("ON!Track Activation Mail not received even after waiting for 2 min 30 sec, so confirmation link will be Empty");
						break OUTERLOOP;
					} else {
						System.out
								.println("ON!Track Activation Mail not received even after waiting for 2 min 30 sec, hence failing the test case");
						Assert.fail("ON!Track Activation Mail not received even after waiting for 2 min 30 sec, hence failing the test case");
						break OUTERLOOP;
					}
				}
			}
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
		}
	}

	/**
	 * Read mailid.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String readMailid(String input) {
		Pattern p = Pattern.compile(
				"\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(input);
		String mailId = "";
		while (matcher.find()) {
			mailId = matcher.group();
			break;
		}
		return mailId;
	}

}
