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

    // Champs pour l'adresse IP du serveur et le port
    private JTextField serverAddressField;
    private JTextField portField;
    private JTextArea messageArea;
    private JTextField messageField;

    // Méthode pour créer l'interface
    public void createAndShowGUI() {
        // Créer la fenêtre principale
        JFrame frame = new JFrame("Client TCP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Créer un panneau principal pour organiser les éléments
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Créer un sous-panneau pour l'adresse et le port
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridLayout(2, 2));

        // Champs de texte pour l'adresse IP et le port
        connectionPanel.add(new JLabel("Adresse du serveur:"));
        serverAddressField = new JTextField("localhost");
        connectionPanel.add(serverAddressField);

        connectionPanel.add(new JLabel("Port:"));
        portField = new JTextField("8080");
        connectionPanel.add(portField);

        // Ajouter le panneau de connexion au panneau principal
        panel.add(connectionPanel, BorderLayout.NORTH);

        // Zone de texte pour afficher la communication
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Champ de texte pour saisir un message
        messageField = new JTextField();
        panel.add(messageField, BorderLayout.SOUTH);

        // Bouton pour envoyer le message
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'adresse, le port et le message
                String serverAddress = serverAddressField.getText();
                int port = Integer.parseInt(portField.getText());
                String message = messageField.getText();

                // Envoyer le message via TCP
                sendMessageToServer(serverAddress, port, message);
            }
        });
        panel.add(sendButton, BorderLayout.EAST);

        // Ajouter le panneau principal à la fenêtre
        frame.add(panel);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    // Méthode pour envoyer un message via TCP
    private void sendMessageToServer(String serverAddress, int port, String message) {
        try (Socket socket = new Socket(serverAddress, port)) {
            // Créer un flux de sortie pour envoyer le message
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Créer un flux d'entrée pour lire la réponse du serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Envoyer le message
            out.println(message);
            messageArea.append("Vous: " + message + "\n");

            // Lire la réponse du serveur
            String serverResponse = in.readLine();
            messageArea.append("Serveur: " + serverResponse + "\n");

            // Effacer le champ de texte pour le prochain message
            messageField.setText("");
        } catch (IOException e) {
            messageArea.append("Erreur de connexion: " + e.getMessage() + "\n");
        }
    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Créer et afficher l'interface
                new TCPClientGUI().createAndShowGUI();
            }
        });
    }
}

