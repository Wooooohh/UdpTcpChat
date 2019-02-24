package edu.csuft.nchat.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import edu.csuft.nchat.user.User;

public class ChatServer {
	//tcp服务端套接字
	ServerSocket serverSocket;
	
	//并发编程(线程池)
	ExecutorService  pool;
	
	//记录在线用户信息端的信息
	Map<User,Socket> allactiveusers = new HashMap<>();
	
	Gson gson;
	
	HashMap<String,User> allusers = new HashMap<>();
	
	
	public ChatServer() {
		pool = Executors.newCachedThreadPool();
		gson = new Gson();
	}
	public void start(){
		try {
			serverSocket = new ServerSocket(9000);
			
			while(true) {
				//建立连接
				Socket socket = serverSocket.accept();
				
				//让线程池中的一个线程处理用户上线
				OnlineService onlineService = new OnlineService(socket,allactiveusers,allusers,gson);
				
				pool.execute(onlineService);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ChatServer c = new ChatServer();

		HashSet<User> bobf =new HashSet<>();
		HashSet<User> alicef =new HashSet<>();
		HashSet<User> vcatf =new HashSet<>();
		HashSet<User> aif =new HashSet<>();
		HashSet<User> audif =new HashSet<>();
		
		
		User bob = new User("bob",bobf);
		User alice = new User("alice",alicef);
		User ai = new User("ai",aif);
		User audi =new User("audi",audif);
		
		
		alice.addfriend(bob);
		bob.addfriend(alice);
		
		ai.addfriend(alice);
		alice.addfriend(ai);
		
		ai.addfriend(bob);
		bob.addfriend(ai);
		
		audi.addfriend(bob);
		bob.addfriend(audi);
		
		audi.addfriend(ai);
		ai.addfriend(audi);
		
		c.allusers.put("bob",bob);
		c.allusers.put("alice",alice);
		c.allusers.put("ai",ai);
		c.allusers.put("audi",audi);
		c.allusers.put("vcat",new User("vcat",vcatf));
		System.out.println(c.allusers.toString());
		c.start();
	}
}
