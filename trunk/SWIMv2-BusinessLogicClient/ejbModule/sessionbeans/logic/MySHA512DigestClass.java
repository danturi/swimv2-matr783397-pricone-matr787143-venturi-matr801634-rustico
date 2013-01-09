package sessionbeans.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MySHA512DigestClass {
	
	public static String sha512HexDigest(String plainText){
	MessageDigest md = null;
	try {
		md = MessageDigest.getInstance("SHA-512");
		md.update(plainText.getBytes());
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "";
	}

}
