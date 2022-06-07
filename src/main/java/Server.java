import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);
        ExecutorService executorService = Executors.newFixedThreadPool(500);

        System.out.println("server up");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> receive(clientSocket));
            }
        } catch (IOException e){
            e.printStackTrace();
            serverSocket.close();
        }

    }

    public static void receive(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println("New connection accepted");
                printWriter.println(String.format("Hi %s, your port is %d", input, socket.getPort()));
                if (input.equals("end")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
