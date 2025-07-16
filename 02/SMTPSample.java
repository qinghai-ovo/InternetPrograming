import java.io.*;
import java.net.*;

public class SMTPSample{
    String to_addr = "qinghai1027@gmail.com"; //送信先アドレス
    String from_addr = "is_test@is.noda.tus.ac.jp";  //送信元アドレス
    String mail_server = "mail.is.noda.tus.ac.jp"; //メールサーバ
    String message = "Hello, How are you?";
    Socket socket;
    int port = 25;  // SMTPは25番ポート
    PrintWriter pw;
    BufferedReader br;

    SMTPSample(){
	try{
	    socket = new Socket(mail_server,port);
	    pw = new PrintWriter(socket.getOutputStream());
	    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    System.out.println(br.readLine());
	    sendMessage("HELO "+(InetAddress.getLocalHost().getHostName()));
	    System.out.println(br.readLine());
	    sendMessage("MAIL FROM: "+from_addr);
	    System.out.println(br.readLine());
	    sendMessage("RCPT TO: "+to_addr);
	    System.out.println(br.readLine());
	    sendMessage("DATA");
	    System.out.println(br.readLine());
	    sendMessage("To: "+to_addr);
	    sendMessage("From: "+from_addr);
	    sendMessage("Subject: Test");
	    sendMessage("");
	    sendMessage(message);
	    sendMessage(".");
	    System.out.println(br.readLine());
	    sendMessage("QUIT");
	    System.out.println(br.readLine());
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

    void sendMessage(String str){
	pw.print(str+"\r\n");
	pw.flush();
    }
    
    public static void main(String args[]){
	new SMTPSample();
    }
}