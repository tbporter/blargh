package com.travisbporter.blargh;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Crypt {
	
	static KeyPair _keyPair;
	static public void genKey() throws NoSuchAlgorithmException, NoSuchPaddingException{
		_keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
	}
	static public byte[] encrypt(byte[] d) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, _keyPair.getPublic());
		return cipher.doFinal(d);
	}
	static public byte[] decrypt(byte[] d) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, _keyPair.getPrivate());
		return cipher.doFinal(d);
	}
	
	static public String byteToString(byte[] d) throws UnsupportedEncodingException{
		return new String(d, "UTF-8");
	}
}
