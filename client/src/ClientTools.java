import ForCity.*;
import Tools.Message;
import javafx.scene.Node;
import managers.DBManager;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class ClientTools {
    Scanner in;
    InetAddress host;
    int port;
    Message ms;

    SocketAddress addr;
    DatagramChannel dc;
    public ClientTools(Scanner in, InetAddress host, int port) throws IOException {
        this.in = in;
        this.host = host;
        this.port = port;
        addr = new InetSocketAddress(host, port);
        dc = DatagramChannel.open();
    }




    public void setName(City city){
        System.out.println("Введите имя города.");
        System.out.print("~ ");
        String name = in.nextLine();
        if (name.equals("") || name.equals("null")) {
            this.setName(city);
        } else city.setName(name);
    }

    /**
     * Sets coordinate x.
     *
     * @param coords the coords
     */
    public void setCoordinateX(Coordinates coords) {
        try {
            System.out.println("Введите координату x:");
            System.out.print("~ ");
            String x = in.nextLine();
            if (x.equals("") || x.equals(null)) this.setCoordinateX(coords);
            else {
                int xn = Integer.parseInt(x);
                if (xn > -400) {
                    coords.setX(xn);
                } else {
                    System.out.println("Минимальное значение поля: -400");
                    this.setCoordinateX(coords);
                }
                coords.setX(xn);
            }
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Значение должно быть типа:\"integer\".");
            this.setCoordinateX(coords);
        }
    }

    /**
     * Sets coordinate y.
     *
     * @param coords the coords
     */
    public void setCoordinateY(Coordinates coords) {
        try {
            System.out.println("Введите координату y:");
            System.out.print("~ ");
            String y = in.nextLine();
            if (y.equals("") || y.equals(null)) this.setCoordinateY(coords);
            else {
                int xn = Integer.parseInt(y);
                coords.setY(xn);
            }
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"integer\".");
            this.setCoordinateY(coords);
        }
    }

    /**
     * Sets area size.
     *
     * @param city the city
     */
    public void setAreaSize(City city) {
        try {
            System.out.println("Введите площадь города:");
            System.out.print("~ ");
            String x = in.nextLine();
            int xn = Integer.parseInt(x);
            if (xn<=0) {
                System.out.println("Значение поля должно быть больше 0,попробуйте ещё раз.");
                this.setAreaSize(city);
            }
            else city.setAreaSize(xn);
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"integer\".");
            this.setAreaSize(city);
        }
    }

    /**
     * Sets population size.
     *
     * @param city the city
     */
    public void setPopulation(City city) {
        try {
            System.out.println("Введите число жителей города:");
            System.out.print("~ ");
            String x = in.nextLine();
            int xn = Integer.parseInt(x);
            if (xn<=0) {
                System.out.println("Значение поля должно быть больше 0,попробуйте ещё раз.");
                this.setAreaSize(city);
            }
            else city.setAreaSize(xn);
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"integer\".");
            this.setAreaSize(city);
        }
    }

    /**
     * Sets meters above sea level.
     *
     * @param city the city
     */
    public void setMetersAboveSeaLevel(City city) {
        try {
            System.out.println("Введите уровень города над морем:");
            System.out.print("~ ");
            String x = in.nextLine();
            float xn = Float.parseFloat(x);
            city.setMetersAboveSeaLevel(xn);
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"float\".");
            this.setAreaSize(city);
        }
    }

    /**
     * Sets establishment date.
     *
     * @param city the city
     */
    public void setEstablishmentDate(City city) {
        try {
            System.out.println("Введите дату учреждения города:");
            System.out.print("~ ");
            String x = in.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            java.time.LocalDate xn = java.time.LocalDate.parse(x, formatter);
            city.setEstablishmentDate(xn);
        } catch (java.util.InputMismatchException | DateTimeParseException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"LocalDate(yyyy-mm-dd)\".");
            this.setEstablishmentDate(city);
        }
    }

    /**
     * Sets telephone code.
     *
     * @param city the city
     */
    public void setTelephoneCode(City city) {
        try {
            System.out.println("Введите телефонный код:");
            System.out.print("~ ");
            String x = in.nextLine();
            int xn = Integer.parseInt(x);
            if (xn < 10000) {
                city.setTelephoneCode(xn);
            } else {
                System.out.println("Максимальное значение поля: 10000");
                this.setTelephoneCode(city);
            }
            city.setTelephoneCode(xn);
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Значение должно быть типа:\"integer\".");
            this.setTelephoneCode(city);
        }
    }

    /**
     * Sets government.
     *
     * @param city the city
     */
    public void setGovernment(City city){
        try {
            System.out.println("Введите власти города:ARISTOCRACY или GERONTOCRACY или THEOCRACY");
            System.out.print("~ ");
            String x = in.nextLine();
            if (!x.equals("")){
                Government xn = Government.valueOf(x);
                city.setGovernment(xn);
            }
        } catch (java.util.InputMismatchException | IllegalArgumentException e) {
            System.out.println("Значение должно быть типа:\"Government\".");
            this.setGovernment(city);
        }
    }

    /**
     * Sets governor.
     *
     * @param city the city
     */
    public void setGovernor(City city){
        try {
            System.out.println("Введите рост губернатора:");
            System.out.print("~ ");
            String x = in.nextLine();
            if (!x.equals("")){
                int xn = Integer.parseInt(x);
                if (xn<=0) {
                    System.out.println("Значение поля должно быть больше 0,попробуйте ещё раз.");
                    this.setGovernor(city);
                }
                else {
                    Human h = new Human();
                    h.setHeight(xn);
                    city.setGovernor(h);
                }}
        } catch (java.util.InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"integer\".");
            this.setGovernor(city);
        }
    }

    public void setLogin(City city, String login){
        city.setLogin(login);
    }


    public void go() throws IOException, ClassNotFoundException, SQLException {
        String login = null, password, kek = "";
        while (!(kek.equalsIgnoreCase("Успешная авторизация") ||
                kek.equalsIgnoreCase("Успешное добавление пользователя и авторизация"))) {
            System.out.println("Введите логин, если у вас еще нет аккаунта, введите 'reg'");

            login = in.nextLine();
            Message log;
            if (login.equalsIgnoreCase("reg")) {
                System.out.println("Введите желаемый логин:");
                login = in.nextLine();
                System.out.println("Введите желаемый пароль:");
                password = in.nextLine();
                log = new Message("NewLogin", SHA512(password), login);
            } else {
                System.out.println("Введите пароль:");
                password = in.nextLine();
                log = new Message("Login", SHA512(password), login);
            }
            //System.out.println(SHA512(password));
            /*kek = send(log);
            System.out.println(kek);*/
            kek = send(log);
            System.out.println(kek);
        }
        System.out.println("Введите help, чтобы увидеть доступные команды");
        while (true) {
            Boolean f = true;
            String commandName = in.nextLine();
            String[] nameAndArgument = commandName.trim().split(" ");
            commandName = nameAndArgument[0];
            if (commandName.equals("add")){
                City city = new City();
                city.setCreationDate(java.time.LocalDate.now());
                this.setName(city);
                Coordinates coordinates = new Coordinates();
                this.setCoordinateX(coordinates);
                this.setCoordinateY(coordinates);
                city.setCoordinates(coordinates);
                this.setAreaSize(city);
                this.setPopulation(city);
                this.setMetersAboveSeaLevel(city);
                this.setEstablishmentDate(city);
                this.setTelephoneCode(city);
                this.setGovernment(city);
                this.setGovernor(city);
                //city.setLogin(login);
                this.setLogin(city, login);
                ms = new Message(commandName, city, login);
            }
            else if(commandName.equals("help") || commandName.equals("info") || commandName.equals("show") || commandName.equals("clear") ||
                    commandName.equals("exit") || commandName.equals("shuffle") || commandName.equals("reorder") || commandName.equals("average_of_telephone_code")){
                ms = new Message(commandName, login);
            }
            else if (nameAndArgument.length == 2){
                ms = new Message(commandName, nameAndArgument[1], login);
            }
            else{
                f = false;
                System.out.println("Неверный формат команды, введите \"help\", чтобы ознакомиться с форматами команд.");
            }
            if (f){
            System.out.println(send(ms));
            }

        }
    }
    public String send(Message ms) throws IOException, ClassNotFoundException {
        //System.out.println(ms.getCommand());
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ms);
        byte arr[] = baos.toByteArray();
        ByteBuffer buf = ByteBuffer.wrap(arr);
        dc.send(buf, addr);

        arr = new byte[4096];
        int len = arr.length;
        buf = ByteBuffer.wrap(arr);
        addr = dc.receive(buf);
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        ObjectInputStream oin = new ObjectInputStream(bis);
        ms = (Message) oin.readObject();
        //System.out.println(ms.getClass());
        if (ms.getCommand().equals("Завершаю работу.")){
            System.out.println("Завершаю работу.");
            System.exit(0);
        }
        else{
            return ms.getCommand();
            //System.out.println(ms.getCommand());
        }
        return null;
    }
    private String SHA512(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(in.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            return no.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
