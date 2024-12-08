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
        socket = new Socket(serverAddress, port);  // Establishing connection to the server
        out = new PrintWriter(socket.getOutputStream(), true);  // Output stream for sending data
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Input stream for receiving data
    }

    /**
     * Sends a message to the server and receives a response.
     * @param message The message to send.
     * @return The server's response.
     * @throws IOException if an I/O error occurs.
     */
    public String sendMessage(String message) throws IOException {
        out.println(message);  // Sending the message to the server
        return in.readLine();  // Reading the response from the server
    }

    /**
     * Disconnects from the server.
     * @throws IOException if an I/O error occurs during disconnection.
     */
    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();  // Closing the socket connection
            System.out.println("Disconnected from the server.");
        }
    }

  
  
  
  
  
    /**
     * Main method to run the TCPClient.
     * @param args Command-line arguments: server address and port.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java TCPClient <server address> <port>");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        TCPClient client = new TCPClient(serverAddress, port);

        // Adding a Shutdown Hook to capture the program termination (Ctrl + C)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                client.disconnect();  // Disconnect properly when the program is closed
            } catch (IOException e) {
                System.err.println("Error while disconnecting: " + e.getMessage());
            }
        }));

        try {
            client.connect();  // Connecting to the server
            System.out.println("Connected to the server.");

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));  // Reading user input from the console

            String userMessage;
            // The user can keep sending messages until they type "exit"
            while (true) {
                System.out.print("Enter message to send (type 'exit' to quit): ");
                userMessage = userInput.readLine();  // Reading the message input by the user

                if ("exit".equalsIgnoreCase(userMessage)) {
                    System.out.println("Exiting...");
                    break;  // Exiting the loop if the user types "exit"
                }

                // Sending the message to the server
                String response = client.sendMessage(userMessage);
                System.out.println("Server response: " + response);  // Displaying the server's response
            }

            // Disconnecting when the loop ends
            client.disconnect();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());  // Handling any IOException
        }
    }
}



