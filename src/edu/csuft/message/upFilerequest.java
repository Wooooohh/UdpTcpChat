package edu.csuft.message;

import java.io.File;

public class upFilerequest implements Transferable{
	public static final byte Idcode =7;
	String info;
	
	public upFilerequest(String info) {
		this.info=info;
	}
	
	public upFilerequest(File file) {
	
		info= file.getPath()+"."+file.length();
	}
	@Override
	public byte getIdcode() {
		return Idcode;
	}

	@Override
	public String getMssage() {
		return info; 
	}
}
