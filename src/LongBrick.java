/* Subclass for the long brick 
 *
 */
/** I Shaped Brick
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */

public class LongBrick extends TetrisBrick {

    public LongBrick() {
        colorNum = 7;
    }

    @Override
    public void initPosition(int cenX) {
        position = new int[][]{{0, cenX - 2},
        {0, cenX - 1},
        {0, cenX},
        {0, cenX + 1}
        };
    }

    @Override
    public void rotate() {
        int zeroOrientation = 0;
        int firstOrientation = 1;

        if (orientation == firstOrientation) {
            orientation = zeroOrientation;
        } else {
            orientation++;
        }
                
        LongBrickOrientations();
    }

    @Override
    public void unrotate() {
        int zeroOrientation = 0;
        int firstOrientation = 1;
        
        if (orientation == zeroOrientation) {
            orientation = firstOrientation;
        } else {
            orientation--;
        }
        
        LongBrickOrientations();      
        
    }

    private void LongBrickOrientations() {
        int cenY = position[2][0];
        int cenX = position[2][1];     
        
        switch (orientation) {
            case 0:
                position = new int[][]{{cenY, cenX - 2},
                {cenY, cenX - 1},
                {cenY, cenX},
                {cenY, cenX + 1}};
                break;
            case 1:
                position = new int[][]{{cenY - 2, cenX},
                {cenY - 1, cenX},
                {cenY, cenX},
                {cenY + 1, cenX}};
                break;
        }
    }
}
