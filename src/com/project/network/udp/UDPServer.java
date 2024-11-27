package com.project.network.udp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class UDPServer {
    //default port if none is specified
    private static final int DEFAULT_PORT = 8080;
    private int port;

    public UDPServer(int port) {
        this.port = port;
    }

    public UDPServer() {
        this(DEFAULT_PORT);
    }

    public void launch() {
        //creating UDP socket on specified port
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Serveur UDP en écoute sur le port " + port);
        //buffer to keep received data
            byte[] buffer = new byte[1024];

            while (true) {
            //creating packet to receive data
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                System.out.println("Reçu de " + packet.getAddress() + ":" + packet.getPort() + ": " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //veryfing if a port is passed as an argument , if not ,default port is used
        int portToUse;
        if (args.length > 0) {
            portToUse = Integer.parseInt(args[0]);
        } else {
            portToUse = DEFAULT_PORT;
        }
        UDPServer server = new UDPServer(portToUse);
        server.launch();
    }

    @Override
    public String toString() {
        return "UDPServer en écoute sur le port " + port;
    }
}
