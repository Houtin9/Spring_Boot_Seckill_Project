package com.itstyle.seckill.common.encrypt;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


public class SecurityDES {
	/*
	 * 使用KeyGenerator生成key
	 * 
	 * 其中，algorithm支持的算法有：AES、DES、DESEDE、HMACMD5、HMACSHA1、HMACSHA256、RC2等
	 * 全部支持的算法见官方文档
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#KeyGenerator
	 *  
	 */
	public static Key newKeyByKeyGenerator(String algorithm) throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		Key key = kg.generateKey();
		return key;
	}
	

	public static Key newKeyBySecretKeySpec(byte[] key, String algorithm) throws NoSuchAlgorithmException {
		return new SecretKeySpec(key, algorithm);
	}
	

	public static byte[] encrypt(String transformation, Key key, String password) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		//加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(password.getBytes());
	}
	

	public static String decrypt(String transformation, Key key, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		//解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(data);
		String password = new String(result);
		return password;
	}
	
	public static void main(String[] args) throws Exception {
		String password = "123456";
		
		String algorithm = "DES";
		String transformation = algorithm;
		
		//加密解密使用的都是同一个秘钥key
		Key key = newKeyByKeyGenerator(algorithm);
		System.out.println(" 秘钥: " + key);
		//加密
		byte[] passData = encrypt(transformation, key, password);
		//解密
		String pass = decrypt(transformation, key, passData);
		
		System.out.println("解密后的密码 : " + pass);
	}
}
