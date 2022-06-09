import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class Discover {

    MulticastSocket socket;     //creating a multicast socket object
    DatagramPacket packet;      //creating a packet object to receive data
    int port;        //port used to communicate
    String group;       //Multicast address of Shure Discovery Protocol

    String discoveredIP;

    String data;
    String model = "AD4D";

    boolean timeout;

    public Discover() {
        port = 8427;
        group = "239.255.254.253";
        timeout = false;
    }

    public static void main(String[] args) {

    }


    public void discover() {
        try (MulticastSocket multicastSocket = socket = new MulticastSocket(port)) {            //multicast constructor
            System.out.println("Multicast Receiver running at:" + socket.getLocalSocketAddress());
            socket.joinGroup(InetAddress.getByName(group));     //Joining the multicast group
            socket.setSoTimeout(2000);     //Set discovery timeout to 2s
            byte[] buf = new byte[512];     //Init buffer to store received data
            packet = new DatagramPacket(buf, buf.length);   //packet constructor
            System.out.println("Waiting for a multicast message...");
            try {
                socket.receive(packet);     //Receive packet from device
                System.out.println("Packet received! \n IP Address: " + packet.getAddress());       //Print discovered IP address
                discoveredIP = String.valueOf(packet.getAddress());     //Storing discovered IP address to variable
                discoveredIP = discoveredIP.replace("/","");        //removing the forward slash at the start
                System.out.println("\nData:");
                System.out.write(packet.getData(),0,packet.getLength());        //Print received data


                data = new String(packet.getData(), StandardCharsets.UTF_8);
                model = data.substring(92, 98);
                System.out.println("\n" +
                        model);
            } catch (SocketTimeoutException e) {
                timeout = true;
                System.out.println("No Devices Found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
