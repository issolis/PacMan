import java.io.*;
import java.net.*;

public class Client {

    void sendMessage(){
        String serverAddress = "127.0.0.1"; // Dirección IP o nombre de host del servidor
        int serverPort = 12345; // Puerto del servidor

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor.");
            out.println("Buena puta!"); // Enviar un mensaje al servidor
            String response = in.readLine(); // Leer la respuesta del servidor
            System.out.println("Respuesta del servidor: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client(); 
        client.sendMessage();
    }
}
