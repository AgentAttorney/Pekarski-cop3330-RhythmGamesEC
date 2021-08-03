package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileFilter;
import java.util.Objects;

public class FindSongNameList {
        public static ObservableList<String> FindNames(){
            ObservableList<String> NameList = FXCollections.observableArrayList();
            File dir = new File("C:/Games/StepMania 5/Songs/DDR Official Songs");
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
}
