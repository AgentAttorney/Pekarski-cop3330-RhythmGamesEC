package ucf.assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoresController implements Initializable {

    @FXML TextField SearchBar;
    @FXML CheckBox CheckSearch;

    @FXML ChoiceBox<String> NameChoice;
    @FXML ChoiceBox<String> DifficultyChoice;
    @FXML TextField ScoreValue;
    @FXML ChoiceBox<String> ComboChoice;

    @FXML Button EnterScoreButton;
    @FXML Button SearchButton;
    @FXML Button MakeImageButton;

    @FXML TableView<Score> ScoreList;
    @FXML TableColumn<Score,String> NameColumn;
    @FXML TableColumn<Score,String> DifficultyColumn;
    @FXML TableColumn<Score,String> ScoreColumn;
    @FXML TableColumn<Score,String> ComboColumn;


    public void LoadSongsPushed(ActionEvent event){
        // Gets the stage and sends it to be used by FindNames
        Stage stage = (Stage) NameChoice.getScene().getWindow();

        // Returns an observable list which we set the Choice Box for Names with
        ObservableList<String> SongNames = FindSongNameList.FindNames(stage);

        // Set the Choice Box Items and initialize with a value, and let user enter a score
        NameChoice.setItems(SongNames);
        NameChoice.setValue(SongNames.get(0));
        EnterScoreButton.setDisable(false);
    }

    public void ClearSelectionPushed(ActionEvent event){
        // Allows for Deselection of Table Object
        // May not be useful for users, but useful for testing at least
        ScoreList.getSelectionModel().clearSelection();
    }

    public void EnterScorePushed(ActionEvent event){
        // Get the values in the text fields and make a new Score
        Score score = new Score(NameChoice.getValue(),DifficultyChoice.getValue(),ScoreValue.getText(),ComboChoice.getValue());

        // Check if the new Score matches an existing score
        boolean match_Score = Score.matchingScore(score,ScoreList.getItems());

        // Add the Score unless one of the fields are empty or matches an existing score
        if(!NameChoice.getValue().isEmpty() && !DifficultyChoice.getValue().isEmpty() && !ComboChoice.getValue().isEmpty() && !match_Score){
            // add Item
            ScoreList.getItems().add(score);
        }
    }
    @FXML
    public void changeScoreValueEdit(TableColumn.CellEditEvent<Score,String> editedCell){
        Score score_selected = ScoreList.getSelectionModel().getSelectedItem();
        String new_score = editedCell.getNewValue();

        // Check if the score should be changed
        boolean was_changed = Score.ChangeValue(score_selected,new_score);
        if(!was_changed){
            ScoreList.refresh();
        }
    }
    @FXML
    public void changeComboValueEdit(TableColumn.CellEditEvent<Score,String> editedCell){
        Score score_selected = ScoreList.getSelectionModel().getSelectedItem();
        String new_combo = editedCell.getNewValue();

        // Check if the Combo Type should be changed
        boolean was_changed = Score.ChangeCombo(score_selected,new_combo);
        if(!was_changed){
            ScoreList.refresh();
        }
    }

    @FXML
    public void MakeImageButtonPushed(ActionEvent event){

        // Lets the user choose a directory to place their image in
        Stage stage = (Stage) ScoreList.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Location of Images");
        File file = dc.showDialog(stage);

        // if the user selected a directory
        if(file != null){
            // Write the image here using my old code
            FindSongNameList.OutputImage(file.getAbsolutePath(),ScoreList.getSelectionModel().getSelectedItem(),stage);
        }

    }
    public void SaveButtonPushed(ActionEvent event){

        // get the current stage and let the user choose a directory to save their JSON file
        Stage stage = (Stage) ScoreList.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        File file = dc.showDialog(stage);

        // Create an Array List of Serializable Scores (identical class except they use Strings)
        ArrayList<ScoreSerialize> scores_for_file = new ArrayList<>();
        try{
            // Loop through all the Items in the table, make a new Serialized Score with the parameters
            // Add it to the Array List
            for(Score score: ScoreList.getItems()){
                ScoreSerialize serialized_score = new ScoreSerialize(score.getName(),score.getDifficulty(),score.getScore(),score.getCombo());
                scores_for_file.add(serialized_score);
            }
            // Make a ScoresSerialize instance that simply has an arraylist of Serialized Score
            ScoresSerialize serial_scores = new ScoresSerialize(scores_for_file);

            // Write the above class instance to the json file denoted "scores.json"
            new ObjectMapper().writeValue(Paths.get(file + "/scores.json").toFile(),serial_scores);
        }
        catch(Exception ignored){

        }
    }

    public void LoadButtonPushed(ActionEvent event){
        // Check first if the Table is empty, so we don't load into a filled table
        if(ScoreList.getItems().isEmpty()) {
            try {
                // Make a new FileChooser and let the user search for JSON files
                Stage stage = (Stage) ScoreList.getScene().getWindow();
                FileChooser fc = new FileChooser();
                fc.setTitle("Load Scores From Previous App Instance");
                fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
                File file = fc.showOpenDialog(stage);

                // Get the contents in the JSON file using ObjectMapper and add them to the ScoresSerialize class
                // That class's Arraylist will then contain all the scores. Then make an empty Observable List of Scores
                ScoresSerialize S_Scores = new ObjectMapper().readValue(Paths.get(String.valueOf(file)).toFile(), ScoresSerialize.class);
                ObservableList<Score> addTableFromFile = FXCollections.observableArrayList();

                // loop through all the Scores in the Arraylist, turn each into a ScoreSerializable class instance
                // then turn each of those instances into a new Score, then add it to the Observable List
                for (int i = 0; i < S_Scores.getScores().size(); i++) {
                    ScoreSerialize scoreToUnserialize = S_Scores.getScores().get(i);
                    Score score = new Score(scoreToUnserialize.name, scoreToUnserialize.difficulty, scoreToUnserialize.score, scoreToUnserialize.combo);
                    addTableFromFile.add(score);
                }
                // Add the Observable List to the Table
                ScoreList.getItems().addAll(addTableFromFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void SearchButtonPushed(ActionEvent event) {
        // NOT FULLY FUNCTIONAL BEWARE
        if (CheckSearch.isSelected()) {
            FilteredList<Score> FilteredScores = new FilteredList<>(ScoreList.getItems(), b -> true);
            SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                FilteredScores.setPredicate(score -> {
                    if (newValue.isEmpty()) {
                        return true;
                    }
                    String lcFilter = newValue.toLowerCase();
                    if (score.getName().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (score.getDifficulty().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (score.getScore().contains(newValue)) {
                        return true;
                    } else return score.getCombo().toLowerCase().contains(lcFilter);

                });
            });
            SortedList<Score> sortedScores = new SortedList<>(FilteredScores);
            sortedScores.comparatorProperty().bind(ScoreList.comparatorProperty());
            ScoreList.setItems(sortedScores);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ************************** TABLE SETUP *************************

        // Set the Data types that each Table Column accepts, and where it refers to in code
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        DifficultyColumn.setCellValueFactory(new PropertyValueFactory<>("Difficulty"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));
        ComboColumn.setCellValueFactory(new PropertyValueFactory<>("Combo"));

        ScoreList.setEditable(true);

        // Set each column to be editable, and specifically the type of editing that can occur
        NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        DifficultyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ComboColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Set the list to an empty list
        ScoreList.setItems(FXCollections.observableArrayList());

        // ************************** CHOICE BOX SETUP *************************
        ObservableList<String> SongNames = FXCollections.observableArrayList("Click Load Songs");
        NameChoice.setItems(SongNames);
        DifficultyChoice.getItems().addAll("Beginner","Basic","Difficult","Expert","Challenge");
        DifficultyChoice.setValue("Basic");
        ComboChoice.getItems().addAll("Pass","Fail","FC","GFC","PFC","MFC");
        ComboChoice.setValue("Pass");
        ScoreValue.setText("0");

        // ************************** BUTTON SETUP *************************

        EnterScoreButton.setDisable(true);
        MakeImageButton.disableProperty().bind(Bindings.isEmpty(ScoreList.getSelectionModel().getSelectedItems()));


        // ************************** SCORE VALUE LAMBDA *************************

        ScoreValue.setOnKeyTyped(
                event ->{
                if(!ScoreValue.getText().isEmpty()) {
                    String scoreString = ScoreValue.getText();
                    boolean only_numbers = true;
                    char[] chars = scoreString.toCharArray();
                    for (char c : chars) {
                        if (!Character.isDigit(c)) {
                            only_numbers = false;
                        }
                    }
                    if (!only_numbers) {
                        EnterScoreButton.setDisable(true);
                    }
                    else{
                        int score = Integer.parseInt(scoreString);
                        // Validating a score in DDR is more complex, but would require
                        // the user to know metadata about their steps or
                        // for the program to extract the metadata from an xml file
                        // in the appdata for the program in a specific user file
                        EnterScoreButton.setDisable(score < 0 || score > 1000000);
                    }
                }
                else{
                    EnterScoreButton.setDisable(true);
                }
        }
        );

    }
}
