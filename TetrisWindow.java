/* This is the TetrisWindow class that is also the driver for the game of Tetris.
 *      Houses TetrisDisplay and TetrisGame to instantiate the game of Tetris
 * 
 * Contains JFrame: Can resize the popup window to a desired size
 * Can alter the size of well of this Tetris game
 */
/** Tetris Game Main Executable File
 *
 * @author benedictlee
 * @version 0.1
 * Last Edit: 27 June 2022
 */

import javax.swing.*;

class TetrisWindow extends JFrame {

    private TetrisGame game;
    private TetrisDisplay display;
    private int winWidth = 500;
    private int winHeight = 500;
    private int gameRows = 30;
    private int gameColumns = 15;

    public TetrisWindow() {
        String title = "My Very First Tetris Game";
        game = new TetrisGame(gameRows, gameColumns);
        display = new TetrisDisplay(game);

        this.setTitle(title);
        this.setSize(winWidth, winHeight); //Only use numeric numerals for assigning literals to variables
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes window when program terminates

        this.add(display);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new TetrisWindow();
    }
}
