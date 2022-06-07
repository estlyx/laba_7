import Commands.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Scanner in = new Scanner(System.in);
        InetAddress host = InetAddress.getLocalHost();
        int port=1338;
        ClientTools cc = new ClientTools(in, host, port);
        cc.go();


    }
}
