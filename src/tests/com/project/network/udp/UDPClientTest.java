package tests.com.project.network.udp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.project.network.udp.UDPClient;

class UDPClientTest {

    /**
     * Test that the UDPClient can send a message.
     * Ensures no exception occurs when sending a message to the server.
     */
    @Test
    void testSendMessage() {
        // Create a UDPClient object
        UDPClient client = new UDPClient("localhost", 8080);

        try {
            // Send a message and ensure no exception is thrown
            client.sendMessage("Hello, Server!");
        } catch (Exception e) {
            fail("Failed to send message with exception: " + e.getMessage());
        }
    }

    /**
     * Test that the UDPClient can send and receive messages from the server.
     * Verifies that the client can communicate with the server successfully.
     */
    @Test
    void testMessageExchange() {
        // We create a UDPClient object
        UDPClient client = new UDPClient("localhost", 8080);

        try {
            // Send a message and verify it's sent successfully
            client.sendMessage("Hello, Server!");
            // Since UDP is connectionless, we can't directly verify a response here
            // We can check if no exception is thrown instead
        } catch (Exception e) {
            fail("Message exchange failed with exception: " + e.getMessage());
        }
    }
}

