package Threads;


import Tools.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicReference;

public class Sendm extends Thread {
    private final AtomicReference<Message> msout;
    private final DatagramSocket ds;
    private final AtomicReference<SocketAddress> addr;

    private volatile AtomicReference<InetAddress> host2;

    private final AtomicReference<Integer> port2;

    public Sendm(AtomicReference<Message> msout, DatagramSocket ds, AtomicReference<SocketAddress> addr, AtomicReference<InetAddress> host2, AtomicReference<Integer> port2) {
        this.msout = msout;
        this.ds = ds;
        this.addr = addr;
        this.host2 = host2;
        this.port2 = port2;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                this.wait();
            }
            //System.out.println(host2.get() + " " + port2.get() + " " + msout.get().getCommand());
            ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msout.get());
            byte[] arr = baos.toByteArray();
            DatagramPacket dps = new DatagramPacket(arr, arr.length, host2.get(), port2.get());
            ds.send(dps);

            /*
            ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(packout.get());
            byte[] arr = baos.toByteArray();
            ByteBuffer buf = ByteBuffer.wrap(arr);
            dc.send(buf, addr.get());*/
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

