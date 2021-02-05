public class AutoPlayerClass3 implements StrategyInterface{
    @Override
    public void autoPlayer() {
        Aardvark aardvark = new Aardvark(3);
        aardvark.playerName = "Player Level - 03";
        aardvark.play();


    }
}
