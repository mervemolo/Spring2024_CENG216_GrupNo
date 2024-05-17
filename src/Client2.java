import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client2 {
    private static JTextArea chatArea;
    private static JTextField inputField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("İstemci 2");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);
            frame.add(scrollPane, BorderLayout.CENTER);

            inputField = new JTextField();
            inputField.setPreferredSize(new Dimension(400, 50)); // Mesaj gönderme alanını küçültmek için boyut belirle
            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendMessage();
                }
            });
            frame.add(inputField, BorderLayout.SOUTH);

            JButton fileButton = new JButton("Dosya Gönder");
            fileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendFile();
                }
            });
            frame.add(fileButton, BorderLayout.NORTH);

            frame.setVisible(true);
        });

        final String SERVER_IP = "localhost";
        final int SERVER_PORT = 5000;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            Thread receiverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String message;
                        while ((message = in.readLine()) != null) {
                            if (message.startsWith("Ben: ")) {
                                String finalMessage = message.substring("Ben: ".length());
                                SwingUtilities.invokeLater(() -> {
                                    chatArea.append("(gelen mesaj): " + finalMessage + "\n");
                                });
                            } else {
                                String finalMessage = message.substring("İstemci 1: ".length());
                                SwingUtilities.invokeLater(() -> {
                                    chatArea.append("ben): " + finalMessage + "\n");
                                });
                            }
                        }
                    } catch (IOException ex) {
                        System.err.println("Sunucu bağlantısı kesildi.");
                    }
                }
            });
            receiverThread.start();

            while (true) {
                // Kullanıcıdan mesajları al ve sunucuya gönder
            }
        } catch (IOException e) {
            System.err.println("Sunucuya bağlanırken hata oluştu: " + e.getMessage());
        }
    }

    private static void sendMessage() {
        String message = inputField.getText();
        inputField.setText("");

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("İstemci 2: " + message);
            chatArea.append(".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Socket socket = new Socket("localhost", 5000);
                 OutputStream out = socket.getOutputStream();
                 FileInputStream fileInputStream = new FileInputStream(file)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                chatArea.append("Dosya gönderildi: " + file.getName() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



