package org.sdblt.utils.sha1;


import java.util.UUID;

import org.springframework.util.Assert;

/**
 * 用户密码加密
 * @author rongxj
 * 生成随机的16位salt并经过1024次 sha-1 hash
 *
 */
//@Named("sha1EncryptService")
public class SHA1Encrypt {

	private static int hashIterations = 1024;

    private static boolean saltDisabled = false;
    
//	public static final int SALT_SIZE = 8;
	
    public static String encryptPassword(String plainPassword, String salts) throws IllegalArgumentException {
        Assert.notNull(plainPassword, "password cannot be empty.");
        if (saltDisabled) {
            salts = null;
        }
//        byte[] salts = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salts.getBytes(), hashIterations);
//		return Encodes.encodeHex(salts)+Encodes.encodeHex(hashPassword);
		return Encodes.encodeHex(hashPassword);
    }
    public String getCredentialsStrategy() {
        return Digests.SHA1;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public boolean saltDisabled() {
        return saltDisabled;
    }

    /*-------------- provide setter methods  ------------------*/

    public void setSaltDisabled(boolean disabled) {
        this.saltDisabled = disabled;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

//    public static void main(String[] args) {//7174073305164529900
//    	System.out.println(Encodes.encodeBase64("1112312323"));
//    	System.out.println(Encodes.decodeBase64String("123"));
//    	System.out.println(UUID.randomUUID().toString().replace("-", ""));
//    	System.out.println(UUID.fromString("fa9ec41c-b7db-438a-9c70-9256bd0e3f14").getLeastSignificantBits());
//		System.out.println(new SHA1Encrypt().encryptPassword("123", "6a785302-3e4e-44f3-bf65-48ac95b9bf00"));
//	}
}
