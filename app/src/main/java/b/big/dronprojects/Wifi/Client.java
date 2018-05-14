package b.big.dronprojects.Wifi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client implements Runnable {

    private String message;

    public Client(String message) {
        this.message = message;
    }
    private String stringData;

    @Override
    public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    InetAddress serverAddr = InetAddress.getByName("192.168.4.1");
                    DatagramPacket dp;
                    dp = new DatagramPacket(this.message.getBytes(), this.message.length(), serverAddr, 9001);
                    ds.send(dp);
                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.receive(dp);
                    stringData = new String(lMsg, 0, dp.getLength());

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
            }
}