
public class Location extends SpacePlace {
  public int column;
  public int row;
  public DIRECTION direction;
  
  public enum DIRECTION {VERTICAL, HORIZONTAL};
  
  public Location(int r, int c) {
    this.row = r;
    this.column = c;
  }

  public Location(int r, int c, DIRECTION d) {    
    this(r,c);
    this.direction=d;
  }
  
  public String toString() {
    if(direction==null){
      return "(" + (column+1) + "," + (row+1) + ")";
    } else {
      return "(" + (column+1) + "," + (row+1) + "," + direction + ")";
    }
  }
}
