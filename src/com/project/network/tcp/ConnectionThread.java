package com.project.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A thread that handles communication with a single client. It receives messages from the
 * client, processes them, and sends responses back to the client.
 */
public class ConnectionThread extends Thread {
    private final Socket clientSocket; // The client socket for communication

    /**
     * Constructs a new thread to handle communication with a specific client.
     *
     * @param clientSocket the socket representing the client connection
     */
    public ConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Runs the communication loop with the client. This method listens for messages from the
     * client, processes them, and sends an echo response. The connection is closed when the
     * client sends the "exit" command.
     */
    @Override
    public void run() {
        String clientAddress = clientSocket.getInetAddress().getHostAddress();

        // Set up the streams for communication with the client
        try (
            BufferedReader reader = createReader(clientSocket);
            PrintWriter writer = createWriter(clientSocket)
        ) {
            System.out.println("Connected to client: " + clientAddress);

            String receivedMessage;
            while ((receivedMessage = reader.readLine()) != null) {
                processMessage(clientAddress, receivedMessage, writer);

                // If the client sends "exit", disconnect
                if ("exit".equalsIgnoreCase(receivedMessage)) {
                    System.out.println("Client " + clientAddress + " disconnected.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error with client " + clientAddress + ": " + e.getMessage());
        } finally {
            closeClientSocket(clientSocket);
        }
    }

    /**
     * Creates a BufferedReader to read messages from the client.
     *
     * @param clientSocket the socket representing the client connection
     * @return a BufferedReader for reading data from the client
     * @throws IOException if an I/O error occurs while creating the reader
     */
    private BufferedReader createReader(Socket clientSocket) throws IOException {
        InputStream input = clientSocket.getInputStream();
        return new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    /**
     * Creates a PrintWriter to send messages to the client.
     *
     * @param clientSocket the socket representing the client connection
     * @return a PrintWriter for writing data to the client
     * @throws IOException if an I/O error occurs while creating the writer
     */
    private PrintWriter createWriter(Socket clientSocket) throws IOException {
        OutputStream output = clientSocket.getOutputStream();
        return new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);
    }

    /**
     * Processes the received message from the client and sends an appropriate response.
     *
     * @param clientAddress the address of the client
     * @param receivedMessage the message received from the client
     * @param writer the PrintWriter used to send the response
     */
    private void processMessage(String clientAddress, String receivedMessage, PrintWriter writer) {
        System.out.println("Message from " + clientAddress + ": " + receivedMessage);

        // Respond to the client with an echo message
        String response = clientAddress + ": " + receivedMessage;
        writer.println(response);
    }

    /**
     * Closes the client socket after the communication is finished.
     *
     * @param clientSocket the socket to close
     */
    private void closeClientSocket(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to close client socket: " + e.getMessage());
        }
    }
}

