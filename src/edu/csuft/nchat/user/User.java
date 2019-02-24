package edu.csuft.nchat.user;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import edu.csuft.message.Transferable;

public class User {
	//用户ID
	public String userid;
	//用户的活跃好友列表
	public HashSet<User> activefriendlist = new HashSet<>();
	//用户的所有好友列表
	public HashSet<User> friendlist = new HashSet<>();
	//用户的离线消息列表
	public ArrayList<Transferable> offlinemsgs = new ArrayList<>();	
	private boolean isActive;
	
//	public User(String userid) {
//		this.userid=userid;
//		isActive=;
//	}
	
	public User(String userid, HashSet<User> friendlist) {
		this.userid= userid;
		this.friendlist=friendlist;
	}
	
	public void seekActivefriend(Map<User, Socket> users) {
		for(User u: friendlist) {
			if(users.get(u)!=null)
				activefriendlist.add(u);
		}
	}

	public void addfriend(User user) {
		friendlist.add(user);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("User :"+userid+"\n");
		s.append("user friend list: \n");
		for(User u:friendlist) {
			s.append("nick : "+u.userid+ "  isActive : "+isActive+"\n");
		}
		return s.toString();
	}

	public void sigin() {
		isActive=true;
		
	}

	public void offline() {
		isActive=false;
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return isActive;
	}
}
