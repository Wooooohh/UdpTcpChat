package edu.csuft.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver {
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			//创建udp套接字
			socket = new DatagramSocket(7000);
			//数据包
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
				
			//打印内容
			byte[] data =null;
			String msg= null;
			do{
				socket.receive(packet);
				data=packet.getData();
				msg = new String(data,0,packet.getLength());
				System.out.println(msg);
			}while(!msg.equals("再见"));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			socket.close();
		}
	}
}
