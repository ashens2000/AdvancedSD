public class mainStrategy {
    public static void main(String args[]){

        ContextStrategy contextStrategy = new ContextStrategy(new AutoPlayerClass1());
        contextStrategy.executeStrategy();


        contextStrategy = new ContextStrategy(new AutoPlayerClass2());
        contextStrategy.executeStrategy();

        contextStrategy = new ContextStrategy(new AutoPlayerClass3());
        contextStrategy.executeStrategy();

    }
}
