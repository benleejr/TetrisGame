/* Subclass for the square shaped brick 
 *
 */
/** Square Shaped Brick 
 * @author benedictlee 
 * @version 1.0 Last Edit: 21 July 2022
 */

public class SquareBrick extends TetrisBrick {

    public SquareBrick() {
        colorNum = 5;
    }

    @Override
    public void initPosition(int cenX) {
        position = new int[][]
        { {0, cenX - 1},
          {1, cenX - 1},
          {0, cenX},
          {1, cenX}
        };        
    }
    
    @Override
    public void rotate() {
    }
    
    @Override
    public void unrotate() {        
    }
}
