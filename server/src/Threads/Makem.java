package Threads;

import Commands.AnyCommand;
import Commands.Commands;
import ForCity.City;
import ForCity.Coordinates;
import ForCity.Government;
import ForCity.Human;
import Tools.Message;
import managers.DBManager;
import serv.ServConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicReference;

public class Makem extends Thread {
    private AtomicReference<Message> ms, msout;
    private final Commands commands;
    private final Sendm send;

    public Makem(AtomicReference<Message> ms, AtomicReference<Message> msout,
                 Commands commands, Sendm send) {
        this.ms = ms;
        this.msout = msout;
        this.commands = commands;
        this.send = send;
        //start();
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //System.out.println(ms.get().getCommand() + "22");
        if (ms.get().getCity() != null) {
            //msout = new Message("Город успешно добавлен.");
            try {
                msout.set(new Message(commands.getCommand("add").execute(ms.get().getCity())));
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
            //citycollection.add(ms.getCity());

        } else if (ms.get().getArg() != null) {
            if (ms.get().getCommand().equals("Login")) {
                msout.set(new Message(commands.getCommand("login").execute(ms.get().getLogin(), ms.get().getArg())));
                System.out.println(msout.get().getCommand());
            }
            else{
                if (ms.get().getCommand().equals("NewLogin")){
                    msout.set(new Message(commands.getCommand("newlogin").execute(ms.get().getLogin(), ms.get().getArg())));
                    System.out.println(msout.get().getCommand());
                }
                else{
                    msout.set(new Message(commands.getCommand(ms.get().getCommand()).execute(ms.get().getArg(), ms.get().getLogin())));
                }
            }

            //System.out.println(commands.getCommand(ms.getCommand()).execute(ms.getArg()));
        } else {
            if (ms.get().getCommand().equals("exit")){
                try {
                    System.out.println(commands.getCommand("save").execute());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                msout.set(new Message("Завершаю работу."));
            }
            else{
                try {
                    msout.set(new Message(commands.getCommand(ms.get().getCommand()).execute("null")));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            //System.out.println(commands.getCommand(ms.getCommand()).execute("null"));
        }


        ResultSet tmp = null;
        try {
            tmp = DBManager.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM city");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServConnection.citycollection.clear();
        while (true) {
            try {
                if (!tmp.next()) break;
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }




        }
        //System.out.println("kringe");
        synchronized (send) {
            send.notify();
        }
    }
}