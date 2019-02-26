package edu.csuft.message;

public class ChatContentrequest implements Transferable{

	public static final byte Idcode =4;
	String info;
	public ChatContentrequest(String sender,String receiver,String info) {
		this.info = receiver+"."+sender+"发送:"+info;
	}
	
	public ChatContentrequest(String info) {
		this.info=info;
	}
	
	@Override
	public byte getIdcode() {

		return Idcode;
	}

	@Override
	public String getMssage() {
		// TODO Auto-generated method stub
		return info; 
	}
}
