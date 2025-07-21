package org.example.pythonandjava.JavaScripts;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

public class NetworkPing extends Thread{

   private final int startIp;
   private final int endIp;

    // Constructor to set the IP range for this thread
    public NetworkPing(int startIp, int endIp) {
        this.startIp = startIp;
        this.endIp = endIp;
    }

    @Override
    public void run() {
        try {
            for (int i = startIp; i <= endIp; i++) {
                String ipAddress = "192.168.1." + i;
                sendPingRequest(ipAddress);
            }
        } catch (Exception e) {
            System.out.println("Exception is caught");
            System.err.println("Error: " + java.util.Arrays.toString(e.getStackTrace()));
        }
    }

    public static void sendPingRequest(String ipAddress)
            throws IOException {
        InetAddress address = InetAddress.getByName(ipAddress);
        if (address.isReachable(1000)) {
            System.out.println("Host is reachable at "+ipAddress);
            try {
                FileWriter IpWriter = new FileWriter("/PythonAndJava/src/main/res/Logs/DiscoveredIPs", true);
                IpWriter.write("Connected device at "+ipAddress+ System.lineSeparator());
                IpWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                System.err.println("Error: " + java.util.Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static void main(String[] args)
            throws IOException {

        {
            int n = 32; // Number of threads
            int rangePerThread = 254/n;
            int start = 1;

            for (int i = 0; i < n; i++) {
                int end = (i == n -1) ? 254 : start + rangePerThread - 1;
                NetworkPing thread = new NetworkPing(start, end);
                thread.start();
                start = end + 1;
            }
        }
    }
}
