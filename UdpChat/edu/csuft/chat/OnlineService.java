package edu.csuft.chat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import com.google.gson.Gson;

public class OnlineService implements Runnable {

	Socket socket;

	Map<String, Integer> users;

	Socket s;

	int size;

	public OnlineService(Socket socket, Map<String, Integer> users) {
		this.socket = socket;
		this.users = users;
	}

	@Override
	public void run() {
		try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
			// 获取昵称
			byte[] buf1 = new byte[64];
			int size1 = in.read(buf1);
			String nick = new String(buf1, 0, size1);
			// 读取端口号
			byte[] buf2 = new byte[64];
			int size2 = in.read(buf2);
			int port = Integer.valueOf(new String(buf2, 0, size2));
			// 存储目标的端口号
			users.put(nick, port);
			size = users.size();

			// 发送其他用户信息
			// 对象的输出
			// users-->XML/JSON
			Gson gson = new Gson();
			String json = gson.toJson(users);
			System.out.println("发送");
			out.write(json.getBytes());
			out.flush();
			while (true) {
				if(socket.isClosed()) {
					users.remove(nick);
				}
				int newsize = users.size();
				if (size != newsize) {
					json = gson.toJson(users);
					System.out.println("更新列表");
					out.write(json.getBytes());
					out.flush();
					size = newsize;
				}
				Thread.sleep(10000);
			}
		} catch (Exception e) {
		}
	}
}
