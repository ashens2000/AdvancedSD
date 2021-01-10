
public class Domino implements Comparable<Domino> {
  public int high;
  public int low;
  public int heightofx;
  public int heightofy;
  public int lengthofx;
  public int lengthofy;
  public boolean placed = false;

  public Domino(int high, int low) {
    super();
    this.high = high;
    this.low = low;
  }
  
  public void setdata(int hx, int hy, int lx, int ly) { //changed to setdata
    this.heightofx = hx;
    this.heightofy = hy;
    this.lengthofx = lx;
    this.lengthofy = ly;
    placed = true;
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("[");
    result.append(Integer.toString(high));
    result.append(Integer.toString(low));
    result.append("]");
    if(!placed){
      result.append("unplaced");
    } else {
      result.append("(");
      result.append(Integer.toString(heightofx+1));
      result.append(",");
      result.append(Integer.toString(heightofy+1));
      result.append(")");
      result.append("(");
      result.append(Integer.toString(lengthofx+1));
      result.append(",");
      result.append(Integer.toString(lengthofy+1));
      result.append(")");
    }
    return result.toString();
  }

  /* turn the domino around 180 degrees clockwise*/
  
  public void invert() {
    int tx = heightofx;
    heightofx = lengthofx;
    lengthofx = tx;
    
    int ty = heightofy;
    heightofy = lengthofy;
    lengthofy = ty;    
  }

  public boolean ishl() {    
    return heightofy==lengthofy;
  }


  public int compareTo(Domino arg0) {
    if(this.high < arg0.high){
      return 1;
    }
    return this.low - arg0.low;
  }
  
  
  
}
