package edu.csuft.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SenderTask implements Runnable {

	DatagramSocket socket;
	int aimport;
	Scanner sc;

	public SenderTask(DatagramSocket socket, Scanner sc) {
		this.socket = socket;

		this.sc = sc;
	}

	@Override
	public void run() {
		String msg = null;
		System.out.println("请输入目标端口号：");
		aimport = sc.nextInt();
		do {
			System.out.println("发送：");
			while (sc.hasNextLine()) {
				msg = sc.nextLine();
				break;
			}
			byte[] data = msg.getBytes();
			DatagramPacket packet;
			try {
				packet = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), aimport);
				socket.send(packet);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (!msg.equalsIgnoreCase("bye"));

	}
}
