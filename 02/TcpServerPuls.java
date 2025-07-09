import	java.net.*;
import	java.io.*;

class TcpServer {
    public static void main(String args[]) {
	ServerSocket	ss = null;
	Socket 	s;
	OutputStream sOut;
	InputStream sIn;
	BufferedReader br;
	PrintWriter pw;
	String str;
	int a,b;
	try {
	    ss = new ServerSocket(4321, 300);

	    while (true) {
                try{
		  s = ss.accept();
		  sOut = s.getOutputStream();
                  sIn = s.getInputStream();

		  br = new BufferedReader(new InputStreamReader(sIn));
		  pw = new PrintWriter(new OutputStreamWriter(sOut),true);
		
		  str = br.readLine();
		  System.out.println(str);
		  if(!str.equals("hello")){
		      pw.println("nack :no hello message");
		      sIn.close();
		      sOut.close();
		      s.close();
		      continue;
		  }
		
		  pw.println("ack :Please input 1st integer.");
		  str = br.readLine();
		  System.out.println(str);
		  try{
		      a = Integer.parseInt(str);
		  }catch(NumberFormatException e){
		      pw.println("nack :"+str+" is not integer.");
		      sIn.close();
		      sOut.close();
		      s.close();
		      continue;
		  }
		  pw.println("ack :Ok 2nd please.");
		  str = br.readLine();
		  System.out.println(str);
		  try{
		      b = Integer.parseInt(str);
		  }catch(NumberFormatException e){
		      pw.println("nack :"+str+" is not integer.");
		      sIn.close();
		      sOut.close();
		      s.close();
		      continue;
		  }
	    
		  pw.println(""+(a+b));
		  sIn.close();

		  sOut.close();
		  s.close();
               }catch(SocketException e){
		    System.err.println("Connection reset");
		    continue;
	       }
	    }
	}catch(IOException e){
	    e.printStackTrace();
	    System.exit(1);
	}
    }
}