public class AutoPlayerClass2 implements StrategyInterface{
    @Override
    public void autoPlayer() {
        Aardvark aardvark = new Aardvark(2);
        aardvark.playerName = "Player Level - 02";
        aardvark.play();
    }
}
