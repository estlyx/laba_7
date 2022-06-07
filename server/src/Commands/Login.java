package Commands;

import managers.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends AnyCommand{
    @Override
    public synchronized String execute(Object log, Object pass) {
        try {
            ResultSet tmp = DBManager.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT * FROM users where login=" + "'" + log + "'");
            if (!tmp.next() || !tmp.getString("password").equals(pass))
                return "Неверный логин и/или пароль.";
            return "Успешная авторизация";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getName() {
        return "login";
    }
}
