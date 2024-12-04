package com.project.network.udp;

import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP Client that sends messages to a specified server over UDP.
 */
public class UDPClient {
    private static final int DEFAULT_PORT = 8080; // Default port to connect to
    private final String serverAddress; // Server address to send messages to
    private final int port; // Port to send messages to

    /**
     * Constructs a new UDP client with the specified server address and port.
     *
     * @param serverAddress the server address to send messages to
     * @param port the port to use for communication
     */
    public UDPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * Sends a message to the specified server using UDP.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buffer = message.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(serverAddress), port);
            socket.send(packet);
            System.out.println("Message sent: " + message);
        } catch (IOException e) {
            System.err.println("Error while sending message: " + e.getMessage());
        }
    }

    /**
     * Main method that allows user input from the console to send UDP messages.
     * 
     * @param args command-line arguments for server address and port
     */
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Default server address
        int portToUse = DEFAULT_PORT; // Default port

        // Parse arguments for server address and port
        if (args.length > 0) {
            serverAddress = args[0];
        }
        if (args.length > 1) {
            try {
                portToUse = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port " + DEFAULT_PORT);
            }
        }

        UDPClient client = new UDPClient(serverAddress, portToUse);
        Console console = System.console(); // Get the console for user input

        if (console == null) {
            System.err.println("No console available.");
            return;
        }

        // Loop to send and read messages from the console
        while (true) {
            String message = console.readLine("Enter a message to send (or 'exit' to quit): ");
            if ("exit".equalsIgnoreCase(message)) {
                System.out.println("Exiting...");
                break;
            }
            client.sendMessage(message);
        }
    }
}

