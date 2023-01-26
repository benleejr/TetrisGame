/* Class that converts scores to Strings for display and file storage.
 * Contains method to compare and sort scores.
 */
/**
 * Class containing Names and HighScores
 *
 * @author benedictlee
 * @version 1.0 Last Edit: 21 July 2022
 */
class Scores implements Comparable<Scores>{

    private String name;
    private int score;
    
    public Scores(String nam, int scor) {
        name = nam;
        score = scor;
    }
    
    @Override
    public int compareTo(Scores score) {        
        return (this.getScore() > score.getScore() ? -1 :
                (this.getScore() == score.getScore() ? 0 : 1)); 
    }
    
    @Override
    public String toString() {
        return "Name: " + this.name + " Score: " + this.score;
    }
    
    public String fileToString() {
        return this.name + "," + this.score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
}
