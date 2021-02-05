import java.awt.Graphics;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class AardvarkCont {

	void generateDominoes(Aardvark aardvark) {
	    aardvark._d = new LinkedList<Domino>();
	    int count = 0;
	    int x = 0;
	    int y = 0;
	    for (int l = 0; l <= 6; l++) {
	      for (int h = l; h <= 6; h++) {
	        Domino d = new Domino(h, l);
	        aardvark._d.add(d);
	        d.place(x, y, x + 1, y);
	        count++;
	        x += 2;
	        if (x > 6) {
	          x = 0;
	          y++;
	        }
	      }
	    }
	    errorGeneratingDominoes(count);
	  }

	void errorGeneratingDominoes(int count) {
	    if (count != 28) {
	      System.out.println("something went wrong generating dominoes");
	      System.exit(0);
	    }
	  }

	void generateGuesses(Aardvark aardvark) {
	    aardvark._g = new LinkedList<Domino>();
	    int count = 0;
	    for (int l = 0; l <= 6; l++) {
	      for (int h = l; h <= 6; h++) {
	        Domino d = new Domino(h, l);
	        aardvark._g.add(d);
	        count++;
	      }
	    }
	    errorGeneratingDominoes(count);
	  }

	void collateGrid(Aardvark aardvark) {
	    for (Domino d : aardvark._d) {
	      if (!d.placed) {
	        aardvark.grid[d.hy][d.hx] = 9;
	        aardvark.grid[d.ly][d.lx] = 9;
	      } else {
	        aardvark.grid[d.hy][d.hx] = d.high;
	        aardvark.grid[d.ly][d.lx] = d.low;
	      }
	    }
	  }

	void collateGuessGrid(Aardvark aardvark) {
	    for (int r = 0; r < 7; r++) {
	      for (int c = 0; c < 8; c++) {
	        aardvark.gg[r][c] = 9;
	      }
	    }
	    for (Domino d : aardvark._g) {
	      if (d.placed) {
	        aardvark.gg[d.hy][d.hx] = d.high;
	        aardvark.gg[d.ly][d.lx] = d.low;
	      }
	    }
	  }

	int printGridorGuess(int number[][]) {
	    for (int are = 0; are < 7; are++) {
	      for (int see = 0; see < 8; see++) {
	        if (number[are][see] != 9) {
	          System.out.printf("%d", number[are][see]);
	        } else {
	          System.out.print(".");
	        }
	      }
	      System.out.println();
	    }
	    return 11;
	  }
		
	void shuffleDominoesOrder(Aardvark aardvark) {
	    List<Domino> shuffled = new LinkedList<Domino>();

	    while (aardvark._d.size() > 0) {
	      int n = (int) (Math.random() * aardvark._d.size());
	      	shuffled.add(aardvark._d.get(n));
	      aardvark._d.remove(n);
	    }

	    aardvark._d = shuffled;
	  }

	void invertSomeDominoes(Aardvark aardvark) {
	    for (Domino d : aardvark._d) {
	      if (Math.random() > 0.5) {
	        d.invert();
	      }
	    }
	  }

	void placeDominoes(Aardvark aardvark) {
	    int x = 0;
	    int y = 0;
	    int count = 0;
	    for (Domino d : aardvark._d) {
	      count++;
	      d.place(x, y, x + 1, y);
	      x += 2;
	      if (x > 6) {
	        x = 0;
	        y++;
	      }
	    }
	    errorGeneratingDominoes(count);
	  }

	void rotateDominoes(Aardvark aardvark) {
	    for (int x = 0; x < 7; x++) {
	      for (int y = 0; y < 6; y++) {
	        aardvark.ac.tryToRotateDominoAt(aardvark, x, y);
	      }
	    }
	  }

	void tryToRotateDominoAt(Aardvark aardvark, int x, int y) {
	    Domino d = aardvark.ac.findAt(aardvark, x, y, aardvark._d);
	    if (aardvark.ac.thisIsTopLeftOfDomino(aardvark, x, y, d)) {
	      if (d.ishl()) {
	        boolean weFancyARotation = Math.random() < 0.5;
	        if (weFancyARotation) {
	          if (aardvark.ac.theCellBelowIsTopLeftOfHorizontalDomino(aardvark, x, y)) {
	            Domino e = aardvark.ac.findAt(aardvark, x, y + 1, aardvark._d);
	            e.hx = x;
	            e.lx = x;
	            d.hx = x + 1;
	            d.lx = x + 1;
	            e.ly = y + 1;
	            e.hy = y;
	            d.ly = y + 1;
	            d.hy = y;
	          }
	        }
	      } else {
	        boolean weFancyARotation = Math.random() < 0.5;
	        if (weFancyARotation) {
	          if (aardvark.ac.theCellToTheRightIsTopLeftOfVerticalDomino(aardvark, x, y)) {
	            Domino e = aardvark.ac.findAt(aardvark, x + 1, y, aardvark._d);
	            e.hx = x;
	            e.lx = x + 1;
	            d.hx = x;
	            d.lx = x + 1;
	            e.ly = y + 1;
	            e.hy = y + 1;
	            d.ly = y;
	            d.hy = y;
	          }
	        }

	      }
	    }
	  }

	boolean theCellToTheRightIsTopLeftOfVerticalDomino(Aardvark aardvark, int x, int y) {
	    Domino e = aardvark.ac.findAt(aardvark, x + 1, y, aardvark._d);
	    return aardvark.ac.thisIsTopLeftOfDomino(aardvark, x + 1, y, e) && !e.ishl();
	  }

	boolean theCellBelowIsTopLeftOfHorizontalDomino(Aardvark aardvark, int x, int y) {
	    Domino e = aardvark.ac.findAt(aardvark, x, y + 1, aardvark._d);
	    return aardvark.ac.thisIsTopLeftOfDomino(aardvark, x, y + 1, e) && e.ishl();
	  }

	boolean thisIsTopLeftOfDomino(Aardvark aardvark, int x, int y, Domino d) {
	    return (x == Math.min(d.lx, d.hx)) && (y == Math.min(d.ly, d.hy));
	  }

	Domino findAt(Aardvark aardvark, int x, int y, List<Domino> list) {
	    for (Domino d : list) {
	      if ((d.lx == x && d.ly == y) || (d.hx == x && d.hy == y)) {
	        return d;
	      }
	    }
	    return null;
	  }

	Domino findByLH(Aardvark aardvark, int x, int y, List<Domino> list) {
	    for (Domino d : list) {
	      if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
	        return d;
	      }
	    }
	    return null;
	  }

	void printDominoes(Aardvark aardvark) {
	    for (Domino d : aardvark._d) {
	      System.out.println(d);
	    }
	  }

	void printGuesses(Aardvark aardvark) {
	    for (Domino d : aardvark._g) {
	      System.out.println(d);
	    }
	  }

	public void drawGuesses(Aardvark aardvark, Graphics g) {
	    for (Domino d : aardvark._g) {
	      aardvark.pf.dp.drawDomino(g, d);
	    }
	  }

	void recordTheScore(Aardvark aardvark) {
	    try {
	      PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
	      String n = aardvark.getPlayerName().replaceAll(",", "_");
	      pw.print(n);
	      pw.print(",");
	      pw.print(aardvark.score);
	      pw.print(",");
	      pw.println(System.currentTimeMillis());
	      pw.flush();
	      pw.close();
	    } catch (Exception e) {
	      System.out.println("Something went wrong saving scores");
	    }
	  }

	void quit(Aardvark aardvark) {
	    if (aardvark._d == null) {
	      System.out.println("It is a shame that you did not want to play");
	    } else {
	      System.out.println("Thankyou for playing");
	    }
	    System.exit(0);
	  }

	public static int gecko(int n) {
	    if (n == (32 & 16)) {
	      return -7;
	    } else {
	      if (n < 0) {
	        return gecko(n + 1 | 0);
	      } else {
	        return gecko(n - 1 | 0);
	      }
	    }
	}
}
