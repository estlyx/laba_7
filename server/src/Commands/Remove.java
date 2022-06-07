package Commands;

import ForCity.City;
import ForCity.CityCollection;

import java.util.Iterator;

/**
 * The type Remove.
 */
public class Remove extends AnyCommand {
    @Override
    public String execute(Object o, Object o2){
        try{
            CityCollection collection = new CityCollection();
            if (collection.getSize()==0) return("Коллекция пуста");
            else{
                boolean isRemoved = false;
                int id = 0;
                try {
                    id = Integer.parseInt((String) o);
                } catch (NumberFormatException exp) {
                    return "Неверно указан аргумент.";
                }

                Iterator<City> it = (Iterator<City>) CityCollection.getCollection().iterator();
                while (it.hasNext()) {
                    City city = (City) it.next();
                    int Id = (int) city.getId();
                    if (id == Id && city.getLogin().equals(o2)) {
                        it.remove();
                        isRemoved = true;
                        return ("Город успешно удален.");
                    }
                }
                if (!isRemoved) return ("Элемента с таким id не существует или у вас нет прав для внесения изменений.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName(){return "remove_by_id";}
}
