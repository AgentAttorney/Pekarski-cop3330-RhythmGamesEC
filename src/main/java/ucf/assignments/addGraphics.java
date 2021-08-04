package ucf.assignments;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Formatter;

public class addGraphics {
    public BufferedImage makeGraphics(BufferedImage songBackground, String songTitle, String songDifficulty, String songScore, String songCombo) {
        Formatter format = new Formatter();
        format.format("%,d",Integer.parseInt(songScore)); // formatted score like 750,000

        BufferedImage product = songBackground; // set background
        Graphics2D g2d = product.createGraphics(); // create 2D Graphics on top of background

        // define Colors
        Color gold = new Color(218,165,32);
        Color light_blue = new Color(0,191,255);
        Color purple = new Color(128,0,128);
        Color dgreen = new Color(0,100,0);
        Color dred = new Color(204,0,0);

        // Make Title Info R.Rect.
        g2d.setColor(Color.white);
        g2d.fill(new RoundRectangle2D.Double(10,10,400,100,80,20));

        // Make Border for R.Rect.
        g2d.setStroke(new BasicStroke());
        g2d.setColor(Color.black);
        g2d.draw(new RoundRectangle2D.Double(10,10,400,100,80,20));

        // Make Title and Difficulty Text
        int fontSize = 30;
        g2d.setFont(new Font("TimesRoman",Font.PLAIN,fontSize));
        if(songCombo.equals("MFC")){ // orange was defined for MFCs in Input
            g2d.setColor(gold); // Will Change Song Name to Gold
        }

        g2d.drawString(songTitle,30,50);
        // based on Colors present in DDR Songs in the Arcade
        switch(songDifficulty){
            case "BEGINNER":
                g2d.setColor(light_blue);
                break;
            case "BASIC":
                g2d.setColor(Color.yellow);
                break;
            case "DIFFICULT":
                g2d.setColor(dred);
                break;
            case "EXPERT":
                g2d.setColor(dgreen);
                break;
            default:
                g2d.setColor(purple); //  CHALLENGE
        }
        g2d.drawString(songDifficulty,250,50);

        // Make Song Text
        g2d.setColor(Color.black);
        g2d.drawString("Score: ",200,85);
        // The colors of the FC types. Normally a colored circle next to the score, but I deviated
        switch(songCombo){
            case "FC":
                g2d.setColor(light_blue);
                break;
            case "GFC":
                g2d.setColor(dgreen);
                break;
            case "PFC":
            case "MFC":
                g2d.setColor(gold);
                break;
            default:
                g2d.setColor(Color.black); // pass or fail
        }
        g2d.drawString(String.valueOf(format),290,85);

        return product;
    }
}
