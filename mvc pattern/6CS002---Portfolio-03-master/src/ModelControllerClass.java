public class ModelControllerClass {
    private ModelClass modelclass;
    private ModelViewClass view;

    public ModelControllerClass(ModelClass model, ModelViewClass view){
        this.modelclass = model;
        this.view = view;
    }

    public void setPlayerName(String playerName){
        modelclass.setPN(playerName);
    }

    public String getPlayerName(){
        return modelclass.getPN();
    }

    public void GetView() {
        view.ModelGUI();
    }
}
