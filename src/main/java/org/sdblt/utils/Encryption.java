package org.sdblt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	private static final String KEY_MD5 = "MD5";
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static byte[] encryptToMD5(String data) {
		byte[] digestdata = null;
		try {
			MessageDigest alga = MessageDigest.getInstance(KEY_MD5);
			alga.update(data.getBytes());
			digestdata = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return digestdata;
	}

	public static String MD5(String pwd) {
		StringBuffer signatureData = new StringBuffer(pwd);
		byte[] byteMD5 = encryptToMD5(signatureData.toString());

		return toHexString(byteMD5);
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[((b[i] & 0xF0) >>> 4)]);
			sb.append(HEX_DIGITS[(b[i] & 0xF)]);
		}
		return sb.toString();
	}
}