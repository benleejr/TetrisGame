/* Superclass that contains all attributes and methods that the seven brick 
 * types have in common. 
 */
/**
 * Abstract Class containing all bricks in Tetris game
 *
 * @author benedictlee
 * @version 0.2
 * 30 June 2022
 */
public abstract class TetrisBrick {

    protected int[][] position;
    protected int numSegments = 4;
    protected int colorNum;

    public TetrisBrick() {        
        position = new int[numSegments][2];        
    }

    public void moveDown() {
        for (int row = 0; row < numSegments; row++) {            
            position[row][0] = position[row][0] + 1;
        }
    }

    public void moveUp() {
        for (int row = 0; row < numSegments; row++) {
            position[row][0] = position[row][0] - 2;
        }
    }

    public int getColorNumber() {        
        return colorNum;
    }

    public abstract void initPosition(int centerX);
        
}
