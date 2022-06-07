package Commands;

import ForCity.CityCollection;

/**
 * The type Info.
 */
public class Info extends CommandWithNoArg {
    @Override
    public String execute(Object o){
        CityCollection collection = new CityCollection();
        return(collection.getInfo());
    }

    @Override
    public String getName(){return "info";}
}
