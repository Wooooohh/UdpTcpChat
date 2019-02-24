package edu.csuft.message;

public class Friendrespose implements Transferable{
	public static final byte Idcode =6;
	String info;
	public Friendrespose(String sender,boolean isAgree) {
		if(isAgree)
			info=sender +",同意好友请求";
		else
			info =sender +",拒绝好友请求";
	}
	public Friendrespose(String info) {
		this.info=info;
	}
	
	public Friendrespose(String userid, String resposeinfo) {
		info=userid+resposeinfo;
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
