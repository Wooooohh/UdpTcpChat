package edu.csuft.client;

import java.io.File;
import java.io.IOException;

import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;

import edu.csuft.message.ChatContentrequest;
import edu.csuft.message.Friendrequest;
import edu.csuft.message.SigninRequest;
import edu.csuft.message.Transferable;
import edu.csuft.message.upFilerequest;
import edu.csuft.stream.ChatInputStream;
import edu.csuft.stream.ChatOutputStream;

public class ChatClient {
	ChatInputStream in;

	ChatOutputStream out;
	Gson gson;

	Socket tcpsocket;

	Scanner scanner;

	String userid;

	public ChatClient() {
		try {
			scanner = new Scanner(System.in);
			gson = new Gson();

			// 获取tcp管道的io流
			tcpsocket = new Socket("127.0.0.1", 9000);
			in = new ChatInputStream(tcpsocket.getInputStream(), gson);
			out = new ChatOutputStream(tcpsocket.getOutputStream(), gson);

			Thread  t = new Thread(new Receiver(in));
			t.start();
			System.out.println("请输入操作 1、登入");
			String key = scanner.nextLine();
			Transferable msg;
			do {
				switch (key) {
				case "1":
					System.out.println("请输入用户id");
					userid = scanner.nextLine();
					msg = new SigninRequest(userid);
					out.write(msg);
					break;
				case "2":
					System.out.println("请输入好友昵称：");
					String receive = scanner.nextLine();
					System.out.println("请输入内容：退出当前聊天请输入esc");
					String content;
					while (scanner.hasNext()) {
						content = scanner.nextLine();
						if (content.equals("esc")) {
							break;
						}
						msg = new ChatContentrequest(userid, receive, content);
						out.write(msg);
						System.out.println("输入：");
					}
					break;
				case "3":
					System.out.println("请输入文件所在全路径名");
					String filepath =scanner.nextLine();
					File f = new File(filepath);
					
					if(f.exists())
					{
						msg=new upFilerequest(f);
						System.out.println(f.getPath());
						out.write(msg);
					}
					else {
						System.out.println("文件不存在");
					}
					
					break;
				case "4":
					System.out.println("请输入文件所在全路径名");
					String filepath1 =scanner.nextLine();
					System.out.println("请输入好友昵称");
					String friendname=scanner.nextLine();
					
					
					
					
					break;
				case "5":
					System.out.println("请输入好友昵称");
					String fname=scanner.nextLine();
					out.write(new Friendrequest(userid, fname));
					
					break;	
					
				default:
					break;
				}
				System.out.println("请继续操作 2、聊天  3、上传文件到云 4、上传文件给好友 5、请求添加好友（退出请输入退出）");
				key = scanner.nextLine();
			} while (!key .equals("退出"));
			System.out.println("over");
			in.close();
			out.close();
			t.interrupt();
			tcpsocket.close();
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
	}

	public static void main(String[] args) {
		ChatClient chat = new ChatClient();
	}
}
