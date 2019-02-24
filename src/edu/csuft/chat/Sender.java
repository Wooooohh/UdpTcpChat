package edu.csuft.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Sender {
	public static void main(String[] args) {

		
		DatagramSocket socket = null;
		
		Scanner scanner = new Scanner(System.in);
		try {
			// udp套接字
			socket = new DatagramSocket();

			while(scanner.hasNextLine()) {
				String msg = scanner.nextLine();
				
				byte[] data = msg.getBytes();
				InetAddress address = InetAddress.getByName("");
				int port = 7000;
				// 创建数据包
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

				// 发送
				socket.send(packet);
				System.out.println("send");
				if(msg.equals("再见")) {
					break;
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			socket.close();
		}

	}
}
