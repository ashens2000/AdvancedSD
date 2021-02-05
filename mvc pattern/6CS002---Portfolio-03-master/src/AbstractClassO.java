import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractClassO extends JFrame implements ActionListener {

    static JTextField text;
    static JFrame aardvarkMainGUI = new JFrame("Aardvark");
    static JFrame aardvarkmainMenuGUI = new JFrame("Main Menu");
    static JFrame aardvarkSelectDifficulty = new JFrame("Difficulty Menu");
    static JButton button;
    static JLabel label;
    static ModelClass model = new ModelClass();
    static ModelViewClass view = new ModelViewClass();
    static ModelControllerClass Modelcontroller = new ModelControllerClass(model, view);
    static protected Aardvark aardvark;
    static int level;

    protected SubjectClassO subjectClassO;

    public abstract void displayFrame();

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();  
        switch(s)
        {
        	case "Ok":
        		 Modelcontroller.setPlayerName(text.getText());
                 aardvark.playerName = model.getPN();
                 aardvarkMainGUI.dispose();
                 aardvarkmainMenuGUI.setVisible(true);
                 break;
        	case "Play":
        		aardvarkSelectDifficulty.setVisible(true);
        		break;
        	case "View Rules":
                aardvark.viewRules();
                break;
        	case "View High Scores":
                aardvark.viewHighScores();
                break;
                
        	case "Quit":
                aardvark.quit();
                break;
        	case "Simples":
        		level = 1;
                aardvark.play();
                break;
        	case "Not-so-simples":
        		level = 2;
                aardvark.play();
                break;
        	case "Super-duper-shuffled":
        		level = 3;
                aardvark.play();
                break;
           
        	
        		
        		
        	
        }
    }
}
