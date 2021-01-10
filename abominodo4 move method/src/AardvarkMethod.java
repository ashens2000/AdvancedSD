import java.awt.Graphics;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class AardvarkMethod {

	public String playerName;
	public List<Domino> _d;
	public List<Domino> _g;
	public int[][] grid = new int[7][8];
	public int[][] secgrid = new int[7][8];
	protected int score;
	protected PictureFrame pf = new PictureFrame();

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
	

	protected void collateGrid() {
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

	protected void generateGuesses() {
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

	protected void collateGuessGrid() {
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

	protected int printGuessGrid() {
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

	protected void generateDominoes() {
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

	protected void shuffleDominoesOrder() {
	    List<Domino> shuffled = new LinkedList<Domino>();
	
	    while (_d.size() > 0) {
	      int n = (int) (Math.random() * _d.size());
	      shuffled.add(_d.get(n));
	      _d.remove(n);
	    }
	
	    _d = shuffled;
	  }

	protected void invertSomeDominoes() {
	    for (Domino d : _d) {
	      if (Math.random() > 0.5) {
	        d.invert();
	      }
	    }
	  }

	protected void placeDominoes() {
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

	protected void rotateDominoes() {
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

	protected Domino findGuessandDominoAt(int x, int y, List<Domino> _p) {
	    for (Domino d : _p) {
	      if ((d.lengthofx == x && d.lengthofy == y) || (d.heightofx == x && d.heightofy == y)) {
	        return d;
	      }
	    }
	    return null;
	  }

	protected Domino findGuessByLH(int x, int y) {
	    for (Domino d : _g) {
	      if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
	        return d;
	      }
	    }
	    return null;
	  }

	protected Domino findDominoByLH(int x, int y) {
	    for (Domino d : _d) {
	      if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
	        return d;
	      }
	    }
	    return null;
	  }

	

	public void findallcertandposs(HashMap <Domino, List<Location>> map) {
		  
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

	protected void recordTheScore() {
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

	/**
	 * @deprecated Use {@link PictureFrame#drawDominoes(AardvarkMethod,Graphics)} instead
	 */
	public void drawDominoes(Graphics g) {
		pf.drawDominoes(this, g);
	}

	public AardvarkMethod() {
		super();
	}

	public void drawGuesses(Graphics g) {
	    for (Domino d : _g) {
	      pf.dp.drawDomino(g, d);
	    }
	  }

}