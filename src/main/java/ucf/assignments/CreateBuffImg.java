package ucf.assignments;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateBuffImg {
    public BufferedImage getBG(String songTitle, Stage stage) {
        BufferedImage bi;
        try{
            // Create a directory chooser and once the user has chosen a directory to place the image
            // Return the buffer image that will return the jacket in the song folder
            // In an ideal scenario, I will make the image look for the banner (consistent size)
            FileChooser fc = new FileChooser();
            fc.setTitle("Select the Jacket or Background of " + songTitle);
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images (png,jpg,jpeg)","*.png","*.jpg","*.jpeg"));
            File file1 = fc.showOpenDialog(stage);
            bi = ImageIO.read(file1);
            return bi;
        }
        catch(Exception Ignored){
            // You didn't pick an actual song directory, may change to let you choose a new one
            // System.out.println("The song does not exist,or was misspelled.");
        }
        return null;
    }
}
