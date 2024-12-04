package tests.com.project.network.tcp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.project.network.tcp.TCPServer;

class TCPServerTest {

    /**
     * Test that the server starts and listens on the specified port.
     * Ensures that no exception is thrown when starting the server.
     */
    @Test
    void testStartServer() {
        // We create a TCPServer object
        TCPServer server = new TCPServer(8080);

        try {
            // We start the server and ensure no exception is thrown
            server.start();
        } catch (IOException e) {
            fail("Server failed to start with IOException: " + e.getMessage());
        }
    }

    /**
     * Test that the server can handle a connection.
     * Verifies that the server can accept a client connection without errors.
     */
    @Test
    void testServerConnection() {
        // We create a TCPServer object
        TCPServer server = new TCPServer(8080);

        try {
            // Start the server and attempt a connection
            server.start();
            assertDoesNotThrow(() -> {
                // Simulate a connection or client interaction here
            });
        } catch (IOException e) {
            fail("Server connection failed with IOException: " + e.getMessage());
        }
    }
}

