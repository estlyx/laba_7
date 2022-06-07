package Commands;

import ForCity.CityCollection;

import java.util.Collections;

/**
 * The type Reorder.
 */
public class Reorder extends CommandWithNoArg {
    @Override
    public String execute (Object o){
        try{
            CityCollection collection = new CityCollection();
            int sz = collection.getSize();
            int j = sz - 1;
            for (int i = 0; i < (int) sz / 2; i++){
                Collections.swap(CityCollection.getCollection(), i ,j);
                j--;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("");
        }
        //Collections.reverse(CityCollection.getCollection());
        return "Коллекция отсортирована в обратном порядке";
    }

    @Override
    public String getName(){return "reorder";}
}
