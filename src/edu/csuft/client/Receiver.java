package edu.csuft.client;

import java.io.IOException;
import java.util.Scanner;

import edu.csuft.message.Friendrespose;
import edu.csuft.message.Transferable;
import edu.csuft.stream.ChatInputStream;
import edu.csuft.stream.ChatOutputStream;

public class Receiver implements Runnable {

	ChatInputStream in;

	public Receiver(ChatInputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Transferable msg = in.peek();
				System.out.println(msg.getMssage());
			}
		} catch (IOException e) {

		}
	}

}
