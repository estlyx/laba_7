package Commands;

import ForCity.City;
import ForCity.CityCollection;

/**
 * The type Average phone code.
 */
public class AveragePhoneCode extends CommandWithNoArg {
    @Override
    public String execute(Object o){
        int avg = 0;
        avg = CityCollection.getCollection().stream().map(City::getTelephoneCode).reduce(0, (x,y)->x+y);
        int size = CityCollection.getCollection().size();
        if (size >0){
            return "Средний код телефона в коллекции: " + String.valueOf(avg / size);
        }
        return "Коллекция пуста";
    }
    @Override
    public String getName(){return "average_of_telephone_code"; }
}
