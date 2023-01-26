/* Superclass that contains all attributes and methods that the seven brick 
 * types have in common. 
 */
/**
 * Abstract Class containing all bricks in Tetris game
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */
public abstract class TetrisBrick {

    protected int[][] position;
    protected int orientation = 0;
    protected int numSegments = 4;
    protected int colorNum;

    public TetrisBrick() {
        position = new int[numSegments][2];
    }

    public void moveUp() {
        int brickRow = 0;
        for (int row = 0; row < numSegments; row++) {
            position[row][brickRow] = position[row][brickRow] - 1;
        }
    }

    public void moveDown() {
        int brickRow = 0;
        for (int row = 0; row < numSegments; row++) {
            position[row][brickRow] = position[row][brickRow] + 1;
        }
    }

    public void moveLeft() {
        int brickColumn = 1;
        for (int column = 0; column < numSegments; column++) {
            position[column][brickColumn] = position[column][brickColumn] - 1;
        }
    }

    public void moveRight() {
        int brickColumn = 1;
        for (int column = 0; column < numSegments; column++) {
            position[column][brickColumn] = position[column][brickColumn] + 1;
        }
    }

    public abstract void initPosition(int centerX);

    public abstract void rotate();

    public abstract void unrotate();

    public int getColorNumber() {
        return colorNum;
    }
    
    public int setColorNumber(int colorNumber) {
        colorNum = colorNumber;
        return colorNum;
    }

    public int setSegPosition(int segment, int rowCol, int coord) {
        position[segment][rowCol] = coord;
        return position[segment][rowCol];
    }

    public int getTopRow() {
        int rows = 0;
        int topRow = 30;
        for (int segment = 0; segment < numSegments; segment++) {
            if (position[segment][rows] < topRow) {
                topRow = position[segment][rows];
            }
        }
        return topRow;
    }

    public int getBottomRow() {
        int rows = 0;
        int botRow = 0;
        for (int segment = 0; segment < numSegments; segment++) {
            if (position[segment][rows] > botRow) {
                botRow = position[segment][rows];
            }
        }
        return botRow;
    }
}
