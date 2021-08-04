package ucf.assignments;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WriteBufferedImage {
    public void writeToFile(BufferedImage completedScore, String songTitle, String songDifficulty,String absolutePath) {
        // Directory folder, Song Name and Difficulty
        File OutputFile = new File(absolutePath + "\\" + songTitle
            + " " + songDifficulty + ".png");
        try{
            // write the image to a file. Will override the previous version
            ImageIO.write(completedScore,"png",OutputFile);
        }
        catch(IOException e){
            System.out.println("File was not found for some reason.");
        }
    }
}
