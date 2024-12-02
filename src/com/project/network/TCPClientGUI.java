package com.project.network;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TCPClientGUI {

   
    private JTextField serverAddressField;
    private JTextField portField;
    private JTextArea messageArea;
    private JTextField messageField;

   
    public void createAndShowGUI() {
        
        JFrame frame = new JFrame("Client TCP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridLayout(2, 2));

     
        connectionPanel.add(new JLabel("Adresse du serveur:"));
        serverAddressField = new JTextField("localhost");
        connectionPanel.add(serverAddressField);

        connectionPanel.add(new JLabel("Port:"));
        portField = new JTextField("8080");
        connectionPanel.add(portField);

        
        panel.add(connectionPanel, BorderLayout.NORTH);

       
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        
        messageField = new JTextField();
        panel.add(messageField, BorderLayout.SOUTH);

       
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                String serverAddress = serverAddressField.getText();
                int port = Integer.parseInt(portField.getText());
                String message = messageField.getText();

                
                sendMessageToServer(serverAddress, port, message);
            }
        });
        panel.add(sendButton, BorderLayout.EAST);

      
        frame.add(panel);

       
        frame.setVisible(true);
    }

    
    private void sendMessageToServer(String serverAddress, int port, String message) {
        try (Socket socket = new Socket(serverAddress, port)) {
            // Créer un flux de sortie pour envoyer le message
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Créer un flux d'entrée pour lire la réponse du serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           
            out.println(message);
            messageArea.append("Vous: " + message + "\n");

            String serverResponse = in.readLine();
            messageArea.append("Serveur: " + serverResponse + "\n");

           
            messageField.setText("");
        } catch (IOException e) {
            messageArea.append("Erreur de connexion: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                new TCPClientGUI().createAndShowGUI();
            }
        });
    }
}

