package org.unibl.etf.sni.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.unibl.etf.sni.beans.AndroidBean;

public class ServiceUtility {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private static final Map<String,String> testData=new HashMap<>();
	static {
		testData.put("jovan", "joco-95@hotmail.com");
	}
	public static String generateAndroidToken(AndroidBean bean) {
		if(testData.containsKey(bean.getUsername())) {
			try {
				if(match(bean.getUsername()+testData.get(bean.getUsername()),bean.getHash())) {
					String token=tokenGenerator();
					sendMail(testData.get(bean.getUsername()),token);
					return token;
				}
			} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
				e.printStackTrace();
			}
		}
		return "NOT_SENT";
	}
	
	public static final ResourceBundle bundle = ResourceBundle.getBundle("org.unibl.etf.sni.config.SniConfig");
	private static final int LENGTH = Integer.parseInt(bundle.getString("token.length"));

	public static String sha512Hash(String plainText) throws NoSuchAlgorithmException, NoSuchProviderException {
		setProvider();
		MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");
		byte[] digest = mda.digest(plainText.trim().getBytes());
		return Hex.encodeHexString(digest);
	}

	public static boolean match(String plainText, String hash)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		if (plainText == null || hash == null) {
			return false;
		}
		setProvider();
		return hash.equals(sha512Hash(plainText));
	}

	private static void setProvider() {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	public static boolean sendMail(String mailTo, String code) {
		final String username = bundle.getString("username");
		final String password = bundle.getString("password");
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", bundle.getString("mail.smtp.auth"));
		prop.put("mail.smtp.host", bundle.getString("mail.smtp.host"));
		prop.put("mail.smtp.port", bundle.getString("mail.smtp.port"));
		prop.put("mail.smtp.starttls.enable", bundle.getString("mail.smtp.starttls.enable"));

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			String text = "Your token is:" + code;
			Message message = new MimeMessage(session);
			message.setContent(text, "text/plain");
			message.setFrom(new InternetAddress("sni@gmail.com", "SNI"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject("SNI Token");
			Transport.send(message);
			return true;

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String tokenGenerator() {
		StringBuilder builder = new StringBuilder();
		int count = LENGTH;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();

	}
	
	public static void main(String[] args) {
		sendMail("joco-95@hotmail.com", tokenGenerator());
	}

}
