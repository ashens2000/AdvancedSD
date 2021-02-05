import javax.swing.*;
import java.awt.*;

public class menuClass extends AbstractClassO{

    public menuClass(SubjectClassO subjectClassO){
        this.subjectClassO = subjectClassO;
        this.subjectClassO.attach(this);
    }

    @Override
    public void displayFrame() {
        aardvarkmainMenuGUI.setLayout(new GridLayout(0, 1));
        label = new JLabel(MultiLinugualStringTable.getMessage(1) + ". " + MultiLinugualStringTable.getMessage(2));
        aardvarkmainMenuGUI.add(label);
        button = new JButton("Play Game");
        aardvarkmainMenuGUI.add(button);
        button.addActionListener(this);
        button = new JButton("View High Scores");
        aardvarkmainMenuGUI.add(button);
        button.addActionListener(this);
        button = new JButton("View Rules");
        aardvarkmainMenuGUI.add(button);
        button.addActionListener(this);
        button = new JButton("Quit");
        aardvarkmainMenuGUI.add(button);
        button.addActionListener(this);
        aardvarkmainMenuGUI.pack();
    }
}
