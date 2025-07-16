import java.io.*;
import java.net.*;
import java.util.*;


class OthelloAI{
    public static void main(String[] args) {
        Socket s;
        InputStream sIn;
        OutputStream sOut;
        BufferedReader br;
        BufferedReader stdIn;
        PrintWriter pw;
        String str;
        StringTokenizer stn;

        try {
            s = new Socket(args[0], 25033);

            sIn = s.getInputStream();
            sOut = s.getOutputStream();
            br = new BufferedReader(new InputStreamReader(sIn));
            pw = new PrintWriter(new OutputStreamWriter(sOut),true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server. Enter text to send.");
            System.out.println("Type 'exit' to quit.");
            pw.println("NICK 6323041");
                        
            String serverResponse = br.readLine();

            stn = new StringTokenizer(serverResponse, " ",false);
            String msg = stn.nextToken();
            String color = stn.nextToken();
            System.out.println("Message = " + msg);
            System.out.println("Color = " + color);

            while(true){
                //System.out.println("New loop");
                serverResponse = br.readLine();

                if((serverResponse) == null){
                    System.out.println("over");
                    break;
                }

                System.out.println("Server: " + serverResponse);

                if(serverResponse.equals("TURN " + color) || serverResponse.equals("ERROR 2")){
                    //getB
                    //put
                    int x = (int)(Math.random()*8);
                    int y = (int)(Math.random()*8);
                    pw.println("PUT " + x + " " + y);
                    //System.out.println("PW OVER");
                    pw.flush();
                }else if(serverResponse.equals("ERROR 3")) {
                    System.out.println("NOT My Turn");
                }else if(serverResponse.equals("ERROR 4")){
                    System.out.println("Command Error");
                }else if(serverResponse.equals("ERROR 1")){
                    System.out.println("Syntax Error");
                }

            }
            //     // PUT col row
            //     //BOARD 
            //     // 0 0 0 0 0 0 0 0
            //     // 0 0 0 0 0 0 0 0
            //     // 0 0 0 0 0 0 0 0
            //     // 0 0 0 1 -1 0 0 0 
            //     // 0 0 0 -1 1 0 0 0
            //     // 0 0 0 0 0 0 0 0 
            //     // 0 0 0 0 0 0 0 0
            //     // 0 0 0 0 0 0 0 0
        } catch (IOException e) {
            System.err.println("Caught IOException");
			System.exit(1);
        }
    }
}