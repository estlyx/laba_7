package serv;

import Commands.*;
import ForCity.*;
import Threads.Makem;
import Threads.Readm;
import Threads.Sendm;
import Tools.Message;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ServConnection {
    private static final ExecutorService servicectp = Executors.newCachedThreadPool();
    public static volatile int count = 0;
    private static final ExecutorService serviceftp = Executors.newFixedThreadPool(30);
    private SocketAddress addr;
    public static CityCollection citycollection;
    private Commands commands = new Commands();

    private final DatagramSocket ds;
    public ServConnection(int port) throws IOException {

        this.citycollection = new CityCollection();
        this.addr = new InetSocketAddress(port);
        ds = new DatagramSocket(addr);
        commands.regist(new Add(), new AveragePhoneCode(), new Clear(), new Exit(), new FillCollection(), new Help(),
                new Info(), new Login(), new NewLogin(), new Remove(), new Remove_at(), new Reorder(), new Save(), new Show(), new Shuffle(),
                new Update());
        System.out.println(commands.getCommand("FillCollection").execute());
    }



    public void go() throws IOException, ClassNotFoundException, SQLException, InterruptedException {
        do {
            //System.out.println(i);
            if (count < 15) {
                AtomicReference<SocketAddress> addr = new AtomicReference<>();
                AtomicReference<InetAddress> host = new AtomicReference<>();
                AtomicReference<Message> tmp = new AtomicReference<>();
                AtomicReference<Message> packout = new AtomicReference<>();
                AtomicReference<Integer> port = new AtomicReference<>();

                count++;
                Sendm send = new Sendm(packout, ds, addr, host, port);
                Makem make = new Makem(tmp, packout, commands, send);
                Readm take = new Readm(tmp, ds, host, port, make);
                serviceftp.submit(take);
                serviceftp.submit(make);
                servicectp.submit(send);
            }
            //System.out.println("kek");
        } while (true);
        /*Scanner sc = new Scanner(System.in);
        DatagramChannel dc;
        dc = DatagramChannel.open();
        dc.bind(addr);*/

        /*DatagramSocket ds = new DatagramSocket(port);
        while (true) {
            byte[] ars = new byte[4096];
            DatagramPacket dpr = new DatagramPacket(ars, ars.length);
            ds.receive(dpr);
            int port2 = dpr.getPort();
            InetAddress host2 = dpr.getAddress();
            ByteArrayInputStream bis = new ByteArrayInputStream(ars);
            ObjectInputStream oin = new ObjectInputStream(bis);
            Message ms = (Message) oin.readObject();
            //System.out.println(ms.getCommand());

            Message msout;
            if (ms.getCity() != null) {
                //msout = new Message("Город успешно добавлен.");
                msout = new Message(commands.getCommand("add").execute(ms.getCity()));
                //citycollection.add(ms.getCity());

            } else if (ms.getArg() != null) {
                if (ms.getCommand().equals("Login")) {
                    msout=new Message(commands.getCommand("login").execute(ms.getLogin(), ms.getArg()));
                    System.out.println(msout.getCommand());
                }
                else{
                    if (ms.getCommand().equals("NewLogin")){
                        msout=new Message(commands.getCommand("newlogin").execute(ms.getLogin(), ms.getArg()));
                        System.out.println(msout.getCommand());
                    }
                    else{
                        City c1 = citycollection.getCollection().get(Integer.parseInt(ms.getArg()));
                        if (c1.getLogin() == ms.getLogin())
                        {

                            msout = new Message(commands.getCommand(ms.getCommand()).execute(ms.getArg()));
                        }
                        else{
                            msout = new Message("У вас нет прав для изменения этого элемента");
                        }
                    }
                }

                //System.out.println(commands.getCommand(ms.getCommand()).execute(ms.getArg()));
            } else {
                if (ms.getCommand().equals("exit")){
                    System.out.println(commands.getCommand("save").execute());
                    msout = new Message("Завершаю работу.");
                }
                else{
                    msout = new Message(commands.getCommand(ms.getCommand()).execute("null"));
                }
                //System.out.println(commands.getCommand(ms.getCommand()).execute("null"));
            }

            ResultSet tmp = DBManager.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM city");
            ServConnection.citycollection.clear();
            while (tmp.next()) {
                City city = new City();
                city.setId(tmp.getInt("id"));
                city.setName(tmp.getString("name"));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(tmp.getInt("x"));
                coordinates.setY(tmp.getInt("y"));
                city.setCoordinates(coordinates);
                city.setCreationDate(LocalDate.parse(tmp.getString("creationdate")));
                city.setAreaSize(tmp.getLong("area"));
                city.setPopulation(tmp.getLong("population"));
                city.setMetersAboveSeaLevel(tmp.getFloat("metersabovesealevel"));
                city.setEstablishmentDate(LocalDate.parse(tmp.getString("establishmentdate")));
                city.setTelephoneCode(tmp.getInt("telephonecode"));
                city.setGovernment(Government.valueOf(tmp.getString("government")));
                Human governor = new Human();
                governor.setHeight(tmp.getInt("governor"));
                city.setGovernor(governor);
                city.setLogin(tmp.getString("login"));
                ServConnection.citycollection.add(city);

            }


            ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msout);
            byte[] arr = baos.toByteArray();
            DatagramPacket dps = new DatagramPacket(arr, arr.length, host2, port2);
            ds.send(dps);
        }
            */

    }
}
