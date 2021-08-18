package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class FindSongNameList {
        public static ObservableList<String> FindNames(Stage stage){

            // Create a Directory Chooser where the user selects where the system looks for
            // sub directories (i.e. the songs as each song is located in a folder sub-directory)
            ObservableList<String> NameList = FXCollections.observableArrayList();
            try {
                DirectoryChooser dc = new DirectoryChooser();
                File dir = dc.showDialog(stage);

                // Checks if each value in the selected directory is a sub-directory
                File[] files = dir.listFiles();
                FileFilter fileFilter = new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory();
                    }
                };
                files = dir.listFiles(fileFilter);

                // For each file in the array, Replace the full directory name with the low part of the directory
                // For example, "C:\Games\StepMania 5\Songs\Love Live!\DROPOUT" would turn into "DROPOUT"
                // add the SongName to the NameList, then return the NameList Once completed
                for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                    File filename = files[i];
                    String SongName = FindSongNameList.lowestDirectory(filename.toString());
                    NameList.add(SongName);
                }
            }
            catch(Exception ignored){

            }
                    return NameList;
        }

    private static String lowestDirectory(String fileNameRaw) {
            // Change the directory string so we can easily split the file directory into an array
            //  Get the last element of the array (which will contain the song name) and return it
            String song = fileNameRaw.replace("\\", "/");
            String[] song_words = song.split("/");
        return song_words[song_words.length - 1];
    }

    public static void OutputImage(String absolutePath, Score selectedItem, Stage stage) {
            // Get the info from the selected score
            String songTitle = selectedItem.getName();
            String songDifficulty = selectedItem.getDifficulty().toUpperCase();
            String songScore = selectedItem.getScore();
            String songCombo = selectedItem.getCombo();

            // Create class instances which will work with graphics
            CreateBuffImg get_bg = new CreateBuffImg();
            addGraphics Graphics = new addGraphics();
            WriteBufferedImage product = new WriteBufferedImage();

            // Make the Base Image by getting the background jacket for the song
            // Make the graphic that goes over top of our Base Image using the info from selected score
            // Write the score to a file
            BufferedImage songBackground = get_bg.getBG(songTitle,stage);
            BufferedImage CompletedScore = Graphics.makeGraphics(songBackground,songTitle,songDifficulty,songScore,songCombo);
            product.writeToFile(CompletedScore,songTitle,songDifficulty,absolutePath);

    }
}
