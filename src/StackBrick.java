/* Subclass for the T shaped brick 
 *
 */
/** T Shaped Brick
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */

public class StackBrick extends TetrisBrick {

    public StackBrick() {
        colorNum = 6;
    }

    @Override
    public void initPosition(int cenX) {
        position = new int[][]{{1, cenX - 1},
        {0, cenX},
        {1, cenX},
        {1, cenX + 1}
        };
    }

    @Override
    public void rotate() {
        int zeroOrientation = 0;
        int thirdOrientation = 3;
        
        
        if (orientation == thirdOrientation) {
            orientation = zeroOrientation;
        } else {
            orientation++;
        }       
       
        StackBrickOrientations();
    }

    @Override
    public void unrotate() {
        int zeroOrientation = 0;
        int thirdOrientation = 3;
        
        if (orientation == zeroOrientation) {
            orientation = thirdOrientation;
        } else {
            orientation--;
        }
        
        StackBrickOrientations(); 
    }

    private void StackBrickOrientations() {
        int cenY = position[2][0];
        int cenX = position[2][1]; 
        
        switch (orientation) {
            case 0:
                position = new int[][]{{cenY, cenX - 1},
                {cenY - 1, cenX},
                {cenY, cenX},
                {cenY, cenX + 1}};
                break;
            case 1:
                position = new int[][]{{cenY + 1, cenX},
                {cenY, cenX + 1},
                {cenY, cenX},
                {cenY - 1, cenX}};
                break;
            case 2:
                position = new int[][]{{cenY, cenX + 1},
                {cenY + 1, cenX},
                {cenY, cenX},
                {cenY, cenX - 1}};
                break;
            case 3:
                position = new int[][]{{cenY + 1, cenX},
                {cenY, cenX - 1},
                {cenY, cenX},
                {cenY - 1, cenX}};
                break;
        }
    }
}
