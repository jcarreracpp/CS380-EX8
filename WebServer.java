
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Jorge
 */
public class WebServer {
        public static void main(String[] args) throws IOException, ClassNotFoundException{
            try(ServerSocket serversocket = new ServerSocket(8080)){
                Socket socket = serversocket.accept();
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                PrintStream pout = new PrintStream(os);
                BufferedReader bread = new BufferedReader(isr);
                
                String filepath = null;
                String buffer;
                
                String message = bread.readLine();
                
                if(message.contains("GET"))
                    filepath = message.split(" ", 3)[1];
                
                File html = new File("www"+filepath);
                
                if(html.exists()){
                    BufferedReader filereader = new BufferedReader(new FileReader(html));
                    filepath = "";
                    while((buffer = filereader.readLine()) != null){
                        filepath += buffer;
                    }
                    buffer = "HTTP/1.1 200 OK\r\nContent-type: text/html\r\nContent-length: "+filepath.length()+"\r\n\r\n";

                    pout.println(buffer + filepath);
                }else{
                    BufferedReader filereader = new BufferedReader(new FileReader("www/notfound.html"));
                    filepath = "";
                    while((buffer = filereader.readLine()) != null){
                        filepath += buffer;
                    }
                    buffer = "HTTP/1.1 404 Not Found\r\nContent-type: text/html\r\nContent-length: "+filepath.length()+"\r\n\r\n";
                    
                    pout.println(buffer + filepath);
                }
                
                if(html.exists()){
                System.out.println("Connected! Message was received!");
                }else{
                System.out.println("Connected! Message was not received!");
                }
            }
        
        }
}
