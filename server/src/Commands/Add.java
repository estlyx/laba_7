package Commands;

import ForCity.City;
import ForCity.CityCollection;
import Tools.CityCreater;
import managers.DBManager;
import org.omg.CORBA.Any;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.valueOf;

/**
 * The type Add.
 */

public class Add extends AnyCommand {
    @Override
    public String execute(Object o) throws SQLException {
        City tmp = (City) o;
        ResultSet temp = DBManager.getInstance().getConnection().createStatement().executeQuery("SELECT nextval('seq')");
        temp.next();
        long x = temp.getLong("nextval");
        //System.out.println(x);
        tmp.setId((int) x);
        //return ("Город успешно добавлен.");

        try (Connection connect = DBManager.getInstance().getConnection(); Statement req = connect.createStatement()) {
            connect.setAutoCommit(false);
            //req.addBatch("DELETE from city");
                //System.out.println(tmp.getId());
            req.addBatch("insert into city VALUES (" + tmp.getId() + ",'" + tmp.getName() + "'," +
                        tmp.getCoordinates().getX() + ',' + tmp.getCoordinates().getY() + ",'" +
                        valueOf(tmp.getCreationDate()) + "'," + tmp.getAreaSize() + ',' + tmp.getPopulation() + ',' +
                        tmp.getMetersAboveSeaLevel() + ",'" + valueOf(tmp.getEstablishmentDate()) + "'," + tmp.getTelephoneCode() + ",'"
                        +valueOf(tmp.getGovernment()) + "'," + tmp.getGovernor().getHeight() + ",'" + tmp.getLogin() + "')");

            req.executeBatch();
            connect.commit();

            return "Коллекция успешно сохранена.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "не удалось сохранить коллекцию.";
        }
    }

    @Override
    public String getName() {
        return "add";
    }
}
