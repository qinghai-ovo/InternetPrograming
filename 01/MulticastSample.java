
/*     MulticastSample.java
 * 
 *     t-matsu  5/27 2011
 */
 
import java.io.*;
import java.net.*;

public class MulticastSample extends Thread{
    static final String M_ADDRESS ="224.0.0.100";
    static final int M_PORT = 25000;
    static final int BUFSIZE = 1024;
    InetAddress maddr;
    MulticastSocket socket;

    MulticastSample(){
	try{
	    maddr = InetAddress.getByName(M_ADDRESS);
	    socket = new MulticastSocket(M_PORT);
	    socket.joinGroup(maddr);
            socket.setTimeToLive(1);  
            System.out.println("==== Join the IP Multicast Group:"
                                   +M_ADDRESS+" ====");
	    this.start();
	    BufferedReader reader = new BufferedReader
		(new InputStreamReader(System.in));
	    while(true){
		String str = reader.readLine();
		if(str.equals("")){
		    break;
		}
		DatagramPacket s_packet = new DatagramPacket
		    (str.getBytes(),str.getBytes().length,maddr,M_PORT);
		socket.send(s_packet);
	    }
	    socket.leaveGroup(maddr);
            socket.close();
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

    public void run(){
	try{
	    while(true){
		byte[] buf  = new byte[BUFSIZE];
		DatagramPacket r_packet = new DatagramPacket(buf, BUFSIZE);
		socket.receive(r_packet);
		System.out.print("From"+r_packet.getAddress()+": ");
		System.out.write(buf,0,r_packet.getLength());
		System.out.println();
	    }
	    
	}catch(UnknownHostException e){
	    e.printStackTrace();
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

    public static void main(String args[]){
	new MulticastSample();
    }
}