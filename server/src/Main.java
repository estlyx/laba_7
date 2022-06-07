import serv.ServConnection;

import java.io.IOException;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InterruptedException {
        ServConnection serv = new ServConnection(1338);
        serv.go();

    }
}