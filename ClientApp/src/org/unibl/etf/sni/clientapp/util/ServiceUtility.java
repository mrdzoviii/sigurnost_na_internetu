package org.unibl.etf.sni.clientapp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.unibl.etf.sni.clientapp.mysql.dao.DrivingLicenceDao;
import org.unibl.etf.sni.clientapp.mysql.dao.IdentityCardDao;
import org.unibl.etf.sni.clientapp.mysql.dao.PassportDao;
import org.unibl.etf.sni.clientapp.mysql.dto.Document;


public class ServiceUtility {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNPQRSTUVWXYZ0123456789";
	
	
	public static final ResourceBundle bundle = ResourceBundle.getBundle("org.unibl.etf.sni.clientapp.config.ClientApp");
	private static final int LENGTH = Integer.parseInt(bundle.getString("token.length"));
	public static String sha512Hash(String plainText) throws NoSuchAlgorithmException, NoSuchProviderException {
		setProvider();
		MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");
		byte[] digest = mda.digest(plainText.trim().getBytes());
		return Hex.encodeHexString(digest);
	}
	public static String serialGenerator() {
		StringBuilder builder = new StringBuilder();
		int count = LENGTH;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();

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
	
	public static List<Document> getAllByDate(Date date){
		List<Document> documents=new ArrayList<>();
		documents.addAll(PassportDao.getByDate(date));
		documents.addAll(IdentityCardDao.getByDate(date));
		documents.addAll(DrivingLicenceDao.getByDate(date));
		return documents;
	}
	
	public static List<Document> getAllByPid(String pid){
		List<Document> documents=new ArrayList<>();
		documents.addAll(PassportDao.getByUid(pid));
		documents.addAll(IdentityCardDao.getByUid(pid));
		documents.addAll(DrivingLicenceDao.getByUid(pid));
		return documents;
	}
	
	public static List<Document> getAll(){
		List<Document> documents=new ArrayList<>();
		documents.addAll(PassportDao.getAll());
		documents.addAll(IdentityCardDao.getAll());
		documents.addAll(DrivingLicenceDao.getAll());
		return documents;
	}

	public static byte[] getPdf(Document doc) {
		return PdfDocument.makePdf(doc);
	}
	
}
