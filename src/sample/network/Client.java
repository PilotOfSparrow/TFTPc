package sample.network;

import java.io.IOException;
import java.net.*;

public class Client {

//    static final int DEFAULT_TFTP_PORT = 69;
    private InetAddress serverIpAddress;
    private int serverPort;
    private boolean isServerPortChanged;

    private DatagramSocket clientSocket;
    private DatagramPacket clientPacket;



    public Client(String sIpAddress, String sPort) {

        try {
            serverIpAddress = Inet4Address.getByName(sIpAddress);
            serverPort = Integer.parseInt(sPort);
            isServerPortChanged = false;

            clientSocket = new DatagramSocket();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] sendBuff, int sendBuffSize) {

        clientPacket = new DatagramPacket(sendBuff, sendBuffSize, serverIpAddress, serverPort);

        try {
            clientSocket.send(clientPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket receive(byte[] receiveBuff) {

//        byte[] receiveBuff = new byte[TftpBasePacket.DEFAULT_PACKAGE_SIZE];
        clientPacket = new DatagramPacket(receiveBuff, receiveBuff.length);

        try {
            clientSocket.receive(clientPacket);

        if (!isServerPortChanged) {
            serverPort = clientPacket.getPort();
            isServerPortChanged = true;
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientPacket;
    }
}
