public class AutoPlayerClass1 implements StrategyInterface{
    @Override
    public void autoPlayer() {
        Aardvark aardvark = new Aardvark(1);
        aardvark.playerName = "Player Level - 01";
        aardvark.play();
    }
}
