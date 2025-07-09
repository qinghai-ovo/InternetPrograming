import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

class TcpClient {
	public static void main(String args[]) {
		char c;
		Socket s;
		InputStream sIn;
        OutputStream sOut;
        BufferedReader br;
        BufferedReader stdIn;
        PrintWriter pw;
        String str;
        StringTokenizer stn;


		if (args.length != 1) {
			System.out.println("No hostname given");
			System.exit(1);
		}

		try {
			s = new Socket(args[0], 4321);

			sIn = s.getInputStream();
            sOut = s.getOutputStream();	
            br = new BufferedReader(new InputStreamReader(sIn));
            pw = new PrintWriter(new OutputStreamWriter(sOut),true);

            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server. Enter text to send.");
            System.out.println("Type 'exit' to quit.");

            String userInput;
            while((userInput = stdIn.readLine()) != null){
                pw.println(userInput);

                if("exit".equalsIgnoreCase(userInput)){
                    break;
                }

                String serverResponse = br.readLine();
                if(serverResponse != null){
                       System.out.println("Server: " + serverResponse);
                }else{
                    System.out.println("Server closed the connection.");
                    break;
                }
            }
            

			// pw.println("hello");
            // str = br.readLine();
            // System.out.println(str);
            // stn = new StringTokenizer(str," ",false);
            // if(!stn.nextToken().equals("ack")){
            // System.exit(1);
            // }
            // pw.println("4");
            // str = br.readLine();
            // System.out.println(str);
            // stn = new StringTokenizer(str," ",false);
            // if(!stn.nextToken().equals("ack")){
            // System.exit(1);
            // }

            // pw.println("5");
            // str = br.readLine();
            // System.out.println(str);
            // sIn.close();
            // sOut.close();
            // s.close();
			// sIn.close();
			// s.close();
		} catch (IOException e) {
			System.err.println("Caught IOException");
			System.exit(1);
		}
	}
}