package edu.csuft.stream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.Gson;

import edu.csuft.message.ChatContentrequest;
import edu.csuft.message.Friendrequest;
import edu.csuft.message.Friendrespose;
import edu.csuft.message.SigninRequest;
import edu.csuft.message.SigninRespose;
import edu.csuft.message.Transferable;
import edu.csuft.message.UpdataFriendList;
import edu.csuft.message.upFilerequest;

public class ChatInputStream {

	public final byte[] buff;
	public final Gson gson;
	public static final int MaxBuff = 1024 * 8;
	protected InputStream in;
	int size;

	public ChatInputStream(InputStream in, Gson gson) {
		this.in = in;
		this.gson = gson;
		buff = new byte[MaxBuff];
	}

	public Transferable peek() throws IOException {
		byte b = 0;
		Transferable msg = null;
		if (-1 != (size = in.read(buff))) {
			b = buff[0];
			System.out.println(b);
			switch (b) {
			case 1:
				msg = gson.fromJson(new String(buff, 1, size - 1), SigninRequest.class);
				break;
			case 2:
				msg = gson.fromJson(new String(buff, 1, size - 1), UpdataFriendList.class);
				break;
			case 3:
				msg = gson.fromJson(new String(buff, 1, size - 1), SigninRespose.class);
				break;
			case 4:
				msg = gson.fromJson(new String(buff, 1, size - 1), ChatContentrequest.class);
				break;
			case 5:
				msg = gson.fromJson(new String(buff, 1, size - 1), Friendrequest.class);
				break;
			case 6:
				msg = gson.fromJson(new String(buff, 1, size - 1), Friendrespose.class);
				break;
			case 7:
				msg = gson.fromJson(new String(buff, 1, size - 1), upFilerequest.class);
				receiveFile(msg.getMssage());
				break;
			}
		}
		return msg;
	}

	public void receiveFile(String mssage) {
		String[] fileinfo = mssage.split("\\.");
		File file = new File(fileinfo[0]);

		try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			// 写入文件
			int size;
			while (-1 != (size = in.read(buff))) {
				out.write(buff, 0, size);
				out.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
