/* Contains all logic for directing the game
 * Responds to all moves communicated by TetrisDisplay class as valid or invalid
 * End-game detection
 */
/** Tetris Logic Control
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */
import java.util.*;
import java.io.*;

public class TetrisGame {

    private TetrisBrick fallingBrick;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int state = 1;
    private int score = 0;
    private int[][] background;
    private Random randomGen = new Random();

    ArrayList<Scores> highScores = new ArrayList<>(10);

    public TetrisGame(int rw, int col) {
        rows = rw;
        cols = col;
        background = new int[rows][cols];
        spawnBrick();
    }

    public void initBoard() {
        background = new int[rows][cols];
        spawnBrick();
    }

    public void newGame() {
        initBoard();
        score = 0;
    }

    public int fetchBoardPosition(int row, int column) {
        int boardStatus = background[row][column];
        return boardStatus;
    }

    private void spawnBrick() {
        int cenX = cols / 2;
        int index = randomGen.nextInt(numBrickTypes);

        switch (index) {
            case 0:
                fallingBrick = new ElBrick();
                fallingBrick.initPosition(cenX);
                break;
            case 1:
                fallingBrick = new JayBrick();
                fallingBrick.initPosition(cenX);
                break;
            case 2:
                fallingBrick = new EssBrick();
                fallingBrick.initPosition(cenX);
                break;
            case 3:
                fallingBrick = new ZeeBrick();
                fallingBrick.initPosition(cenX);
                break;
            case 4:
                fallingBrick = new SquareBrick();
                fallingBrick.initPosition(cenX);
                break;
            case 5:
                fallingBrick = new StackBrick();
                fallingBrick.initPosition(cenX);
                break;
            case 6:
                fallingBrick = new LongBrick();
                fallingBrick.initPosition(cenX);
                break;
        }
    }

    public void makeMove(String code) {
        switch (code) {
            case "D":
                fallingBrick.moveDown();
                if (validateMove() == false) {
                    fallingBrick.moveUp();
                    transferColor();
                    clearLines();
                    spawnBrick();
                }
                break;
            case "L":
                fallingBrick.moveLeft();
                if (validateMove() == false) {
                    fallingBrick.moveRight();
                }
                break;
            case "R":
                fallingBrick.moveRight();
                if (validateMove() == false) {
                    fallingBrick.moveLeft();
                }
                break;
            case "RT":
                fallingBrick.rotate();
                if (validateMove() == false) {
                    fallingBrick.unrotate();
                }
                break;
        }
    }

    private boolean validateMove() {
        int brickRow;
        int brickColumn;
        int brickX = 1;
        int brickY = 0;
        int lBorder = -1;
        int noColor = 0;
        int upperBorder = 0;
        int secondRow = 1;
        int endGame = 0;

        for (int segment = 0; segment < fallingBrick.numSegments; segment++) {
            brickRow = fallingBrick.position[segment][brickY];
            brickColumn = fallingBrick.position[segment][brickX];
            if (brickRow <= upperBorder) {
                return false;
            }
            if (brickColumn >= cols) {
                return false;
            }
            if (brickColumn <= lBorder) {
                return false;
            }
            if (brickRow >= rows) {
                return false;
            }
            if (fetchBoardPosition(brickRow, brickColumn) != noColor) {
                return false;
            }
            if (fetchBoardPosition(upperBorder, brickColumn) != noColor || 
                    fetchBoardPosition(secondRow, brickColumn) != noColor) {
                state = endGame;
            }
        }
        return true;
    }

    private void transferColor() {
        int brickColor = fallingBrick.getColorNumber();
        int xCords = 1;
        int yCords = 0;
        int brickCurRow;
        int brickCurColumn;

        for (int segment = 0; segment < fallingBrick.numSegments; segment++) {
            brickCurRow = fallingBrick.position[segment][yCords];
            brickCurColumn = fallingBrick.position[segment][xCords];
            background[brickCurRow][brickCurColumn] = brickColor;
        }
    }

    public void clearLines() {
        int topRow = fallingBrick.getTopRow();
        int bottomRow = fallingBrick.getBottomRow();
        int comboCount = 0;
        for (int currentRow = bottomRow; currentRow > topRow; currentRow--) {
            if (rowHasSpace(currentRow) == false) {
                copyAllRows(currentRow);
                currentRow++;
                comboCount++;
            }
        }
        scoring(comboCount);
    }

    public void scoring(int comboCount) {
        int oneLineScore = 100;
        int twoLineScore = 300;
        int threeLineScore = 600;
        int fourLineScore = 1200;
        switch (comboCount) {
            case 1:
                score += oneLineScore;
                break;
            case 2:
                score += twoLineScore;
                break;
            case 3:
                score += threeLineScore;
                break;
            case 4:
                score += fourLineScore;
                break;
        }
    }

    public boolean rowHasSpace(int rowNumber) {
        for (int column = 0; column < cols; column++) {
            if (background[rowNumber][column] == 0) {
                return true;
            }
        }
        return false;
    }

