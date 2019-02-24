package edu.csuft.nchat.user;

import java.util.HashSet;

public class FriendList{
	
//	int ActiveFriend;
//	
//	int AllFriend;
//	
//	HashSet<User> activefriendlist = new HashSet<>();
//	
//	HashSet<User> friendlist = new HashSet<>();
//	
//	public FriendList(HashSet<User> friendlist)
//	{
//		this.friendlist=friendlist;
//		AllFriend=friendlist.size();
//		seekActiveFriend();
//	}
//	public void seekActiveFriend() {
//		for(User u:friendlist) {
//			if(u.isActive) {
//				activefriendlist.add(u);
//				ActiveFriend++;
//			}
//		}
//	}
//	public void addFriend(User user) {	
//		friendlist.add(user);
//		seekActiveFriend();
//	}
//	public void deleteFriend(String UserId) {
//		friendlist.remove(seekFriend(UserId));
//	}
//	public User seekFriend(String UserId) {
//		for(User u :friendlist) {
//			if(u.userid.equals(UserId))
//				return u;
//		}
//		return null;
//	}
//	public void updataActivelist(User user, boolean isActive) {
//			if(isActive) {
//				for(User u:activefriendlist) {
//					u.friendlist.addActiveUser(user);
//				}
//			}else {
//				for(User u:activefriendlist) {
//					u.friendlist.deleteActiveUser(user);
//				}
//			}
//	}
//	public void deleteActiveUser(User user) {
//		activefriendlist.remove(user);
//		
//	}
//	public void addActiveUser(User user) {
//		activefriendlist.add(user);
//	}
}
