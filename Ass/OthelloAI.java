import java.io.*;
import java.net.*;
import java.util.*;

class OthelloAI{
    
    public static int[][] getboard(String boardStr){
        int[][] rboard = new int[8][8];
        StringTokenizer dataStr = new StringTokenizer(boardStr, " ",false);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                rboard[i][j] = Integer.parseInt(dataStr.nextToken());
                
                //debug
                //System.out.println(".()"+ i +" "+ j + " data " + rboard[i][j]);
            }
        }
        return  rboard;
    }

    public static int[] putPos(int[][] board, int my){
        int[] xy = new int[2];

        int score = 0;
        
        //check row
        for(int i = 0; i < 8; i++){
            //Find the first pos of my
            for(int j_1 = 0; j_1 < 8; j_1++){

                //if[i][j_1] is the first on this row
                if(board[i][j_1] == my && j_1 != 7){
                    int tempScore = 0;

                    //Find the Second on this row from (i, j_1 + 1)
                    for(int j_2 = j_1 + 1; j_2 < 8 ;j_2 ++){
                        if(board[i][j_2] == ((-1) * my)){
                            //record how much between until 0
                            tempScore++;
                        }else if(board[i][j_2] == my){  //if meet the second of my
                            //start from j_2
                            j_1 = j_2;
                            break;
                        }else if(board[i][j_2] == 0){ // if meet 0 at i j_2
                            //compare and update new score & pos
                            if (tempScore > score){
                                score = tempScore;
                                xy[0] = i;
                                xy[1] = j_2;
                            }
                        }
                        //if not meet Ok pos then reset tempScore, and find next line
                        tempScore = 0;
                    }
                }
            }
        }

        //check col (inverse the i and j)
        for(int i = 0; i < 8; i++){
            //Find the first pos of my
            for(int j_1 = 0; j_1 < 8; j_1++){

                //if[i][j_1] is the first on this row
                if(board[j_1][i] == my && j_1 != 7){
                    int tempScore = 0;

                    //Find the Second on this row from (i, j_1 + 1)
                    for(int j_2 = j_1 + 1; j_2 < 8 ;j_2 ++){
                        if(board[j_2][i] == ((-1) * my)){
                            //record how much between until 0
                            tempScore++;
                        }else if(board[j_2][i] == my){  //if meet the second of my
                            //start from j_2
                            j_1 = j_2;
                            break;
                        }else if(board[j_2][i] == 0){ // if meet 0 at i j_2
                            //compare and update new score & pos
                            if (tempScore > score){
                                score = tempScore;
                                xy[0] = j_2;
                                xy[1] = i;
                            }
                        }
                        //if not meet Ok pos then reset tempScore, and find next line
                        tempScore = 0;
                    }
                }
            }
        }
        System.out.println("x" + xy[0] + " y " + xy[1]);
        //random ai
        // //x
        // xy[0] = (int)(Math.random()*8);
        // //y
        // xy[1] = (int)(Math.random()*8);
        return xy;
    }

    public static void main(String[] args) {
        Socket s;
        InputStream sIn;
        OutputStream sOut;
        BufferedReader br;
        //BufferedReader stdIn;
        PrintWriter pw;
        //String str;
        StringTokenizer stn;
        int[][] board = new int[8][8];

        try {
            s = new Socket(args[0], 25033);

            sIn = s.getInputStream();
            sOut = s.getOutputStream();
            br = new BufferedReader(new InputStreamReader(sIn));
            pw = new PrintWriter(new OutputStreamWriter(sOut),true);
            //stdIn = new BufferedReader(new InputStreamReader(System.in));

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

                //System.out.println("Server: " + serverResponse);

                if(serverResponse.equals("TURN " + color) || serverResponse.equals("ERROR 2")){
                    int mycolor = Integer.parseInt(color);
                    int[] pos = putPos(board, mycolor);
                    pw.println("PUT " + pos[0] + " " + pos[1]);
                    //System.out.println("PW OVER");
                    pw.flush();
                }else if(serverResponse.equals("ERROR 3")) {
                    System.out.println("NOT My Turn");
                }else if(serverResponse.equals("ERROR 4")){
                    System.out.println("Command Error");
                }else if(serverResponse.equals("ERROR 1")){
                    System.out.println("Syntax Error");
                }else if(serverResponse.startsWith("BOARD ")){
                    System.out.println("Reading board");
                    String boardStr = serverResponse.substring("BOARD ".length());
                    board = getboard(boardStr);
                    //debug
                    //System.out.println(Arrays.deepToString(board));
                }
            }
        } catch (IOException e) {
            System.err.println("Caught IOException");
			System.exit(1);
        }
    }
}