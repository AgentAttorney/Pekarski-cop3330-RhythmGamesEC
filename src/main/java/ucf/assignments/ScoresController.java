package ucf.assignments;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public void ClearSelectionPushed(ActionEvent event){
        ScoreList.getSelectionModel().clearSelection();
    }

    public void EnterScorePushed(ActionEvent event){
        Score score = new Score(NameChoice.getValue(),DifficultyChoice.getValue(),ScoreValue.getText(),ComboChoice.getValue());
        boolean match_Score = Score.matchingScore(score,ScoreList.getItems());
        if(!NameChoice.getValue().isEmpty() && !DifficultyChoice.getValue().isEmpty() && !ComboChoice.getValue().isEmpty() && !match_Score){
            // add Item
            ScoreList.getItems().add(score);
        }
    }
    @FXML
    public void changeScoreValueEdit(TableColumn.CellEditEvent<Score,String> editedCell){
        Score score_selected = ScoreList.getSelectionModel().getSelectedItem();
        String  new_score = editedCell.getNewValue();
        boolean was_changed = Score.ChangeValue(score_selected,new_score);
        if(!was_changed){
            ScoreList.refresh();
        }
    }
    @FXML
    public void changeComboValueEdit(TableColumn.CellEditEvent<Score,String> editedCell){
        Score score_selected = ScoreList.getSelectionModel().getSelectedItem();
        String new_combo = editedCell.getNewValue();
        boolean was_changed = Score.ChangeCombo(score_selected,new_combo);
        if(!was_changed){
            ScoreList.refresh();
        }
    }

    @FXML
    public void MakeImageButtonPushed(ActionEvent event){
        Stage stage = (Stage) ScoreList.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Location of Images");
        File file = dc.showDialog(stage);
        if(file != null){
            // Write the image here using my old code
            FindSongNameList.OutputImage(file.getAbsolutePath(),ScoreList.getSelectionModel().getSelectedItem());
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
                    } else if (score.getCombo().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else {
                        return false;
                    }

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
        NameColumn.setCellValueFactory(new PropertyValueFactory<Score,String>("Name"));
        DifficultyColumn.setCellValueFactory(new PropertyValueFactory<Score,String>("Difficulty"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<Score,String>("Score"));
        ComboColumn.setCellValueFactory(new PropertyValueFactory<Score,String>("Combo"));

        ScoreList.setEditable(true);

        // Set each column to be editable, and specifically the type of editing that can occur
        NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        DifficultyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ComboColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Set the list to an empty list
        ScoreList.setItems(FXCollections.observableArrayList());

        // ************************** CHOICE BOX SETUP *************************

        ObservableList<String> SongNames = FindSongNameList.FindNames();
        NameChoice.setItems(SongNames);
        DifficultyChoice.getItems().addAll("Beginner","Basic","Difficult","Expert","Challenge");
        ComboChoice.getItems().addAll("Pass","Fail","FC","GFC","PFC","MFC");

        // ************************** BUTTON SETUP *************************

        EnterScoreButton.setDisable(false);
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
                        if ((score >= 0 && score <= 1000000)) {
                            // Validating a score in DDR is more complex, but would require
                            // the user to know metadata about their steps or
                            // for the program to extract the metadata from an xml file
                            // in the appdata for the program in a specific user file
                            EnterScoreButton.setDisable(false);
                        }
                        else{
                            EnterScoreButton.setDisable(true);
                        }
                    }
                }
        }
        );

    }
}
