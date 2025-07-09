import java.io.*;
import java.net.*;

class TcpServer{
    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket s;
        // String sendString = "Hello Net World! via TCP\n";
        // int slength;
        OutputStream sOut;
        InputStream sIn;
        BufferedReader br;
        PrintWriter pw;
        String str;
        int a,b;

        try {
            //ServerSocket ss Listen on socket 4321
            //back log: 300 (backlog : the maxsize for client request queue)
            ss = new ServerSocket(4321, 300);
            while (true) { 
                //wait for a new client connect to socket 4321
                //if connect, create a new Socket s, s = server and this client communication tunnel
                s = ss.accept();
                //
                sOut = s.getOutputStream();
                sIn = s.getInputStream();

                br = new BufferedReader(new InputStreamReader(sIn));
                pw = new PrintWriter(new OutputStreamWriter(sOut), true);

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
            }
        } catch (IOException e) {
            System.err.println("Caught IOException");
            System.exit(1);
        }
    }
}