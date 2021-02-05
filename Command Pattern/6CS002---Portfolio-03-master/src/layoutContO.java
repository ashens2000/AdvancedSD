import javax.swing.*;
import java.awt.*;

public class layoutContO extends AbstractClassO{

    public layoutContO(SubjectClassO subjectClassO){
        this.subjectClassO = subjectClassO;
        this.subjectClassO.attach(this);
    }
    @Override
    public void displayFrame() {
        aardvarkMainGUI.setLayout(new GridLayout(0, 1));
        label = new JLabel(" Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe ");
        aardvarkMainGUI.add(label);
        label = new JLabel(" Version 1.0 (c), Kevan Buckley, 2010");
        aardvarkMainGUI.add(label);
        aardvarkMainGUI.pack();
        aardvarkMainGUI.setVisible(true);
    }
}
