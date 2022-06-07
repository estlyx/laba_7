package Threads;

import Tools.Message;
import serv.ServConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.atomic.AtomicReference;

public class Readm extends Thread {
    private final AtomicReference<Message> tmp;
    private final DatagramSocket ds;

    private final AtomicReference<InetAddress> host;

    private final AtomicReference<Integer> port;
    private final Makem make;


    public Readm(AtomicReference<Message> tmp, DatagramSocket ds, AtomicReference<InetAddress> host, AtomicReference<Integer> port, Makem make) {
        this.tmp = tmp;
        this.ds = ds;
        this.host = host;
        this.make = make;
        this.port=port;
        //start();
    }

    @Override
    public void run() {
        try {
            byte[] ars = new byte[4096];
            DatagramPacket dpr = new DatagramPacket(ars, ars.length);
            //System.out.println("popa");
            ds.receive(dpr);

            int port2 = dpr.getPort();
            System.out.println(port2 + "YYY");
            InetAddress host2 = dpr.getAddress();
            ByteArrayInputStream bis = new ByteArrayInputStream(ars);
            ObjectInputStream oin = new ObjectInputStream(bis);
            tmp.set((Message) oin.readObject());
            System.out.println(tmp.get().getCommand());
            host.set(host2);
            port.set(port2);
            ServConnection.count--;
            synchronized (make) {
                make.notify();
            }
            /*
            byte[] arr = new byte[4096];
            ByteBuffer buf;
            buf = ByteBuffer.wrap(arr);
            SocketAddress sa = dc.receive(buf);
            addr.set(sa);
            ByteArrayInputStream bis = new ByteArrayInputStream(arr);
            ObjectInputStream oin = new ObjectInputStream(bis);
            tmp.set((Message) oin.readObject());
            synchronized (make) {
                make.notify();
            }*/
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}