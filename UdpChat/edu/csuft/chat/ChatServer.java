package edu.csuft.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
	//tcp服务端套接字
	ServerSocket serverSocket;
	
	//并发编程(线程池)
	ExecutorService  pool;
	
	//记录客户端的信息
	Map<String,Integer> users = new HashMap<>();
	
	public ChatServer() {
		pool = Executors.newCachedThreadPool();
	}
	public void start(){
		try {
			serverSocket = new ServerSocket(9000);
			
			while(true) {
				//建立连接
				Socket socket = serverSocket.accept();
				
				//让线程池中的一个线程处理用户上线
				OnlineService onlineService = new OnlineService(socket,users);
				pool.execute(onlineService);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ChatServer c = new ChatServer();
		c.start();
	}
}
