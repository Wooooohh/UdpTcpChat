package edu.csuft.message;

import java.util.HashSet;

import edu.csuft.nchat.user.User;

public class UpdataFriendList implements Transferable{
	
	public static final byte Idcode=2;
	
	public String info;
	
	public UpdataFriendList(HashSet<User> activefriendlist) {
		StringBuilder sbinfo = new StringBuilder();
		sbinfo.append("updataed friend list：");
		for(User u:activefriendlist) {
			sbinfo.append("nick:"+u.userid+"  isActive:"+u.isActive()+ "    ");
		}
		info =sbinfo.toString();
	}

	public UpdataFriendList(String info) {
		this.info=info;
	}

	@Override
	public byte getIdcode() {
		// TODO Auto-generated method stub
		return Idcode;
	}

	@Override
	public String getMssage() {
		// TODO Auto-generated method stub
		return info;
	}


}
