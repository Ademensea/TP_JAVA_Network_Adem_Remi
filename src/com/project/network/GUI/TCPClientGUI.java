package com.project.network.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.project.network.tcp.TCPClient;

/**
 * Graphical User Interface (GUI) for the TCP Client.
 * Provides an interface for users to connect to a TCP server and exchange messages.
 */
public class TCPClientGUI {

    private TCPClient tcpClient; // Handles TCP communication
    private JTextField serverAddressField; // Input for server address
    private JTextField portField; // Input for port
    private JTextArea messageArea; // Displays communication logs
    private JTextField messageField; // Input for the user's message
    private JButton sendButton; // Button to send messages

    /**
     * Creates and displays the GUI.
     */
    public void createAndShowGUI() {
        // Setup main frame
        JFrame frame = new JFrame("TCP Client GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Add connection input fields
        panel.add(createConnectionPanel(), BorderLayout.NORTH);

        // Add message display area
        panel.add(createMessageDisplayArea(), BorderLayout.CENTER);

        // Add message input field and send button
        panel.add(createMessageInputPanel(), BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Creates the connection panel (server address and port inputs).
     */
    private JPanel createConnectionPanel() {
        JPanel connectionPanel = new JPanel(new GridLayout(2, 2));

        serverAddressField = new JTextField("localhost");
        portField = new JTextField("8080");

        connectionPanel.add(new JLabel("Server Address:"));
        connectionPanel.add(serverAddressField);
        connectionPanel.add(new JLabel("Port:"));
        connectionPanel.add(portField);

        return connectionPanel;
    }

    /**
     * Creates the message display area.
     */
    private JScrollPane createMessageDisplayArea() {
        messageArea = new JTextArea();
        messageArea.setEditable(false); // Read-only area
        return new JScrollPane(messageArea);
    }

    /**
     * Creates the message input panel (text field and send button).
     */
    private JPanel createMessageInputPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSendMessage();
            }
        });

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        return bottomPanel;
    }

    /**
     * Handles the sending of a message to the server.
     * Connects to the server if not already connected.
     */
    private void handleSendMessage() {
        try {
            ensureConnection();
            String message = messageField.getText();
            String response = tcpClient.sendMessage(message);
            appendMessageToChat("You", message);
            appendMessageToChat("Server", response);
            messageField.setText(""); // Clear input field
        } catch (Exception ex) {
            appendMessageToChat("Error", ex.getMessage());
        }
    }

    /**
     * Ensures that the client is connected to the server.
     * Connects if not already connected.
     *
     * @throws IOException if the connection fails.
     */
    private void ensureConnection() throws IOException {
        if (tcpClient == null) {
            String serverAddress = serverAddressField.getText();
            int port = Integer.parseInt(portField.getText());
            tcpClient = new TCPClient(serverAddress, port);
            tcpClient.connect();
            appendMessageToChat("Info", "Connected to " + serverAddress + ":" + port);
        }
    }

    /**
     * Appends a message to the chat area.
     *
     * @param sender  The sender of the message.
     * @param message The message content.
     */
    private void appendMessageToChat(String sender, String message) {
        messageArea.append(sender + ": " + message + "\n");
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TCPClientGUI().createAndShowGUI());
    }
}


