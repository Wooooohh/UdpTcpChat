package edu.csuft.message;


import edu.csuft.nchat.user.User;

public class SigninRespose implements Transferable{
	
	public static final byte Idcode=3;
	
	public String info;
	
	public SigninRespose(User user) {
		StringBuilder sbinfo = new StringBuilder();
		sbinfo.append("登陆成功\nfriend list：");
		for(User u:user.friendlist) {
			sbinfo.append("nick:"+u.userid+"  isActive:"+u.isActive()+ "    ");
		}
		//+"\n"+ "好友列表："+user.friendlist.toString();
		info =sbinfo.toString();
	}	
	
	public SigninRespose(String mssage) {
		this.info=mssage;
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
