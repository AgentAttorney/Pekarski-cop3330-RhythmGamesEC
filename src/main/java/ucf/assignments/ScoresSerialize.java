package ucf.assignments;

import java.util.ArrayList;
import java.util.List;

public class ScoresSerialize {
    public ArrayList<ScoreSerialize> Scores;

    public ScoresSerialize() {
    }

    public ArrayList<ScoreSerialize> getScores() {
        return Scores;
    }

    public void setScores(ArrayList<ScoreSerialize> scores) {
        Scores = scores;
    }

    public ScoresSerialize(ArrayList<ScoreSerialize> Scores) {
        this.Scores = Scores;
    }


}
