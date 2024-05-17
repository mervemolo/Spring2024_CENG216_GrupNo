import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}/*import java.io.*;
import java.net.*;
        import java.util.ArrayList;
        import java.util.List;

public class Server {
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        final int PORT = 5000;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Sunucu başlatıldı ve " + PORT + " numaralı bağlantı noktasını dinliyor...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Yeni bir istemci bağlandı: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Bağlantı hatası: " + e.getMessage());
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                if (message == null) break;

                // Sunucu ekranına gönderilen mesajı yazdır
                System.out.println("İstemciden gelen mesaj: " + message);

                Server.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            System.err.println("Bir istemci hatası: " + e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
*/