    public void copyAllRows(int rowNumber) {
        int aboveRowNumber = rowNumber - 1;
        while (rowHasColor(aboveRowNumber) == true) {
            copyRow(aboveRowNumber);
            aboveRowNumber--;
            if (rowHasColor(aboveRowNumber) == false) {
                copyRow(aboveRowNumber);
                return;
            }
        }
    }

    public boolean rowHasColor(int rowNumber) {
        for (int column = 0; column < cols; column++) {
            if (background[rowNumber][column] != 0) {
                return true;
            }
        }
        return false;
    }

    public void copyRow(int rowNumber) {
        int lowerRow = rowNumber + 1;
        for (int column = 0; column < cols; column++) {
            background[lowerRow][column] = background[rowNumber][column];
        }
    }

    @Override
    public String toString() {
        String saveState = "";
        saveState += fallingBrick.getColorNumber() + "\n";

        //Falling brick
        for (int segment = 0; segment < fallingBrick.numSegments; segment++) {
            for (int rowCol = 0; rowCol < fallingBrick.position[0].length; rowCol++) {
                saveState += fallingBrick.position[segment][rowCol] + " ";
            }
            saveState += "\n";
        }
        //Placed bricks
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                saveState += background[row][column] + " ";
            }
            saveState += "\n";
        }
        saveState = saveState.substring(0, saveState.length() - 1);

        return saveState;
    }

    public File saveToFile(File saveFile) {

        try {
            FileWriter outWriter = new FileWriter(saveFile);
            outWriter.write(this.toString());
            outWriter.close();
        } catch (IOException ioe) {
            System.err.print("Trouble while writing to file");
        }
        return saveFile;
    }

    public void retrieveFromFile(File loadFile) {
        try {
            Scanner inScan = new Scanner(loadFile);

            fallingBrick.setColorNumber(inScan.nextInt());

            for (int segment = 0; segment < fallingBrick.numSegments; segment++) {
                for (int rowCol = 0; rowCol < fallingBrick.position[0].length; rowCol++) {
                    fallingBrick.setSegPosition(segment, rowCol, inScan.nextInt());
                }
            }

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < cols; column++) {
                    background[row][column] = inScan.nextInt();
                }
            }

        } catch (FileNotFoundException fnfe) {
            System.err.print("Trouble opening file to read: " + loadFile);
        } catch (Exception e) {
            System.err.print("Error occured when retrieving from file");
        }
    }

    public void deleteHighScores() {
        String highScoreFile = "highScores.csv";
        try {
            FileWriter wipeWriter = new FileWriter(highScoreFile);
            String empty = "";
            wipeWriter.write(empty);
            wipeWriter.close();
        } catch (IOException ioe) {
            System.err.print("Trouble while writing to file");
        }
    }

    public void saveHighScore(String nameField) {
        String highScoreFile = "highscores.csv";        
        try {
            
            FileWriter outWriter = new FileWriter(highScoreFile);
            String name = nameField;
            int highScore = score;
            highScores.add(new Scores(name, highScore));
            scoreSort();
            String saveScore = "";
            for (Scores s : highScores) {
                saveScore += s.fileToString() + "\n";
            }
            //String toString = "\n" + name + "," + highScore;
            outWriter.write(saveScore);
            outWriter.close();
        } catch (IOException ioe) {
            System.err.print("Trouble while writing to file");
        }
    }

    public void readHighScore() {
        String fileName = "highscores.csv";
        File inFile = new File(fileName);
        highScores.clear();
        if (inFile.exists() == false) {
            System.err.print("Cannot read file!");
        }
        try {
            Scanner inScan = new Scanner(inFile).useDelimiter("[,\n]");

            while (inScan.hasNext()) {
                String name = inScan.next();
                int highScore = inScan.nextInt();
                highScores.add(new Scores(name, highScore));
            }
            scoreSort();
        } catch (FileNotFoundException fnfe) {
        }
    }

    public boolean isItAHighScore() {
        int lastElement = highScores.size() - 1;
        int lowestScore = highScores.get(lastElement).getScore();

        return score > lowestScore;
    }

    private Object scoreSort() {
        int topTenOnly = 10;
        Collections.sort(highScores);
        int lastElement = highScores.size() - 1;
        if (highScores.size() > topTenOnly) {
            highScores.remove(lastElement);
        }
        return highScores;
    }

    public String scoreToString() {
        String displayScores = "";
        for (Scores s : highScores) {
            displayScores += s.toString() + "\n";
        }
        return displayScores;
    }

    public int getNumSegs() {
        return fallingBrick.numSegments;
    }

    public int getSegRow(int segNum) {
        return fallingBrick.position[segNum][0];
    }

    public int getSegCol(int segNum) {
        return fallingBrick.position[segNum][1];
    }

    public int getFallingBrickColor() {
        int fallingBrickColor = fallingBrick.getColorNumber();
        return fallingBrickColor;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getScore() {
        return score;
    }

    public int getGameState() {
        return state;
    }

    public int setGameState(int newState) {
        state = newState;
        return state;
    }
}
