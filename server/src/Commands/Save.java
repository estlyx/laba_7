package Commands;

import ForCity.City;
import ForCity.CityCollection;
import managers.DBManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.lang.String.valueOf;

/**
 * The type Save.
 */
public class Save extends CommandWithNoArg {
    @Override
    public String execute(){
        System.out.println("hui");
        try (Connection connect = DBManager.getInstance().getConnection();
             Statement req = connect.createStatement()) {
            connect.setAutoCommit(false);
            req.addBatch("DELETE from city");
            for (City tmp : CityCollection.getCollection()) {
                //System.out.println(tmp.getId());
                req.addBatch("insert into city VALUES (" + tmp.getId() + ",'" + tmp.getName() + "'," +
                        tmp.getCoordinates().getX() + ',' + tmp.getCoordinates().getY() + ",'" +
                        valueOf(tmp.getCreationDate()) + "'," + tmp.getAreaSize() + ',' + tmp.getPopulation() + ',' +
                        tmp.getMetersAboveSeaLevel() + ",'" + valueOf(tmp.getEstablishmentDate()) + "'," + tmp.getTelephoneCode() + ",'"
                        +valueOf(tmp.getGovernment()) + "'," + tmp.getGovernor().getHeight() + ",'" + tmp.getLogin() + "')");
            }
            req.executeBatch();
            connect.commit();

            return "Коллекция успешно сохранена.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "не удалось сохранить коллекцию.";
        }

    }

    @Override
    public String getName(){return "save";}
}
