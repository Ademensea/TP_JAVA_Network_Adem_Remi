package com.project.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A multi-client TCP server that listens for client connections on a specified port.
 * It uses a thread pool to handle multiple clients simultaneously.
 */
public class TCPMultiServer {
    private static final int DEFAULT_PORT = 8080; // Default port number
    private static final int THREAD_POOL_SIZE = 10; // Number of threads in the thread pool
    private final int port; // Port to listen on

    /**
     * Constructs a TCP multi-client server with a custom port.
     *
     * @param port the port on which the server will listen for connections
     */
    public TCPMultiServer(int port) {
        this.port = port;
    }

    /**
     * Constructs a TCP multi-client server with the default port.
     */
    public TCPMultiServer() {
        this(DEFAULT_PORT);
    }

    /**
     * Launches the server, accepting client connections and delegating client communication
     * to a separate thread.
     */
    public void launch() {
        ExecutorService threadPool = createThreadPool();
        System.out.println("TCP Multi-Client Server running on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept a new client connection
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                // Handle the client in a separate thread
                threadPool.execute(new ConnectionHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Error in TCPMultiServer: " + e.getMessage());
        } finally {
            threadPool.shutdown(); // Shutdown thread pool when server stops
        }
    }

    /**
     * Creates a thread pool for handling client connections.
     *
     * @return an ExecutorService with a fixed number of threads
     */
    private ExecutorService createThreadPool() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    /**
     * Main method to launch the server. Optionally accepts a port number as an argument.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        TCPMultiServer server = new TCPMultiServer(port);
        server.launch();
    }
}

/**
 * Runnable class to handle communication with a single client.
 */
class ConnectionHandler implements Runnable {
    private static final String EXIT_COMMAND = "exit"; // Command to disconnect the client
    private final Socket clientSocket; // The client socket for communication

    /**
     * Constructs a handler for a specific client connection.
     *
     * @param clientSocket the socket representing the client
     */
    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Runs the communication loop for the client, receiving messages and sending responses.
     */
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String clientAddress = clientSocket.getInetAddress().toString();
            String message;

            // Communication loop with the client
            while ((message = in.readLine()) != null) {
                System.out.println("Message from " + clientAddress + ": " + message);
                out.println(clientAddress + " : " + message);

                // Disconnect client if the exit command is received
                if (message.equalsIgnoreCase(EXIT_COMMAND)) {
                    System.out.println("Client " + clientAddress + " disconnected.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error in ConnectionHandler: " + e.getMessage());
        } finally {
            closeClientSocket(); // Ensure socket is closed after communication
        }
    }

    /**
     * Closes the client socket gracefully.
     */
    private void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }
}

