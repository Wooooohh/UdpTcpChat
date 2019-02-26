package edu.csuft.nchat.service;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import edu.csuft.message.*;
import edu.csuft.nchat.user.User;
import edu.csuft.stream.*;

public class OnlineService implements Runnable {

	Socket socket;
	User user;
	Map<User, Socket> allactiveusers;
	HashMap<String,User> allusers;
	Gson gson;
	int size;
	ChatInputStream in;
	ChatOutputStream out;
	
	public OnlineService(Socket socket, Map<User, Socket> users,HashMap<String,User> allusers, Gson gson) {
		this.socket = socket;
		this.allactiveusers = users;
		this.allusers = allusers;
		this.gson = gson;
	}

	@Override
	public void run() {
		try {

			in = new ChatInputStream(socket.getInputStream(), gson);
			out = new ChatOutputStream(socket.getOutputStream(), gson);
		} catch (IOException e) {
			e.printStackTrace();
		}
			while (true) {
				try {
					Transferable msg = in.peek();
					handle(msg);
				} catch (IOException | NullPointerException e) {
					
					
					//处理该用户下线
					in.close();
					user.offline();
					user.offlinemsgs= new ArrayList<>();
					allactiveusers.remove(user);
					updataUser();
					break;
				}
			}
	}

	public void handle(Transferable msg) throws IOException{
		
			byte b = msg.getIdcode();
			switch (b) {
			case 1:
				// 忽略检验及密码
				//登陆的一系列操作
				String userid=msg.getMssage();
				user =allusers.get(userid);
				user.seekActivefriend(allactiveusers);
				user.sigin();
				allactiveusers.put(user, socket);
				updataUser();
				out.write(new SigninRespose(user));
				System.out.println("a sigin respose send over");
				sendofflineMessage();
				break;
			case 4:
				//收到消息后的处理
				String info =msg.getMssage();
				
				String[] infos = info.split("\\.");
				User receive = allusers.get(infos[0]);
				if(receive.isActive()) {
					Socket nowsocket = allactiveusers.get(receive);
					new ChatOutputStream(nowsocket.getOutputStream(), gson).write(new ChatContentrequest(infos[1]));;	
				}
				else {
					receive.offlinemsgs.add(msg);
				}
				break;
			case 5:
				String info2 =msg.getMssage();
				String[] infos2 = info2.split("\\.");
				User receiver2 = allusers.get(infos2[0]);
				if(receiver2.isActive()) {
					Socket nowsocket = allactiveusers.get(receiver2);
					new ChatOutputStream(nowsocket.getOutputStream(), gson).write(new Friendrequest(infos2[1]));
				}
				else {
					//返回给消息发送者目标用户不在线或添加到离线消息队列
					receiver2.offlinemsgs.add(msg);
				}
				break;
			case 6:
				String info3 = msg.getMssage();
				String[] infos3 =info3.split(",");
				User receiver3 = allusers.get(infos3[0]);
				if(receiver3.isActive()) {
					Socket nowsocket = allactiveusers.get(receiver3);
					new ChatOutputStream(nowsocket.getOutputStream(), gson).write(new Friendrespose(user.userid,infos3[1]));	
				}
				else {
					//返回给消息发送者目标用户不在线或添加到离线消息队列
					receiver3.offlinemsgs.add(msg);
				}
				
				break;
			case 7:
				String info4 = msg.getMssage();
				//String[] infos4 =info4.split("\\.");
				System.out.println(info4+"上传文件成功");
				break;
			
			}
			
	}

	public void updataUser() {
		System.out.println(user.userid+"状态发生变化");
		ChatOutputStream updataout;
		for(User u:user.activefriendlist) {
			 try {
				updataout = new ChatOutputStream(allactiveusers.get(u).getOutputStream(),gson);
				updataout.write(new UpdataFriendList(u.friendlist));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void sendofflineMessage() throws IOException {
		if(!user.offlinemsgs.isEmpty()) {
			for(Transferable info: user.offlinemsgs) {
				handle(info);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
