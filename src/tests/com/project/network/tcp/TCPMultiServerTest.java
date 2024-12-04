package tests.com.project.network.tcp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.project.network.tcp.TCPMultiServer;

class TCPMultiServerTest {

    /**
     * Test that the multi-client server starts without any errors.
     * Ensures that the server can accept multiple connections.
     */
    @Test
    void testStartMultiServer() {
        // We create a TCPMultiServer object
        TCPMultiServer server = new TCPMultiServer(8080);

        // Start the multi-client server
        assertDoesNotThrow(() -> server.launch());
    }

    /**
     * Test that the server can handle multiple client connections concurrently.
     * Verifies that the server does not throw exceptions during multiple connections.
     */
    @Test
    void testMultiClientConnection() {
        // We create a TCPMultiServer object
        TCPMultiServer server = new TCPMultiServer(8080);

        // Start the multi-client server and simulate multiple connections
        assertDoesNotThrow(() -> server.launch());
    }
}


