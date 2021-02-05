public class InvokerCommand {
    private Cmdinterface placeDomino;

    public InvokerCommand(CommandAardvarkPlaceDomino aardvarkPlaceDominoCommand){
        this.placeDomino = aardvarkPlaceDominoCommand;
    }

    public void pGame(){
        placeDomino.playGame();
    }

    public void uGame(){
        placeDomino.undoGame();
    }
}
