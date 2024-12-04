package com.project.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP Server that listens for incoming UDP packets and prints received messages.
 */
public class UDPServer {
    private static final int DEFAULT_PORT = 8080; // Default port for the server
    private final int port; // Port to listen on

    /**
     * Constructs a new UDP server with the specified port.
     *
     * @param port the port to listen on
     */
    public UDPServer(int port) {
        this.port = port;
    }

    /**
     * Constructs a new UDP server with the default port (8080).
     */
    public UDPServer() {
        this(DEFAULT_PORT);
    }

    /**
     * Starts the server to listen for incoming UDP messages.
     * Receives messages and prints them to the console.
     */
    public void launch() {
        // Creating UDP socket on the specified port
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Server listening on port " + port);

            // Buffer to keep received data
            byte[] buffer = new byte[1024];

            while (true) {
                // Creating a packet to receive data
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Receiving the packet from the client

                // Convert the byte array to a string
                String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                System.out.println("Received from " + packet.getAddress() + ":" + packet.getPort() + ": " + message);
            }
        } catch (IOException e) {
            System.err.println("Error while receiving UDP packets: " + e.getMessage());
        }
    }

    /**
     * Main method to start the UDP server.
     * 
     * @param args command-line arguments for port (optional)
     */
    public static void main(String[] args) {
        int portToUse = (args.length > 0) ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        UDPServer server = new UDPServer(portToUse);
        server.launch();
    }

    /**
     * Returns a string representation of the UDP server, including the port it's listening on.
     *
     * @return string representation of the server
     */
    @Override
    public String toString() {
        return "UDPServer listening on port " + port;
    }
}

