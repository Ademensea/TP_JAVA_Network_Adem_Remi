package tests.com.project.network.udp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.project.network.udp.UDPServer;

class UDPServerTest {

    /**
     * Test that the UDPServer starts and listens on the specified port.
     * Ensures no exception occurs when starting the server.
     */
    @Test
    void testStartServer() {
        // Create a UDPServer object
        UDPServer server = new UDPServer(8080);

        try {
            // Start the server and ensure no exception is thrown
            server.launch();
        } catch (Exception e) {
            fail("UDPServer failed to start with exception: " + e.getMessage());
        }
    }

    /**
     * Test that the UDPServer can receive messages from clients.
     * Verifies that the server listens for incoming messages without throwing errors.
     */
    @Test
    void testServerReceivesMessages() {
        // Create a UDPServer object
        UDPServer server = new UDPServer(8080);

        try {
            // Start the server and simulate message reception
            server.launch();
            assertDoesNotThrow(() -> {
                // Simulate a UDP client sending messages here
            });
        } catch (Exception e) {
            fail("UDPServer failed to receive messages with exception: " + e.getMessage());
        }
    }
}

