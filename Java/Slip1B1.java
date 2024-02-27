import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Slip1B1 {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Slip1B1().initialize());
    }

    public void initialize() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        String serverAddress = JOptionPane.showInputDialog(frame, "Enter Server Address:", "Server Address", JOptionPane.QUESTION_MESSAGE);
        connectToServer(serverAddress);

        frame.setVisible(true);
    }

    private void connectToServer(String serverAddress) {
        try {
            socket = new Socket(serverAddress, 9876);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String username = JOptionPane.showInputDialog(frame, "Enter Your Username:", "Username", JOptionPane.QUESTION_MESSAGE);
            writer.println(username);

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        chatArea.append(serverMessage + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            writer.println(message);
            messageField.setText("");
        }
    }
}
