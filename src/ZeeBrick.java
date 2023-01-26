/* Subclass for the Z shaped brick 
 *
 */
/** Z Shaped Brick
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */

public class ZeeBrick extends TetrisBrick {

    public ZeeBrick() {
        colorNum = 4;
    }

    @Override
    public void initPosition(int cenX) {
        position = new int[][]{{0, cenX - 1},
        {0, cenX},
        {1, cenX},
        {1, cenX + 1}
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
        
        ZeeBrickOrientations();
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
        
        ZeeBrickOrientations();
    }
    
    private void ZeeBrickOrientations() {
        int cenY = position[2][0];
        int cenX = position[2][1]; 
        
        switch (orientation) {
            case 0:
                position = new int[][]{{cenY - 1, cenX - 1},
                {cenY - 1, cenX},
                {cenY, cenX},
                {cenY, cenX + 1}};
                break;
            case 1:
                position = new int[][]{{cenY - 1, cenX + 1},
                {cenY, cenX + 1},
                {cenY, cenX},
                {cenY + 1, cenX}};
                break;
        }        
    }
}
