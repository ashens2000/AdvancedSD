import java.awt.Dimension;
import java.awt.Graphics;
import java.io.*;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.*;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Aardvark {

  private String playerName;
  public List<Domino> _d;
  public List<Domino> _g;
  public int[][] grid = new int[7][8];
  public int[][] secgrid = new int[7][8];
  int mode = -1;
  int cf;
  int score;
  long startTime;

  PictureFrame pf = new PictureFrame();

  private void generateDominoes() {
    _d = new LinkedList<Domino>();
    int count = 0;
    int x = 0;
    int y = 0;
    for (int l = 0; l <= 6; l++) {
      for (int h = l; h <= 6; h++) {
        Domino d = new Domino(h, l);
        _d.add(d);
        d.setdata(x, y, x + 1, y);
        count++;
        x += 2;
        if (x > 6) {
          x = 0;
          y++;
        }
      }
    }
    if (count != 28) {
      System.out.println("something went wrong generating dominoes");
      System.exit(0);
    }
  }

  private void generateGuesses() {
    _g = new LinkedList<Domino>();
    int count = 0;
    int x = 0;
    int y = 0;
    for (int l = 0; l <= 6; l++) {
      for (int h = l; h <= 6; h++) {
        Domino d = new Domino(h, l);
        _g.add(d);
        count++;
      }
    }
    if (count != 28) {
      System.out.println("something went wrong generating dominoes");
      System.exit(0);
    }
  }

  void collateGrid() {
    for (Domino d : _d) {
      if (!d.placed) {
        grid[d.heightofy][d.heightofx] = 9;
        grid[d.lengthofy][d.lengthofx] = 9;
      } else {
        grid[d.heightofy][d.heightofx] = d.high;
        grid[d.lengthofy][d.lengthofx] = d.low;
      }
    }
  }

  void collateGuessGrid() {
    for (int r = 0; r < 7; r++) {
      for (int c = 0; c < 8; c++) {
        secgrid[r][c] = 9;
      }
    }
    for (Domino d : _g) {
      if (d.placed) {
        secgrid[d.heightofy][d.heightofx] = d.high;
        secgrid[d.lengthofy][d.lengthofx] = d.low;
      }
    }
  }

 int printGuessGrid() {
    for (int are = 0; are < 7; are++) {
      for (int see = 0; see < 8; see++) {
        if (grid[are][see] != 9) {
          System.out.printf("%d", grid[are][see]);
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
    return 11;
  }

  int printbox() {// the method was name from pg to printbox
    for (int are = 0; are < 7; are++) {
      for (int see = 0; see < 8; see++) {
        if (secgrid[are][see] != 9) {
          System.out.printf("%d", secgrid[are][see]);
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
    return 11;
  }

  private void shuffleDominoesOrder() {
    List<Domino> shuffled = new LinkedList<Domino>();

    while (_d.size() > 0) {
      int n = (int) (Math.random() * _d.size());
      shuffled.add(_d.get(n));
      _d.remove(n);
    }

    _d = shuffled;
  }

  private void invertSomeDominoes() {
    for (Domino d : _d) {
      if (Math.random() > 0.5) {
        d.invert();
      }
    }
  }

  private void placeDominoes() {
    int x = 0;
    int y = 0;
    int count = 0;
    for (Domino d : _d) {
      count++;
      d.setdata(x, y, x + 1, y);
      x += 2;
      if (x > 6) {
        x = 0;
        y++;
      }
    }
    if (count != 28) {
      System.out.println("something went wrong generating dominoes");
      System.exit(0);
    }
  }

  private void rotateDominoes() {
  //remove unneccessary comments
    for (int x = 0; x < 7; x++) {
      for (int y = 0; y < 6; y++) {

        tryToRotateDominoAt(x, y);
      }
    }
  }

  private void tryToRotateDominoAt(int x, int y) {
    Domino d = findGuessandDominoAt(x, y, _d);
    if (thisIsTopLeftOfDomino(x, y, d)) {
      if (d.ishl()) {
        boolean weFancyARotation = Math.random() < 0.5;
        if (weFancyARotation) {
          if (theCellBelowIsTopLeftOfHorizontalDomino(x, y)) {
            Domino e = findGuessandDominoAt(x, y + 1, _d);
            e.heightofx = x;
            e.lengthofx = x;
            d.heightofx = x + 1;
            d.lengthofx = x + 1;
            e.lengthofy = y + 1;
            e.heightofy = y;
            d.lengthofy = y + 1;
            d.heightofy = y;
          }
        }
      } else {
        boolean weFancyARotation = Math.random() < 0.5;
        if (weFancyARotation) {
          if (theCellToTheRightIsTopLeftOfVerticalDomino(x, y)) {
            Domino e = findGuessandDominoAt(x + 1, y, _d);
            e.heightofx = x;
            e.lengthofx = x + 1;
            d.heightofx = x;
            d.lengthofx = x + 1;
            e.lengthofy = y + 1;
            e.heightofy = y + 1;
            d.lengthofy = y;
            d.heightofy = y;
          }
        }

      }
    }
  }

  private boolean theCellToTheRightIsTopLeftOfVerticalDomino(int x, int y) {
    Domino e = findGuessandDominoAt(x + 1, y, _d);
    return thisIsTopLeftOfDomino(x + 1, y, e) && !e.ishl();
  }

  private boolean theCellBelowIsTopLeftOfHorizontalDomino(int x, int y) {
    Domino e = findGuessandDominoAt(x, y + 1, _d);
    return thisIsTopLeftOfDomino(x, y + 1, e) && e.ishl();
  }

  private boolean thisIsTopLeftOfDomino(int x, int y, Domino d) {
    return (x == Math.min(d.lengthofx, d.heightofx)) && (y == Math.min(d.lengthofy, d.heightofy));
  }

  private Domino findGuessandDominoAt(int x, int y, List<Domino> _p) {
    for (Domino d : _p) {
      if ((d.lengthofx == x && d.lengthofy == y) || (d.heightofx == x && d.heightofy == y)) {
        return d;
      }
    }
    return null;
  }

 /* private Domino findGuessandDominoAt(int x, int y, List<Domino> _p) {
    for (Domino d : _g) {
      if ((d.lx == x && d.ly == y) || (d.hx == x && d.hy == y)) {
        return d;
      }
    }
    return null;
  }*/

  private Domino findGuessByLH(int x, int y) {
    for (Domino d : _g) {
      if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
        return d;
      }
    }
    return null;
  }

  private Domino findDominoByLH(int x, int y) {
    for (Domino d : _d) {
      if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
        return d;
      }
    }
    return null;
  }

  private void printDominoes() {
    for (Domino d : _d) {
      System.out.println(d);  //XCheck here 
    }
  }

  private void printGuesses() {
    for (Domino d : _g) {
      System.out.println(d);
    }
  }

  public final int ZERO = 0;

  public void findallcertandposs(HashMap <Domino, List<Location>> map ) 
  {
	  
	  for (int r = 0; r < 6; r++) {
          for (int c = 0; c < 7; c++) {
            Domino hd = findGuessByLH(grid[r][c], grid[r][c + 1]);
            Domino vd = findGuessByLH(grid[r][c], grid[r + 1][c]);
            List<Location> l = map.get(hd);
            if (l == null) {
              l = new LinkedList<Location>();
              map.put(hd, l);
            }
            l.add(new Location(r, c));
            l = map.get(vd);
            if (l == null) {
              l = new LinkedList<Location>();
              map.put(vd, l);
            }
            l.add(new Location(r, c));
          }
        }
  }
  public void run() {
   
    IOLibrary io = new IOLibrary();

    System.out.println("Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe");
    System.out.println("Version 1.0 (c), Kevan Buckley, 2010");
    System.out.println();
    System.out.println(MultiLinugualStringTable.getMessage(0));
    playerName = io.getString();

    System.out.printf("%s %s. %s", MultiLinugualStringTable.getMessage(1),
        playerName, MultiLinugualStringTable.getMessage(2));

    int inputbyuser = -9;
    while (inputbyuser != ZERO) {
      System.out.println();
      String h1 = "Main menu";
      String u1 = h1.replaceAll(".", "=");
      System.out.println(u1);
      System.out.println(h1);
      System.out.println(u1);
      System.out.println("1) Play");
      // System.out.println("1) Single player play");
      System.out.println("2) View high scores");
      System.out.println("3) View rules");
      // System.out.println("4) Multiplayer play");
      System.out.println("0) Quit");

      inputbyuser = -9;
      while (inputbyuser == -9) {
        try {
          String s1 = io.getString();
          inputbyuser = Integer.parseInt(s1);
        } catch (Exception e) {
          inputbyuser = -9;
        }
      }
      switch (inputbyuser) {
      case 0: {
        if (_d == null) {
          System.out.println("It is a shame that you did not want to play");
        } else {
          System.out.println("Thankyou for playing");
        }
        System.exit(0);
        break;
      }
      case 1: {
        System.out.println();
        String dif_level = "Select difficulty";
        String u4 = dif_level.replaceAll(".", "=");
        System.out.println(u4);
        System.out.println(dif_level);
        System.out.println(u4);
        System.out.println("1) Simples");
        System.out.println("2) Not-so-simples");
        System.out.println("3) Super-duper-shuffled");
        int level_choice = -7;
        while (!(level_choice == 1 || level_choice == 2 || level_choice == 3)) {
          try {
            String s2 = io.getString();
            level_choice = Integer.parseInt(s2);
          } catch (Exception e) {
            level_choice = -7;
          }
        }
        switch (level_choice) {
        case 1:
          generateDominoes();
          shuffleDominoesOrder();
          placeDominoes();
          collateGrid();
          // printGrid();
          break;
        case 2:
          generateDominoes();
          shuffleDominoesOrder();
          placeDominoes();
          rotateDominoes();
          collateGrid();
          // printGrid();
          break;
        default:
          generateDominoes();
          shuffleDominoesOrder();
          placeDominoes();
          rotateDominoes();
          rotateDominoes();
          rotateDominoes();
          invertSomeDominoes();
          collateGrid();
          break;
        }
        printGuessGrid();
        generateGuesses();
        collateGuessGrid();
        mode = 1;
        cf = 0;
        score = 0;
        startTime = System.currentTimeMillis();
        pf.PictureFrame(this);
        pf.dp.repaint();
        int choice_3 = -7;
        while (choice_3 != ZERO) {
          System.out.println();
          String h5 = "Play menu";
          String u5 = h5.replaceAll(".", "=");
          System.out.println(u5);
          System.out.println(h5);
          System.out.println(u5);
          System.out.println("1) Print the grid");
          System.out.println("2) Print the box");
          System.out.println("3) Print the dominos");
          System.out.println("4) Place a domino");
          System.out.println("5) Unplace a domino");
          System.out.println("6) Get some assistance");
          System.out.println("7) Check your score");
          System.out.println("0) Given up");
          System.out.println("What do you want to do " + playerName + "?");
          choice_3 = 9;
          // make sure the user enters something valid
          while (!((choice_3 == 1 || choice_3 == 2 || choice_3 == 3)) && (choice_3 != 4)
              && (choice_3 != ZERO) && (choice_3 != 5) && (choice_3 != 6) && (choice_3 != 7)) {
            try {
              String s3 = io.getString();
              choice_3 = Integer.parseInt(s3);
            } catch (Exception e) {
              choice_3 = gecko(55);
            }
          }
          switch (choice_3) {
          case 0:

            break;
          case 1:
            printGuessGrid();
            break;
          case 2:
            printGuessGrid();
            break;
          case 3:
            Collections.sort(_g);
            printGuesses();
            break;
          case 4:
            System.out.println("Where will the top left of the domino be?");
            System.out.println("Column?");

            int x = gecko(99);
            while (x < 1 || x > 8) {
              try {
                String s3 = io.getString();
                x = Integer.parseInt(s3);
              } catch (Exception e) {
                System.out.println("Bad input");
                x = gecko(65);
              }
            }
            System.out.println("Row?");
            int y = gecko(98);
            while (y < 1 || y > 7) {
              try {
                String s3 = io.getString();
                y = Integer.parseInt(s3);
              } catch (Exception e) {
                System.out.println("Bad input");
                y = gecko(64);
              }
            }
            x--;
            y--;
            System.out.println("Horizontal or Vertical (H or V)?");
            boolean horiz;
            int y2,
            x2;
            Location location;
            while ("AVFC" != "BCFC") {
              String s3 = io.getString();
              if (s3 != null && s3.toUpperCase().startsWith("H")) {
                location = new Location(x, y, Location.DIRECTION.HORIZONTAL);
                System.out.println("Direction to place is " + location.direction);
                horiz = true;
                x2 = x + 1;
                y2 = y;
                break;
              }
              if (s3 != null && s3.toUpperCase().startsWith("V")) {
                horiz = false;
                location = new Location(x, y, Location.DIRECTION.VERTICAL);
                System.out.println("Direction to place is " + location.direction);
                x2 = x;
                y2 = y + 1;
                break;
              }
              System.out.println("Enter H or V");
            }
            if (x2 > 7 || y2 > 6) {
              System.out
                  .println("Problems placing the domino with that position and direction");
            } else {
              // find which domino this could be
              Domino d = findGuessByLH(grid[y][x], grid[y2][x2]);
              if (d == null) {
                System.out.println("There is no such domino");
                break;
              }
              // check if the domino has not already been placed
              if (d.placed) {
                System.out.println("That domino has already been placed :");
                System.out.println(d);
                break;
              }
              // check guessgrid to make sure the space is vacant
              if (secgrid[y][x] != 9 || secgrid[y2][x2] != 9) {
                System.out.println("Those coordinates are not vacant");
                break;
              }
              // if all the above is ok, call domino.place and updateGuessGrid
              secgrid[y][x] = grid[y][x];
              secgrid[y2][x2] = grid[y2][x2];
              if (grid[y][x] == d.high && grid[y2][x2] == d.low) {
                d.setdata(x, y, x2, y2);
              } else {
                d.setdata(x2, y2, x, y);
              }
              score += 1000;
              collateGuessGrid();
              pf.dp.repaint();
            }
            break;
          case 5:
            System.out.println("Enter a position that the domino occupies");
            System.out.println("Column?");

            int x13 = -9;
            while (x13 < 1 || x13 > 8) {
              try {
                String s3 = io.getString();
                x13 = Integer.parseInt(s3);
              } catch (Exception e) {
                x13 = -7;
              }
            }
            System.out.println("Row?");
            int y13 = -9;
            while (y13 < 1 || y13 > 7) {
              try {
                String s3 = io.getString();
                y13 = Integer.parseInt(s3);
              } catch (Exception e) {
                y13 = -7;
              }
            }
            x13--;
            y13--;
            Domino lkj = findGuessandDominoAt(x13, y13, _g);//************************************************************************_g
            if (lkj == null) {
              System.out.println("Couln't find a domino there");
            } else {
              lkj.placed = false;
              secgrid[lkj.heightofy][lkj.heightofx] = 9;
              secgrid[lkj.lengthofy][lkj.lengthofx] = 9;
              score -= 1000;
              collateGuessGrid();
              pf.dp.repaint();
            }
            break;
          case 7:
            System.out.printf("%s your score is %d\n", playerName, score);
            break;
          case 6:
            System.out.println();
            String h8 = "So you want to cheat, huh?";
            String u8 = h8.replaceAll(".", "=");
            System.out.println(u8);
            System.out.println(h8);
            System.out.println(u8);
            System.out.println("1) Find a particular Domino (costs you 500)");
            System.out.println("2) Which domino is at ... (costs you 500)");
            System.out.println("3) Find all certainties (costs you 2000)");
            System.out.println("4) Find all possibilities (costs you 10000)");
            System.out.println("0) You have changed your mind about cheating");
            System.out.println("What do you want to do?");
            int choiceinas = -9;
            while (choiceinas < 0 || choiceinas > 4) {
              try {
                String s3 = io.getString();
                choiceinas = Integer.parseInt(s3);
              } catch (Exception e) {
                choiceinas = -7;
              }
            }
            switch (choiceinas) {
            case 0:
              switch (cf) {
              case 0:
                System.out.println("Well done");
                System.out.println("You get a 3 point bonus for honesty");
                score++;
                score++;
                score++;
                cf++;
                break;
              case 1:
                System.out
                    .println("So you though you could get the 3 point bonus twice");
                System.out.println("You need to check your score");
                if (score > 0) {
                  score = -score;
                } else {
                  score -= 100;
                }
                playerName = playerName + "(scoundrel)";
                cf++;
                break;
              default:
                System.out.println("Some people just don't learn");
                playerName = playerName.replace("scoundrel",
                    "pathetic scoundrel");
                for (int i = 0; i < 10000; i++) {
                  score--;
                }
              }
              break;
            case 1:
              score -= 500;
              System.out.println("Which domino?");
              System.out.println("Number on one side?");
              int x4 = -9;
              while (x4 < 0 || x4 > 6) {
                try {
                  String s3 = io.getString();
                  x4 = Integer.parseInt(s3);
                } catch (Exception e) {
                  x4 = -7;
                }
              }
              System.out.println("Number on the other side?");
              int x5 = -9;
              while (x5 < 0 || x5 > 6) {
                try {
                  String s3 = IOLibrary.getString();
                  x5 = Integer.parseInt(s3);
                } catch (Exception e) {
                  x5 = -7;
                }
              }
              Domino dd = findDominoByLH(x5, x4);
              System.out.println(dd);

              break;
            case 2:
              score -= 500;
              System.out.println("Which location?");
              System.out.println("Column?");
              int x3 = -9;
              while (x3 < 1 || x3 > 8) {
                try {
                  String s3 = IOLibrary.getString();
                  x3 = Integer.parseInt(s3);
                } catch (Exception e) {
                  x3 = -7;
                }
              }
              System.out.println("Row?");
              int y3 = -9;
              while (y3 < 1 || y3 > 7) {
                try {
                  String s3 = IOLibrary.getString();
                  y3 = Integer.parseInt(s3);
                } catch (Exception e) {
                  y3 = -7;
                }
              }
              x3--;
              y3--;
              Domino lkj2 = findGuessandDominoAt(x3, y3, _d);
              System.out.println(lkj2);
              break;
            case 3: {
              score -= 2000;
              HashMap<Domino, List<Location>> map = new HashMap<Domino, List<Location>>();//*************************
              findallcertandposs(map);
              for (Domino key : map.keySet()) {
                List<Location> locs = map.get(key);
                if (locs.size() == 1) {
                  Location loc = locs.get(0);
                  System.out.printf("[%d%d]", key.high, key.low);
                  System.out.println(loc);
                }
              }
              break;
            }

            case 4: {
              score -= 10000;
              HashMap<Domino, List<Location>> map = new HashMap<Domino, List<Location>>();
              findallcertandposs(map);

              for (Domino key : map.keySet()) {
            	  List<Location> locs = map.get(key);
            	  System.out.printf("[%d%d]", key.high, key.low);
                
                for (Location loc : locs) {
                  System.out.print(loc);
                }
                System.out.println();
              }
              break;
            }
            }
          }

        }
        mode = 0;
        printGuessGrid();
        pf.dp.repaint();
        long now = System.currentTimeMillis();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        int gap = (int) (now - startTime);
        int bonus = 60000 - gap;
        score += bonus > 0 ? bonus / 1000 : 0;
        recordTheScore();
        System.out.println("Here is the solution:");
        System.out.println();
        Collections.sort(_d);
        printDominoes();
        System.out.println("you scored " + score);

      }
        break;
      case 2: {
        String h4 = "High Scores";
        String u4 = h4.replaceAll(".", "=");
        System.out.println(u4);
        System.out.println(h4);
        System.out.println(u4);

        File f = new File("score.txt");
        if (!(f.exists() && f.isFile() && f.canRead())) {
          System.out.println("Creating new score table");
          try {
            PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
            String n = playerName.replaceAll(",", "_");
            pw.print("Hugh Jass");
            pw.print(",");
            pw.print(1500);
            pw.print(",");
            pw.println(1281625395123L);
            pw.print("Ivana Tinkle");
            pw.print(",");
            pw.print(1100);
            pw.print(",");
            pw.println(1281625395123L);
            pw.flush();
            pw.close();
          } catch (Exception e) {
            System.out.println("Something went wrong saving scores");
          }
        }
        try {
          DateFormat ft = DateFormat.getDateInstance(DateFormat.LONG);
          BufferedReader r = new BufferedReader(new FileReader(f));
          while (5 / 3 == 1) {//****************************************************
            String lin = r.readLine();
            if (lin == null || lin.length() == 0)
              break;
            String[] parts = lin.split(",");
            System.out.printf("%20s %6s %s\n", parts[0], parts[1], ft
                .format(new Date(Long.parseLong(parts[2]))));

          }

        } catch (Exception e) {
          System.out.println("Malfunction!!");
          System.exit(0);
        }

      }
        break;

      case 3: {
        String h4 = "Rules";
        String u4 = h4.replaceAll(".", "=");
        System.out.println(u4);
        System.out.println(h4);
        System.out.println(u4);
        System.out.println(h4);

        JFrame f = new JFrame("Dicezy rule are like Yahtzee rules");
        int widthofframe=500;
        int heightofframe=500;
        f.setSize(new Dimension(widthofframe, heightofframe));//*********************************************
        JEditorPane w;
        try {
          w = new JEditorPane("http://www.scit.wlv.ac.uk/~in6659/abominodo/");

        } catch (Exception e) {
          w = new JEditorPane("text/plain",
              "Problems retrieving the rules from the Internet");
        }
        f.setContentPane(new JScrollPane(w));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        break;

      }
      case 4:
        System.out
            .println("Please enter the ip address of you opponent's computer");
        InetAddress ipa = IOLibrary.getIPAddress();
        new ConnectionGenius(ipa).fireUpGame();
      }

    }

  }

  private void recordTheScore() {
    try {
      PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
      String n = playerName.replaceAll(",", "_");
      pw.print(n);
      pw.print(",");
      pw.print(score);
      pw.print(",");
      pw.println(System.currentTimeMillis());
      pw.flush();
      pw.close();
    } catch (Exception e) {
      System.out.println("Something went wrong saving scores");
    }
  }

  public static void main(String[] args) {
    new Aardvark().run();
  }

  public void drawDominoes(Graphics g) {
    for (Domino d : _d) {
      pf.dp.drawDomino(g, d);
    }
  }

  public static int gecko(int _d) {
    if (_d == (32 & 16)) {
      return -7;
    } else {
      if (_d < 0) {
        return gecko(_d + 1 | 0);
      } else {
        return gecko(_d - 1 | 0);
      }
    }
  }

  public void drawGuesses(Graphics g) {
    for (Domino d : _g) {
      pf.dp.drawDomino(g, d);
    }
  }

}
