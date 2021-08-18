package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class Score {
    // Basic Constructor
    public Score(String name, String difficulty, String score, String combo) {
        this.Name = new SimpleStringProperty(name);
        this.Difficulty = new SimpleStringProperty(difficulty);
        this.Score = new SimpleStringProperty(score);
        this.Combo = new SimpleStringProperty(combo);
    }

    public static boolean matchingScore(ucf.assignments.Score score, ObservableList<ucf.assignments.Score> items) {
        boolean matches = false;
        // basically, if the new Score matches the Name *and* difficulty of any item in the list
        // then it matches another item. Otherwise, it's effectively a new item
        for(int i=0;i<items.size();i++){
            if((score.getName().equalsIgnoreCase(items.get(i).getName()))&& score.getDifficulty().equalsIgnoreCase(items.get(i).getDifficulty())){
                matches = true;
            }
        }
        return matches;
    }

    public static boolean ChangeValue(ucf.assignments.Score score_selected, String score) {
            try{
                // Checks if the score value is an Integer, then checks if its in the valid score range
                // If it fails either condition, it returns false (does not change value)
                int number = Integer.parseInt(score);
                if(number >= 0 && number <= 1000000){
                    score_selected.setScore(score);
                    return true;
                }
            }
            catch(NumberFormatException e){
                return false;
            }
            return false;
    }

    public static boolean ChangeCombo(ucf.assignments.Score score_selected, String new_combo) {
        // If the new_combo rating exists among a set of options that happen to be
        // the same as our Choice Box Combo, set the new string, otherwise keep the old string
        String lcCombo = new_combo.toLowerCase();
        if(lcCombo.equalsIgnoreCase("mfc") || lcCombo.equalsIgnoreCase("pfc") || lcCombo.equalsIgnoreCase("gfc") || lcCombo.equalsIgnoreCase("fc") || lcCombo.equalsIgnoreCase("pass") || lcCombo.equalsIgnoreCase("fail")){
            score_selected.setCombo(new_combo.toUpperCase());
            return true;
        }
        return false;
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getDifficulty() {
        return Difficulty.get();
    }

    public SimpleStringProperty difficultyProperty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.Difficulty.set(difficulty);
    }

    public String getScore() {
        return Score.get();
    }

    public SimpleStringProperty scoreProperty() {
        return Score;
    }

    public void setScore(String score) {
        this.Score.set(score);
    }

    public String getCombo() {
        return Combo.get();
    }

    public SimpleStringProperty comboProperty() {
        return Combo;
    }

    public void setCombo(String combo) {
        this.Combo.set(combo);
    }

    private final SimpleStringProperty Name;
    private final SimpleStringProperty Difficulty;
    private final SimpleStringProperty Score;
    private final SimpleStringProperty Combo;

}
