package com.project.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCPServer handles TCP communication with multiple clients.
 */
public class TCPServer {
    private int port;
    private ServerSocket serverSocket;

    /**
     * Constructor for TCPServer.
     * @param port The port to bind to.
     */
    public TCPServer(int port) {
        this.port = port;
    }

    /**
     * Starts the server and listens for client connections.
     * @throws IOException if an I/O error occurs.
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server is running on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    /**
     * Handles communication with a connected client.
     * @param clientSocket The client's socket.
     */
    private void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                out.println("Message Received");
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    /**
     * Stops the server.
     * @throws IOException if an I/O error occurs.
     */
    public void stop() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }


}


