package Commands;

import managers.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewLogin extends AnyCommand {
    public NewLogin() {
    }

    @Override
    public String execute(Object log, Object pass) {
        try {
            ResultSet tmp = DBManager.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM users where login=" + "'" + log + "'");
            if (tmp.next())
                return "Пользователь с данным логином уже существует.";
            else {
                DBManager.getInstance().getConnection().createStatement().execute("insert into users values (" + "'" + log + "' ,'" + pass + "')");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Успешное добавление пользователя и авторизация";
    }
    @Override
    public String getName() {
        return "newlogin";
    }
}
