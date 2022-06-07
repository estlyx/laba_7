package Commands;

/**
 * The type Exit.
 */
public class Exit extends CommandWithNoArg {
    @Override
    public String execute(Object o){
        System.out.println("Завершаю работу.");
        System.exit(0);
        return null;
    }
    @Override
    public String getName(){return "exit";}
}
