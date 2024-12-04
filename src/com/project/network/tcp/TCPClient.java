package com.project.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TCPClient handles the TCP communication with a server.
 */
public class TCPClient {
    private String serverAddress;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Constructor for TCPClient.
     * @param serverAddress The server's address.
     * @param port The port to connect to.
     */
    public TCPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * Connects to the server.
     * @throws IOException if the connection fails.
     */
    public void connect() throws IOException {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message to the server and receives a response.
     * @param message The message to send.
     * @return The server's response.
     * @throws IOException if an I/O error occurs.
     */
    public String sendMessage(String message) throws IOException {
        out.println(message);
        return in.readLine();
    }

    /**
     * Disconnects from the server.
     * @throws IOException if an I/O error occurs.
     */
    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}



