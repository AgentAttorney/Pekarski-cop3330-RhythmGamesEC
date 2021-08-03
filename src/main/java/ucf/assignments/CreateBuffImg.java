package ucf.assignments;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateBuffImg {
    public BufferedImage getBG(String songTitle, Stage stage) {
        BufferedImage bi;
        try{
            DirectoryChooser dc = new DirectoryChooser();
            File file = dc.showDialog(stage);
            bi = ImageIO.read(new File(file + "/"
                    + songTitle +"/" + songTitle + "-jacket.png"));
            return bi;
        }
        catch(IOException e){
            System.out.println("The song does not exist,or was misspelled.");
        }
        return null; // should NEVER reach here due to catch
    }
}
