import javax.swing.*;

public class playernamelayoutO extends AbstractClassO{

    public playernamelayoutO(SubjectClassO subjectClassO){
        this.subjectClassO = subjectClassO;
        this.subjectClassO.attach(this);
    }

    @Override
    public void displayFrame() {
        label = new JLabel(MultiLinugualStringTable.getMessage(0));
        aardvarkMainGUI.add(label);
        text = new JTextField(25);
        aardvarkMainGUI.add(text);
        button = new JButton("PLAY GAME!");
        aardvarkMainGUI.add(button);
        button.addActionListener(this);
        aardvarkMainGUI.pack();
    }
}
