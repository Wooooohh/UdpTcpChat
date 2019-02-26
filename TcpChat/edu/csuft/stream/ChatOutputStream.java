package edu.csuft.stream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

public class ChatOutputStream {

	public final byte[] buff;
	public static final int MaxBuff = 1024*8;
	Gson gson;
	OutputStream out;
	
	byte[] b;
	
	public ChatOutputStream(OutputStream out,Gson gson) {
		this.out=out;
		buff = new byte[MaxBuff];
		this.gson=gson;
	}
	public void write(Transferable msg) throws IOException {
		buff[0]=msg.getIdcode();
		switch (buff[0]) {
		case 1:
			b=gson.toJson(new SigninRequest(msg.getMssage())).getBytes();
			break;
		case 2:
			b=gson.toJson(new UpdataFriendList(msg.getMssage())).getBytes();
			break;
		case 3:
			b=gson.toJson(new SigninRespose(msg.getMssage())).getBytes();
			break;
		case 4:
			b=gson.toJson(new ChatContentrequest(msg.getMssage())).getBytes();
			break;
		case 5:
			b=gson.toJson(new Friendrequest(msg.getMssage())).getBytes();
			break;
		case 6:
			b=gson.toJson(new Friendrespose(msg.getMssage())).getBytes();
			break;
		case 7:
			b=gson.toJson(new upFilerequest(msg.getMssage())).getBytes();
			break;
		default:
			break;
		}
		for(int i=0;i<b.length;i++) {
			buff[i+1]=b[i];
		}
		out.write(buff, 0,b.length+1);
		if(buff[0]==7)
			transferfile(msg.getMssage());
	}
	
	public void transferfile(String mssage) {
		String[] fileinfo = mssage.split("\\.");
		File file = new File(fileinfo[2]+"."+fileinfo[3]);
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))){
			byte[] buf= new byte[MaxBuff];
			//SendPosAndMD5(out, file, file);
			
			//传输文件	
			int size;
			while(-1!=(size=in.read(buf))) {
				out.write(buf,0,size);
				out.flush();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
