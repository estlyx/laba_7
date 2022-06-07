package Commands;

import ForCity.City;
import ForCity.CityCollection;

import java.util.stream.Stream;

/**
 * The type Show.
 */
public class Show extends CommandWithNoArg {
    @Override
    public String execute(Object o) {
        CityCollection collection = new CityCollection();
        if (collection.getSize() == 0) return ("Коллекция пустая.");
        else {
            StringBuilder result=new StringBuilder("---------------------------\n");
            collection.getCollection().stream().forEachOrdered(x -> result.append(x.getInfo()).append("\n---------------------------\n"));
            return result.toString();
        }
    }

    @Override
    public String getName() {
        return "show";
    }
}
