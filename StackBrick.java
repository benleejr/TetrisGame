/* Subclass for the T shaped brick 
 *
*/
/** T Shaped Brick
 * @version 0.2
 * @author benedictlee
 * 30 June 2022
 */

public class StackBrick extends TetrisBrick {
    public StackBrick() {
        colorNum = 5;
    }
//
    public void initPosition(int cenX) {
        position[0][0] = 1;
        position[0][1] = cenX-1;
        position[1][0] = 1;
        position[1][1] = cenX;
        position[2][0] = 0;
        position[2][1] = cenX;
        position[3][0] = 1;
        position[3][1] = cenX+1;   
    }
}