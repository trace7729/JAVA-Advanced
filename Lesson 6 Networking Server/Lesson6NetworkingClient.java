import java.io.*;
import java.net.*;
import java.util.*;
public class Lesson6NetworkingClient implements Runnable {
	/*Lesson6NetworkingClient connects to the server , and then
	  displays the information returned by the server to the console*/
public static void main(String[] args) throws IOException{
	String server_IP =args[0];
	int server_Port = Integer.parseInt(args[1]) ;
	String client_IP ;
    int init = 0  ;

    try {
        InetAddress iAddress = InetAddress.getLocalHost();
        client_IP = iAddress.getHostAddress();
    } catch (UnknownHostException e) {
    }

    try {
        Socket socket = new Socket(server_IP,server_Port);
        init = initialize(socket);

    }catch (SocketException e) {
        System.out.println("Error: Unable to connect to server port ");
    }


    if (init ==  0 ){
        System.out.println("error: Failed to initialize ");
        System.exit(0);

    }
}
private static int initialize(Socket socket ) throws IOException{
    int rt_value = 0 ;

    OutputStream os = socket.getOutputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter pw = new PrintWriter(os, true);
    String line;
    while((line=br.readLine())!= null){
        System.out.println(line);
    }

    socket.close();
    return rt_value = 1 ;

}
	public void run(){

	}
}