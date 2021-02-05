public class MainCommand {
    public static void main(String args[]){
        Aardvark ardvark = new Aardvark();

        CommandAardvarkPlaceDomino aardvarkPlaceDominoCommand = new CommandAardvarkPlaceDomino(ardvark);

        InvokerCommand aardvarkCommandInvoker = new InvokerCommand(aardvarkPlaceDominoCommand);
        aardvarkCommandInvoker.pGame();

        System.out.println("Do you need to undo(Y : yes , N : no) :");
        String undo = IOLibrary.getString();
        if(undo.equalsIgnoreCase("Y")) {
            aardvarkCommandInvoker.uGame();
        }
    }
}
