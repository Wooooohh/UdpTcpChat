package edu.csuft.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceiverTask implements Runnable {

	DatagramSocket socket;
	
	public ReceiverTask(DatagramSocket socket) {
		this.socket=socket;
	}

	@Override
	public void run() {
		String msg= null;
		DatagramPacket packet=null;
		int senderport = 0;
		byte[] data = new byte[1024*8];
		do {
			try {
				
				packet = new DatagramPacket(data, 
						data.length
						);
				socket.receive(packet);
				senderport = packet.getPort();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data=packet.getData();
			msg = new String(data,0,packet.getLength());
			System.out.println("接收到"+senderport);
			System.out.println(msg);
		}while(!msg.equalsIgnoreCase("bye"));
		System.out.println("接收结束");
	}

}
