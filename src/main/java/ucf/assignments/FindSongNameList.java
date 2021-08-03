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
import java.util.Objects;

public class FindSongNameList {
        public static ObservableList<String> FindNames(Stage stage){
            ObservableList<String> NameList = FXCollections.observableArrayList();
            DirectoryChooser dc = new DirectoryChooser();
            File dir = dc.showDialog(stage);
            File[] files = dir.listFiles();
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            };
            files = dir.listFiles(fileFilter);
            for(int i = 0; i< Objects.requireNonNull(files).length; i++){
                File filename = files[i];
                String SongName = FindSongNameList.lowestDirectory(filename.toString());
                //System.out.println(SongName);
                NameList.add(SongName);
            }
                    return NameList;
        }

    private static String lowestDirectory(String fileNameRaw) {
            String SongName = fileNameRaw.replace("C:\\Games\\StepMania 5\\Songs\\DDR Official Songs\\","");
            return SongName;
    }

    public static void OutputImage(String absolutePath, Score selectedItem, Stage stage) {
            String songTitle = selectedItem.getName();
            String songDifficulty = selectedItem.getDifficulty().toUpperCase();
            String songScore = selectedItem.getScore();
            String songCombo = selectedItem.getCombo();

            CreateBuffImg get_bg = new CreateBuffImg();
            addGraphics Graphics = new addGraphics();
            WriteBufferedImage product = new WriteBufferedImage();

            BufferedImage songBackground = get_bg.getBG(songTitle,stage);
            BufferedImage CompletedScore = Graphics.makeGraphics(songBackground,songTitle,songDifficulty,songScore,songCombo);
            product.writeToFile(CompletedScore,songTitle,songDifficulty,absolutePath);


    }
}
