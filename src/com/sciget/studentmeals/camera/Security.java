package com.sciget.studentmeals.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sciget.mvc.Log;

public class Security {
	public static final char[] CHAR_FOR_BYTE = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	private static byte[] sha1(byte[] value) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-1"); 
	    return md.digest(value);
	}
	
	private static String sha1(String value) {
		try {
			return encode(sha1(value.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			Log.high("Nepodprti algoritem: " + e.toString());
		} catch (UnsupportedEncodingException e) {
			Log.high("Nepodprto kodiranje: " + e.toString());
		} catch (Exception e) {
			Log.high("Napaka pri izdelavi sha1 hasha.");
		}
		return null;
	}
    
    public static String encode(byte[] data) {
        char[] store = new char[data.length * 2];
        int val;
        int charLoc;
        for (int i = 0; i < data.length; i++) {
            val = (data[i] & 0xFF);
            charLoc= i << 1;
            store[charLoc] = CHAR_FOR_BYTE[val >>> 4];
            store[charLoc+1] = CHAR_FOR_BYTE[val & 0x0F];
        }
        return new String(store);
    }
    
    public static String fileSha1(File file) {
    	try {
	    	MessageDigest md = MessageDigest.getInstance("SHA1");
	    	InputStream inputStream = new FileInputStream(file);
	     
	    	int read = 0;
	    	byte[] bytes = new byte[1024*100];
	    	
			while ((read = inputStream.read(bytes)) != -1) {
				md.update(bytes, 0, read);
			}
	    	
	    	byte[] mdbytes = md.digest();
	    	
	    	inputStream.close();
	    	
	    	return Security.encode(mdbytes);
    	} catch (Exception e) {
    		Log.info("Napaka pri računanju sha1 hasha: " + e.toString());
    	}
    	
    	return null;
    }
}
