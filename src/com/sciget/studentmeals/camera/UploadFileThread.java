package com.sciget.studentmeals.camera;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.sciget.mvc.Log;

public class UploadFileThread extends Thread {
	private String ip;
	private File file;
	private String sha1;
    private boolean done;
	
	public UploadFileThread(String ip, File file, String sha1) {
		this.ip = ip;
		this.file = file;
		this.sha1 = sha1;
	}
	
	public void run() {
		try {
			send();
			done = true;
		} catch (IOException e) {
			Log.info(e.toString());
		}
	}
	
	public boolean isDone() {
	    return done;
	}
	
	public void send() throws IOException {
	    Socket socket = new Socket(InetAddress.getByName(ip), 5557);
	    OutputStream outputStream = socket.getOutputStream();
	    FileInputStream fis = new FileInputStream(file);
	    BufferedInputStream bis = new BufferedInputStream(fis);

	    outputStream.write("QVE5vtaA5T".getBytes()); // tip (10 bajtov)
	    outputStream.write(0x01); // verzija (1 bajt)
	    outputStream.write(sha1.getBytes()); // sha1 hash datoteke
	    
	    int i;
	    byte[] buffer = new byte[10*1024];
	    while ((i = bis.read(buffer)) != -1) {
	    	outputStream.write(buffer, 0, i);
	    }

	    outputStream.flush();
	    fis.close();
	    bis.close();
	    outputStream.close();
	    socket.close();
	}
}
