package edu.csuft.message;



public class SigninRequest implements Transferable{
	
	public static final byte Idcode =1;
	
	String userid;
	
	public SigninRequest(String userid) {
		this.userid=userid;
	}

	@Override
	public String getMssage() {
		
		return userid;
	}

	@Override
	public byte getIdcode() {
		return Idcode;
	}
	
}
