package org.sdblt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
	
	public void sha1(String text){
		 MessageDigest md=null;
		 try {
			md = MessageDigest.getInstance("SHA-1");

			if (text.getBytes() != null) {
				md.update(text.getBytes());
			}
			byte[] digest = md.digest(text.getBytes());
			String n=Test.byteToString(digest);
			System.out.println(n);
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
	}
	private static String byteToString(byte[] digest) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
		String tempStr = Integer.toHexString(digest[i] & 0xff);
		if (tempStr.length() == 1) {
		buf.append("0").append(tempStr);
		} else {
		buf.append(tempStr);
		}
		}
		return buf.toString().toLowerCase();
		}

	public static void main(String[] args) {
		Test a=new Test();
		a.sha1("123");
		
		// TODO Auto-generated method stub
//		org.springframework.web.util.Log4jConfigListener
	}

}
