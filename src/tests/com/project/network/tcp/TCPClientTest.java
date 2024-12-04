package tests.com.project.network.tcp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.project.network.tcp.TCPClient;

class TCPClientTest {

    /**
     * Test the connection to the server.
     * Ensures that no exception is thrown when connecting to the server.
     */
    @Test
    void testConnection() {
        // Create a TCPClient object
        TCPClient client = new TCPClient("localhost", 8080);

        // We use a try-catch block to capture IOException
        try {
            // Ensure that the connect() method does not throw any exceptions
            client.connect();
        } catch (IOException e) {
            fail("Connection failed with IOException: " + e.getMessage());
        }
    }

    /**
     * We test the message exchange between client and server.
     * We verifies that a message sent from the client is properly sent and received.
     */
    @Test
    void testMessageExchange() {
        // We create a TCPClient object
        TCPClient client = new TCPClient("localhost", 8080);

        try {
            // Connect to the server
            client.connect();

            // Test if the message is successfully sent and received
            String response = client.sendMessage("Hello, Server!");
            assertNotNull(response, "Response should not be null");

            // Disconnect from the server
            client.disconnect();
        } catch (IOException e) {
            fail("IOException during message exchange: " + e.getMessage());
        }
    }
}




