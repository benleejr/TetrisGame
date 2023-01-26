/* Creates all components related to the game though the method paintComponent
 * Will listen to keyboard input to determine movement of bricks in game
 * Displays the status of the game
 * Animates bricks
 */
/** Displays Tetris Game using four methods
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 8 July 2022
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisDisplay extends JPanel {

    private TetrisGame game;
    private int startX = 195;
    private int startY = 50;
    private int cellSize = 10;
    private int speed = 200;
    private boolean pause = false;
    private Timer timer;
    private Color[] colors = {Color.WHITE, Color.MAGENTA, Color.CYAN, Color.RED, Color.GREEN,
        Color.PINK, Color.YELLOW, Color.ORANGE};

    JTextField scoreField = new JTextField();

    public TetrisDisplay(TetrisGame gam) {
        game = gam;
        initScoreBoard();

        timer = new Timer(speed, (ActionEvent ae) -> {
            cycleMove();
        });
        timer.start();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                translateKey(ke);
            }
        });
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWell(g);
        drawBackground(g);
        drawBrick(g);
        gameOver(g);
    }

    private void initScoreBoard() {        

        JPanel northPanel = new JPanel();

        String score = "Score: ";
        JLabel scoreLabel = new JLabel(score);
        northPanel.add(scoreLabel);
        
        int characters = 7;
        String font = "Arial";
        int fontType = 3;
        int fontSize = 16;        
        Font scoreFont = new Font(font, fontType, fontSize);
        scoreField = new JTextField(characters);
        scoreField.setFont(scoreFont);
        scoreField.setHorizontalAlignment(JTextField.CENTER);
        scoreField.setText(String.valueOf(game.getScore()));

        northPanel.add(scoreField).setFocusable(false);

        this.add(northPanel, BorderLayout.NORTH);
    }

    private void drawWell(Graphics g) {
        int columns = cellSize * game.getCols();
        int rows = cellSize * game.getRows();
        int wellWallThickness = -5;

        int[] xCoords = {startX, startX, startX + columns, startX + columns,
            startX + columns - wellWallThickness, startX + columns - wellWallThickness,
            startX + wellWallThickness, startX + wellWallThickness};
        int[] yCoords = {startY, startY + rows, startY + rows, startY, startY,
            startY + rows - wellWallThickness, startY + rows - wellWallThickness,
            startY};

        g.setColor(Color.white);
        g.fillRect(startX, startY, columns, rows);

        g.setColor(Color.black);
        g.fillPolygon(xCoords, yCoords, xCoords.length);

        g.drawLine(startX, startY, startX + columns, startY);
    }

    private void drawBackground(Graphics g) {
        int startPosX;
        int startPosY = startY;
        int backgroundColor;
        for (int rows = 0; rows < game.getRows(); rows++) {
            startPosX = startX;
            for (int columns = 0; columns < game.getCols(); columns++) {
                backgroundColor = game.fetchBoardPosition(rows, columns);
                if (backgroundColor == 0) {
                    g.setColor(Color.WHITE);
                }
                if (backgroundColor != 0) {
                    g.setColor(colors[backgroundColor]);
                    g.fillRect(startPosX, startPosY, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(startPosX, startPosY, cellSize, cellSize);
                }
                startPosX += cellSize;
            }
            startPosY += cellSize;
        }
    }

    private void drawBrick(Graphics g) {

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

    private void gameOver(Graphics g) {
        int xOffset = 50;
        int yOffset = 60;
        int xText = xOffset;
        int yText = yOffset + 40;
        int width = 200;
        int height = 50;
        String font = "Arial";
        int fontType = 3;
        int fontSize = 30;
        int gameEnd = 0;       
        Font gameOverFont = new Font(font, fontType, fontSize);
        int resume = 1;

        String gameOver = "GAME OVER!";
        if (game.getGameState() == gameEnd) {
            timer.stop();
            g.setColor(Color.WHITE);
            g.fillRect(startX - xOffset, startY + yOffset, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(startX - xOffset, startY + yOffset, width, height);
            g.setFont(gameOverFont);
            g.drawString(gameOver, startX - xText, startY + yText);
            game.readHighScore();
            if (game.highScores.isEmpty() == true || game.isItAHighScore() == true) {
                String prompt = "Congratulations! You made it in the top 10 leaderboards!";
                String name = JOptionPane.showInputDialog(prompt);
                game.saveHighScore(name);
                game.setGameState(resume);
                game.newGame();
                timer.start();
            } else {
                String prompt = "Nice try! Try Again?";
                String title = "Better Luck Next Time!";
                JOptionPane.showMessageDialog(null, prompt, title, JOptionPane.PLAIN_MESSAGE);
                game.setGameState(resume);
                game.newGame();
                timer.start();
            }
        }
    }

    private void translateKey(KeyEvent ke) {
        int code = ke.getKeyCode();
        String moveDown = "D";
        String moveLeft = "L";
        String moveRight = "R";
        String rotate = "RT";

        switch (code) {
            case KeyEvent.VK_DOWN:
                game.makeMove(moveDown);
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove(moveLeft);
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove(moveRight);
                break;
            case KeyEvent.VK_UP:
                game.makeMove(rotate);
                break;
            case KeyEvent.VK_SPACE:
                if (pause == false) {
                    timer.stop();
                    pause = true;
                } else {
                    timer.start();
                    pause = false;
                }
                break;
            case KeyEvent.VK_N:
                game.newGame();
                break;
        }
        repaint();
    }

    private void cycleMove() {
        String moveDown = "D";
        game.makeMove(moveDown);
        scoreField.setText(String.valueOf(game.getScore()));
        repaint();
    }
}
