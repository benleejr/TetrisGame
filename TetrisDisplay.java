/* Creates all components related to the game though the method paintComponent
 * Will listen to keyboard input to determine movement of bricks in game
 * Displays the status of the game
 * Animates bricks
 */
/** Displays Tetris Game using four methods
 *
 * @author benedictlee
 * @version 0.2 
 * Last Edit: 30 June 2022
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisDisplay extends JPanel {

    private TetrisGame game;
    private int startX = 50;
    private int startY = 50;
    private int cellSize = 10;
    private int speed;
    private Timer timer;
    private Color[] colors = {Color.MAGENTA, Color.CYAN, Color.RED, Color.GREEN,
        Color.PINK, Color.YELLOW, Color.ORANGE};

    public TetrisDisplay(TetrisGame gam) {
        game = gam;
        animateBricks();
        
    }
    
    private void animateBricks() {
        int delay = 100;
        
        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updateAnimation();
            }
        });
        timer.start();
    }
    
    private void updateAnimation() {
        game.makeMove();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWell(g);
        drawBrick(g);
    }

    public void drawWell(Graphics g) {
        int columns = cellSize * game.getCols();
        int rows = cellSize * game.getRows();
        int wellWallThickness = 5;

        int[] xCoords = {startX, startX, startX + columns, startX + columns,
            startX + columns - wellWallThickness, startX + columns - wellWallThickness,
            startX + wellWallThickness, startX + wellWallThickness};
        int[] yCoords = {startY, startY + rows, startY + rows, startY, startY,
            startY + rows - wellWallThickness, startY + rows - wellWallThickness,
            startY + wellWallThickness};

        g.setColor(Color.white);
        g.fillRect(startX, startY, columns, rows);

        g.setColor(Color.black);
        g.fillPolygon(xCoords, yCoords, 8);

        g.drawLine(xCoords[0], yCoords[0], xCoords[4], yCoords[4]);
    }

    public void drawBrick(Graphics g) {

        int brickColor = game.getFallingBrickColor();
        
        for (int seg = 0; seg < game.getNumSegs(); seg++) {
            int row = game.getSegRow(seg);
            int column = game.getSegCol(seg);

            g.setColor(colors[brickColor]);
            g.fillRect(startX + column * cellSize, startY + row * cellSize,
                    cellSize, cellSize);
            g.setColor(Color.black);
            g.drawRect(startX + column * cellSize, startY + row * cellSize, 
                    cellSize, cellSize);
        }
    }  
    
    private void cycleMove() {
        
    }
}
