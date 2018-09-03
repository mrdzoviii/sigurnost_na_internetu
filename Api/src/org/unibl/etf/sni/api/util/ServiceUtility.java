package org.unibl.etf.sni.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.unibl.etf.sni.api.beans.AndroidBean;
import org.unibl.etf.sni.api.beans.DocumentsBean;
import org.unibl.etf.sni.api.beans.WebServiceBean;
import org.unibl.etf.sni.api.mysql.dao.DrivingLicenceDao;
import org.unibl.etf.sni.api.mysql.dao.IdentityCardDao;
import org.unibl.etf.sni.api.mysql.dao.PassportDao;
import org.unibl.etf.sni.api.mysql.dao.TokenDao;
import org.unibl.etf.sni.api.mysql.dao.UserDao;
import org.unibl.etf.sni.api.mysql.dto.DrivingLicenceDto;
import org.unibl.etf.sni.api.mysql.dto.IdentityCardDto;
import org.unibl.etf.sni.api.mysql.dto.PassportDto;
import org.unibl.etf.sni.api.mysql.dto.TokenDto;
import org.unibl.etf.sni.api.mysql.dto.UserDto;

public class ServiceUtility {
	public static final ResourceBundle bundle = ResourceBundle.getBundle("org.unibl.etf.sni.api.config.SniConfig");
	private static final String ALPHA_NUMERIC_STRING = bundle.getString("alpha.numeric");
	public static final SimpleDateFormat sdf = new SimpleDateFormat(bundle.getString("date.format"));

	public static String generateAndroidToken(AndroidBean bean) {
		if (bean.getUsername() != null && bean.getPassword() != null) {
			UserDto user = UserDao.getByUsername(bean.getUsername());
			if (user != null && bean.getUsername().equals(user.getUsername())) {
				try {
					if (match(bean.getPassword(), user.getPassword())) {
						TokenDto token = TokenDao.getByUserId(user.getId());
						String genToken = tokenGenerator();
						if (token != null) {
							token.setToken(genToken);
							token.setSso(false);
							token.setValidUntil(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
							System.out.println(token.getValidUntil()+"    "+token.getToken());
							if (TokenDao.update(token));
							new Thread(new Runnable() {
								@Override
								public void run() {

									sendMail(user.getEmail(), genToken);
								}
							}).start();
							return token.getToken() + "#" + token.getValidUntil().getTime()+"#"+user.getAdmin()+"#"+user.getPid();
						} else {
							token = new TokenDto(null, user.getId(), user,
									new Date(System.currentTimeMillis() + 30 * 60 * 1000), genToken,false);
							if (TokenDao.insert(token)) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										sendMail(user.getEmail(), genToken);
									}
								}).start();
								return token.getToken() + "#" + token.getValidUntil().getTime()+"#"+user.getAdmin()+"#"+user.getPid();
							}
						}
					}
				} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
					e.printStackTrace();
				}
			}
		}
		return "NOT_SENT";
	}

	
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
		final String username = bundle.getString("mail.username");
		final String password = bundle.getString("mail.password");
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



	public static DocumentsBean getDocumentsByDate(Date issueDate) {
		List<IdentityCardDto> identityCards = IdentityCardDao.getByDate(issueDate);
		List<PassportDto> passports = PassportDao.getByDate(issueDate);
		List<DrivingLicenceDto> drivingLicences = DrivingLicenceDao.getByDate(issueDate);
		DocumentsBean bean = new DocumentsBean();
		bean.setDrivingLicences(drivingLicences);
		bean.setIdentityCards(identityCards);
		bean.setPassports(passports);
		return bean;
	}

	public static DocumentsBean getDocumentsByUid(String uid, String username) {
		UserDto user = UserDao.getByUsername(username);
		if (isAdmin(username) || user.getPid().equals(uid)) {
			List<IdentityCardDto> identityCards = IdentityCardDao.getByUid(uid);
			List<PassportDto> passports = PassportDao.getByUid(uid);
			List<DrivingLicenceDto> drivingLicences = DrivingLicenceDao.getByUid(uid);
			DocumentsBean bean = new DocumentsBean();
			bean.setDrivingLicences(drivingLicences);
			bean.setIdentityCards(identityCards);
			bean.setPassports(passports);
			return bean;
		}
		return null;
	}

	public static boolean checkServiceBean(WebServiceBean bean)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		if (bean!=null && bean.getUsername() != null && bean.getWsHash() != null) {
			UserDto user = UserDao.getByUsername(bean.getUsername());
			TokenDto token = TokenDao.getByUserId(user.getId());
			if (new Date().before(token.getValidUntil()) && bean.getUsername().equals(user.getUsername())
					&& match(user.getPassword() + token.getToken(), bean.getWsHash())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAdmin(String username) {
		UserDto user = UserDao.getByUsername(username);
		if (user != null) {
			return user.getAdmin();
		}
		return false;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		System.out.println(sha512Hash("petar"));
		System.out.println(sha512Hash("milica"));
	}
}
