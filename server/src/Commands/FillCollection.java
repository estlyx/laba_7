package Commands;

import ForCity.*;
import managers.DBManager;
import serv.ServConnection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Fill collection.
 *
 */
public class FillCollection extends AnyCommand {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String execute() throws IOException {


        try {
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
                ServConnection.citycollection.add(city);

            }
            return "Загружено " + ServConnection.citycollection.getSize() + " новых элементов.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Не удалось загрузить элементы";
        }
    }
    @Override
    public String getName(){return "FillCollection";}

}
