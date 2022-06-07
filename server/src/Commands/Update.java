package Commands;

import ForCity.City;
import ForCity.CityCollection;
import Tools.CityCreater;

/**
 * The type Update.
 */
public class Update extends AnyCommand {
    /**
     * @param o the id
     */
    @Override
    public String execute(Object o, Object o2) {
        CityCollection collection = new CityCollection();
        try {

            int id=Integer.parseInt((String) o);
            City updCity = collection.getCollection().stream().filter(x->x.getId()==id).findFirst().get();
            if(updCity.getLogin().equals(o2)){
                collection.add((new CityCreater()).create( (String) o));
                CityCollection.getCollection().remove(updCity);
                return ("Город [id:" + o + "] успешно обновлен.");
            } else return ("Элемента с таким id не существует. или увас нет прав для внесения изменений");
        } catch (Exception e) {
            return ("Элемента с таким id не существует.");
        }
    }

    @Override
    public String getName() {
        return "update";
    }
}
