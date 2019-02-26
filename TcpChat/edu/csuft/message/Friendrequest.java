package edu.csuft.message;

public class Friendrequest implements Transferable{
	public static final byte Idcode =5;
	String info;
	public Friendrequest(String sender,String receiver) {
		this.info = receiver+"."+sender+",发送:请求添加好友,请输入同意或者不同意";
	}
	
	public Friendrequest(String info) {
		this.info=info;
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
