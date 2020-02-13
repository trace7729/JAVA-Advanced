import java.util.*;
import java.io.*;
import java.net.*;

/*Lesson6NetworkingServer waits for clients to connect, then starts a thread
 to read the index.html file from the same directory.*/

public class Lesson6NetworkingServer {
  private static ServerSocket serverSocket;

  public static void main(String[] args) throws IOException {
	int server_Port = Integer.parseInt(args[0]) ;
    serverSocket=new ServerSocket(server_Port);  
    while (true) {
      try {
        Socket s=serverSocket.accept();  
        new ClientHandler(s);  
        serverSocket.close();
      }
      catch (Exception x) {
        System.out.println(x);
        serverSocket.close();
      }
    }
  }
}

class ClientHandler extends Thread {
  private Socket socket;  

  public ClientHandler(Socket s) {
    socket=s;
    start();
  }
  public void run() {
    try {
      BufferedReader in=new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
      PrintStream out=new PrintStream(new BufferedOutputStream(
        socket.getOutputStream()));
      //read this html file from the same directory
      String filename="index.html";
      try {
        InputStream f=new FileInputStream(filename);

        // Determine the MIME type and print header
        String mimeType="text/plain";
        if (filename.endsWith(".html") || filename.endsWith(".htm"))
          mimeType="text/html";

        // Send file content to client and close the connection
        byte[] a=new byte[4096];
        int n;
        while ((n=f.read(a))>0)
          out.write(a, 0, n);
        out.close();
        socket.close();
      }
      catch (FileNotFoundException x) {
        out.println("HTTP/1.0 404 Not Found\r\n"+
          "Content-type: text/html\r\n\r\n"+
          filename+" not found</body></html>\n");
        out.close();
        socket.close();
      }
    }
    catch (IOException x) {
      System.out.println(x);
    }
  }
}
