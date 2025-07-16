import java.io.*;
import java.net.*;

public class HTTPd {
    HTTPd(String file){
	try{
	    ServerSocket ss = new ServerSocket(8000);
	    while(true){
		Socket socket = ss.accept();
		System.out.println("Connect From : "+(socket.getInetAddress()).getHostName());
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		BufferedReader br2 = new BufferedReader(new FileReader(file));
		while(true){
		    String str = br.readLine();
		    System.out.println(str);
		    if(str.equals(""))break;
		}
        //protocal
		pw.println("HTTP/1.1 200 OK\n");
        
		String tmp;
		while((tmp=br2.readLine())!=null){
		    pw.println(tmp);
		    pw.flush();
		}
		socket.close();
		fis.close();
	    }
	    
	}catch(IOException e){
	    e.printStackTrace();
	    System.exit(1);
	}
    }
    public static void main(String args[]){
	if(args.length!=1)System.exit(1);
	new HTTPd(args[0]);
    }
}