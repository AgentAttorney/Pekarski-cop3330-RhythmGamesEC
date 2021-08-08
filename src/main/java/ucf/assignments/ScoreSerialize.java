package ucf.assignments;

import java.util.ArrayList;

public class ScoreSerialize {

    public String name;
    public String difficulty;
    public String score;
    public String combo;

    public ScoreSerialize(){
    }

    public ScoreSerialize(String name, String difficulty, String score, String combo) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
        this.combo = combo;
    }
}
