public class ContextStrategy {
    public StrategyInterface strategyInterface;

    public ContextStrategy(StrategyInterface strategyInterface){
        this.strategyInterface = strategyInterface;
    }

    public void executeStrategy(){
        strategyInterface.autoPlayer();
    }
}
