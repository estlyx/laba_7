package Commands;

import java.io.IOException;
import java.sql.SQLException;

public abstract class AnyCommand {
    public String execute(Object o) throws IOException, SQLException {
        return null;
    }
    public String execute() throws IOException {
        return null;
    }
    public String execute(Object o1, Object o2){
        return null;
    }

    public String getName() {
        return null;
    }
}
