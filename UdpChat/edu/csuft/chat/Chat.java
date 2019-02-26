package edu.csuft.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;

public class Chat {
	Thread sender;
	Thread receiver;
	
	DatagramSocket udpsocket;
	
	Socket tcpsocket;
	
	public Chat() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入昵称");
		String nick= sc.nextLine();
		int localport;
		try {
			
			//c/s
			tcpsocket = new Socket("127.0.0.1",9000);
			InputStream in =tcpsocket.getInputStream();
			OutputStream out = tcpsocket.getOutputStream();
			
			out.write(nick.getBytes());
			out.flush();
			udpsocket = new DatagramSocket();
			localport=udpsocket.getLocalPort();
			byte[] localport1 = String.valueOf(localport).getBytes();
			out.write(localport1);
			out.flush();
			
			//接收在线用户的列表
			Gson gson = new Gson();
			byte[] buf = new byte[1024];
			int size =in.read(buf);
			String json = new String(buf,0,size);
			HashMap<String,Integer> users = gson.fromJson(json, (Type)HashMap.class);
			System.out.println("在线列表："+users);
			
			//UDP 端到端的通信
			sender = new Thread(new SenderTask(udpsocket,sc));
			receiver = new Thread(new ReceiverTask(udpsocket));
			
			sender.start();
			receiver.start();
			
			while(-1!=(size =in.read(buf))) {
				
				json = new String(buf,0,size);
				users = gson.fromJson(json, (Type)HashMap.class);
				System.out.println("在线列表："+users);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				tcpsocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Chat chat = new Chat();
	}
}
