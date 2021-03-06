import java.awt.*;

import javax.swing.*;

public class PictureFrame {
  public int[] reroll = null;
  AardvarkMethod master = null;

  class DominoPanel extends JPanel {
    private static final long serialVersionUID = 4190229282411119364L;

    public void drawGrid(Graphics g) {
      for (int are = 0; are < 7; are++) {
        for (int see = 0; see < 8; see++) {
          drawDigitGivenCentre(g, 30 + see * 20, 30 + are * 20, 20,
              master.grid[are][see]);
        }
      }
    }

    public void drawGridLines(Graphics g) {
      g.setColor(Color.LIGHT_GRAY);
      for (int are = 0; are <= 7; are++) {
        g.drawLine(20, 20 + are * 20, 180, 20 + are * 20);
      }
      for (int see = 0; see <= 8; see++) {
        g.drawLine(20 + see * 20, 20, 20 + see * 20, 160);
      }
    }

    public void drawHeadings(Graphics g) {
      for (int are = 0; are < 7; are++) {
        fillDigitGivenCentre(g, 10, 30 + are * 20, 20, are+1);
      }

      for (int see = 0; see < 8; see++) {
        fillDigitGivenCentre(g, 30 + see * 20, 10, 20, see+1);
      }
    }

    public void drawDomino(Graphics g, Domino d) {
      if (d.placed) {
    	  int cons1=20;
          int cons2=30;
    	  int y = Math.min(d.lengthofy, d.heightofy);
        int x = Math.min(d.lengthofx, d.heightofx);
        int w = Math.abs(d.lengthofx - d.heightofx) + 1;
        int h = Math.abs(d.lengthofy - d.heightofy) + 1;
        int x_cor=cons1 + x * cons1;
        int y_cor=cons1 + y * cons1;
        int width=w * cons1;
        int height=h * cons1;
        
        g.setColor(Color.WHITE);
        g.fillRect(x_cor,y_cor , width, height);
        g.setColor(Color.RED);
        g.drawRect(x_cor, y_cor, width, height);
        drawDigitGivenCentre(g, cons2 + d.heightofx * cons1, cons2 + d.heightofy * cons1, cons1, d.high,
            Color.BLUE);
        drawDigitGivenCentre(g, cons2 + d.lengthofx * cons1, cons2 + d.lengthofy * cons1, cons1, d.low,
            Color.BLUE);
      }
    }

    void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
     final  int radius = diameter / 2;
      g.setColor(Color.BLACK);
      // g.drawOval(x - radius, y - radius, diameter, diameter);
      FontMetrics fm = g.getFontMetrics();
      String txt = Integer.toString(n);
      g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
    }

    void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n,
        Color c) {
     final int radius = diameter / 2;
      g.setColor(c);
      // g.drawOval(x - radius, y - radius, diameter, diameter);
      FontMetrics fm = g.getFontMetrics();
      String txt = Integer.toString(n);
      g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
    }

    void fillDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
     final int radius = diameter / 2;
      g.setColor(Color.GREEN);
      g.fillOval(x - radius, y - radius, diameter, diameter);
      g.setColor(Color.BLACK);
      g.drawOval(x - radius, y - radius, diameter, diameter);
      FontMetrics fm = g.getFontMetrics();
      String txt = Integer.toString(n);
      g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
    }

    protected void paintComponent(Graphics g) 
    {
      g.setColor(Color.YELLOW);
      g.fillRect(0, 0, getWidth(), getHeight());

      // numbaz(g);
      //
      // if (master!=null && master.orig != null) {
      // drawRoll(g, master.orig);
      // }
      // if (reroll != null) {
      // drawReroll(g, reroll);
      // }
      //
      // drawGrid(g);
      if (master.mode == 1) {
        drawGridLines(g);
        drawHeadings(g);
        drawGrid(g);
        master.drawGuesses(g);
      }
      if (master.mode == 0) {
        drawGridLines(g);
        drawHeadings(g);
        drawGrid(g);
        master.drawDominoes(g);
      }
    }

    public Dimension getPreferredSize() {
      return new Dimension(202, 182);
    }
  }

  public DominoPanel dp;

  public void PictureFrame(AardvarkMethod sf) {
    master = sf;
    if (dp == null) {
      JFrame f = new JFrame("Abominodo");
      dp = new DominoPanel();
      f.setContentPane(dp);
      f.pack();
      f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      f.setVisible(true);
    }//word
  }

  public void reset() {
    // TODO Auto-generated method stub

  }

}
