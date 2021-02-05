public class MVCMain
{
    public static void main(String args[]){
        ModelClass model = new ModelClass();
        ModelViewClass view = new ModelViewClass();
        ModelControllerClass controller = new ModelControllerClass(model, view);
        controller.GetView();
    }
}
