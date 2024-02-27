import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Slip1B {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Slip1B().startServer(9876);
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String username = reader.readLine();
                System.out.println("New user joined: " + username);

                broadcastMessage(username + " has joined the chat.", this);

                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    broadcastMessage(username + ": " + clientMessage, this);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    writer.close();
                    socket.close();
                    clients.remove(this);
                    broadcastMessage("User left the chat.", this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }
}
