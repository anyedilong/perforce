package org.sdblt.utils.encode;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HttpEncode {
	
	private static HttpEncode instance = null;

	public static HttpEncode getInstance() {
		if (instance == null) {
			synchronized (HttpEncode.class) {
				if (instance == null) {
					instance = new HttpEncode();
				}
			}
		}
		return instance;
	}

	private HttpEncode() {
		
	}


	// 加密
	public String createEncode(String json) {
		byte[] desKyeByte = My3des.getDesKey();
		if (desKyeByte == null) {
			return "";
		}
		String desKey16 = StringTools.byteToHexString(desKyeByte);
		String desKeyHalfOne = desKey16.substring(0, desKey16.length() / 2);
		String desKeyHalfTwo = desKey16.substring(desKey16.length() / 2, desKey16.length());

		byte[] byteEnc = null;
		try {
			byteEnc = My3des.DesEncode(json.getBytes("UTF-8"), desKyeByte);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (byteEnc == null) {
			return "";
		}
		String encode16 = StringTools.byteToHexString(byteEnc);
		StringBuffer sb = new StringBuffer();
		sb.append(desKeyHalfOne);
		sb.append("|");
		sb.append(encode16);
		sb.append("|");
		sb.append(desKeyHalfTwo);
		sb.append("|");
		String md516 = Md5Encrypt.getMD5Code(desKeyHalfOne + encode16 + desKeyHalfTwo);
		sb.append(md516);
		return sb.toString();
	}

	// 解析
	public String parseDecode(String encodeStr) {
		String[] splits = encodeStr.split("\\|");
		if (splits.length == 4) {
			String currentMd5 = Md5Encrypt.getMD5Code(splits[0] + splits[1] + splits[2]);
			// 比较MD5是否正确
			boolean bool = MessageDigest.isEqual(StringTools.hexStringToBytes(splits[3]),
					StringTools.hexStringToBytes(currentMd5));
			if (bool) {
				byte[] desKey = StringTools.hexStringToBytes(splits[0] + splits[2]);
				byte[] byteDec = null;
				String result = "";
				try {
					byteDec = My3des.DesDecode(StringTools.hexStringToBytes(splits[1]), desKey);
					result =new String(byteDec, "UTF-8");
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return result;
			}
		}
		return "";
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InterruptedException {

	}
}
