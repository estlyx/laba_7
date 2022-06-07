package managers;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.exit;

public class Tunnel {
    private final String host;
    private final String user;
    private final String password;
    private final int port;
    private final String tunnelRemoteHost;
    private final int tunnelLocalPort;
    private final int tunnelRemotePort;

    public Tunnel(String host,
                  String user,
                  String password,
                  int port,
                  String tunnelRemoteHost,
                  int tunnelLocalPort,
                  int tunnelRemotePort) {

        this.user = user;
        this.password = password;
        this.port = port;
        this.host = host;
        try {
            InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.out.println("Некорректный ssh-хост.");
            e.printStackTrace();
        }

        this.tunnelRemotePort = tunnelRemotePort;
        this.tunnelRemoteHost = tunnelRemoteHost;
        this.tunnelLocalPort = tunnelLocalPort;
    }

    public void connect() {
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            System.out.println("Устанавливается ssh подключение к " + host + " : " + port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            //DO PORT FORWARDING
            int assignedPort = session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
            System.out.println("Подключение установлено.");
            System.out.println("localhost:" + assignedPort + " -> " + tunnelRemoteHost + ":" + tunnelRemotePort);
            return;
        } catch (JSchException e) {
            System.out.println("Ошибка подключения через ssh-туннель.");
            e.printStackTrace();
        }
        exit(-1);
    }

}