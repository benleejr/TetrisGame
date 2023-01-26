/* This is the TetrisWindow class that is the driver class for the game of Tetris.
 *      Houses TetrisDisplay and TetrisGame to instantiate the game of Tetris
 * 
 * Contains JFrame: Can resize the popup window to a desired size
 * Can alter the size of well of this Tetris game
 */
/** Tetris Game Main Executable File
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

class TetrisWindow extends JFrame {

    private TetrisGame game;
    private TetrisDisplay display;
    private int winWidth = 500;
    private int winHeight = 500;
    private int gameRows = 20;
    private int gameColumns = 12;

    public TetrisWindow() {
        String title = "My Very First Tetris Game";
        game = new TetrisGame(gameRows, gameColumns);
        display = new TetrisDisplay(game);

        this.setTitle(title);
        this.setSize(winWidth, winHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenu();
        this.add(display);

        this.setVisible(true);
    }

    private void initMenu() {

        JFileChooser fileChooser = new JFileChooser();

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        String fileMenuName = "File";

        JMenu fileMenu = new JMenu(fileMenuName);
        menuBar.add(fileMenu);

        String newGameName = "New Game";
        String loadName = "Load Game";
        String saveName = "Save Game";

        JMenuItem newGame = new JMenuItem(newGameName);
        JMenuItem loadGame = new JMenuItem(loadName);
        JMenuItem saveGame = new JMenuItem(saveName);

        fileMenu.add(newGame);
        fileMenu.add(loadGame);
        fileMenu.add(saveGame);
        newGame.addActionListener((ActionEvent ae) -> {
           game.newGame();
        });

        loadGame.addActionListener((ActionEvent ae) -> {
            int openWindow = fileChooser.showOpenDialog(TetrisWindow.this);
            
            if (openWindow == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                game.retrieveFromFile(fileToLoad);
            }
        });
        saveGame.addActionListener((ActionEvent ae) -> {
            int saveWindow = fileChooser.showSaveDialog(TetrisWindow.this);
            
            if (saveWindow == JFileChooser.APPROVE_OPTION) {
                try {
                    File fileToSave = fileChooser.getSelectedFile();
                    game.saveToFile(fileToSave);
                } catch (Exception e) {
                }
            }
        });

        String gameMenuName = "Game";

        JMenu gameMenu = new JMenu(gameMenuName);
        menuBar.add(gameMenu);

        String leaderboardName = "Leaderboards";
        String clearLeaderboardName = "Clear Leaderboards";

        JMenuItem showLeaderboard = new JMenuItem(leaderboardName);
        JMenuItem clearLeaderboard = new JMenuItem(clearLeaderboardName);

        gameMenu.add(showLeaderboard);
        gameMenu.add(clearLeaderboard);

        String leaderboards = "Leaderboards";

        showLeaderboard.addActionListener((ActionEvent ae) -> {
            game.readHighScore();
            JOptionPane.showMessageDialog(null, game.scoreToString(),
                    leaderboards, JOptionPane.PLAIN_MESSAGE);
        });

        clearLeaderboard.addActionListener((ActionEvent ae) -> {
            game.deleteHighScores();
        });
    }

    public static void main(String[] args) {
        TetrisWindow tetrisGame = new TetrisWindow();
    }
}
