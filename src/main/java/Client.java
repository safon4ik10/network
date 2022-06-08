import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)
        ) {
            String input;
            while (true) {
                System.out.println("Enter message for server: ");
                input = scanner.nextLine();
                printWriter.println(input);
                if (input.equals("end")){
                    break;
                }

                System.out.println(bufferedReader.readLine());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
