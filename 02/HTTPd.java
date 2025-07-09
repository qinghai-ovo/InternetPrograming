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
		OutputStream os = socket.getOutputStream();
		
		while(true){
		    String str = br.readLine();
		    System.out.println(str);
		    if(str.equals(""))break;
		}
		
		byte[] buf = new byte[1024];
		while(true){
		    int n = fis.read(buf);
		    System.out.println("read "+n+" bytes");
		    if(n==-1)break;
		    os.write(buf,0,n);
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
