package ucf.assignments;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Formatter;

public class addGraphics {
    public BufferedImage makeGraphics(BufferedImage songBackground, String songTitle, String songDifficulty, String songScore, String songCombo) {
        Formatter format = new Formatter();
        format.format("%,d",Integer.parseInt(songScore)); // formatted score like 750,000

        String songTitleLC = songTitle.toLowerCase();
        String[] SongTitleSplit = songTitleLC.split(" ");
        StringBuffer sb = new StringBuffer();
        for(String word: SongTitleSplit){
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        String song_title_capitalized = sb.toString().trim();


        String songDifficultyCapital = songDifficulty.substring(0,1).toUpperCase() + songDifficulty.substring(1).toLowerCase();


        BufferedImage product = new BufferedImage(256,80,BufferedImage.TYPE_INT_RGB);
        BufferedImage resizeBG = scaleImage(songBackground,80,80);


         // set background
        Graphics2D g2d = product.createGraphics(); // create 2D Graphics on top of background
        g2d.drawImage(resizeBG,0,0,null);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // define Colors
        Color gold = new Color(218,165,32);
        Color light_blue = new Color(0,191,255);
        Color purple = new Color(128,0,128);
        Color dgreen = new Color(0,100,0);
        Color dred = new Color(204,0,0);
        Color NavyBlue = new Color(0, 0, 128);

        GradientPaint BlueToWhite = new GradientPaint(0,0,light_blue,256,80,Color.WHITE);
        g2d.setPaint(BlueToWhite);
        g2d.fill(new RoundRectangle2D.Double(80,0,176,80,10,10));

        // Make Title Info R.Rect.
        g2d.setColor(NavyBlue);
        g2d.fill(new RoundRectangle2D.Double(93,5,150,40,10,10));

        // Make Border for R.Rect.
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.black);
        g2d.draw(new Rectangle2D.Double(80,0,176,80));

        // Make Title and Difficulty Text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman",Font.BOLD,12));
        if(songCombo.equals("MFC")){ // orange was defined for MFCs in Input
            g2d.setColor(gold); // Will Change Song Name to Gold
        }

        g2d.drawString(song_title_capitalized,100,20);
        // based on Colors present in DDR Songs in the Arcade
        switch(songDifficultyCapital){
            case "Beginner":
                g2d.setColor(light_blue);
                break;
            case "Basic":
                g2d.setColor(gold);
                break;
            case "Difficult":
                g2d.setColor(dred);
                break;
            case "Expert":
                g2d.setColor(dgreen);
                break;
            default:
                g2d.setColor(purple); //  CHALLENGE
        }
        g2d.drawString(songDifficultyCapital,100,40);

        // Make Song Text
        g2d.setColor(Color.black);
        g2d.drawString("Score: ",98,70);
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
                g2d.setColor(Color.white); // pass or fail
        }
        g2d.drawString(songCombo,210,40);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Combo: ",167,40);

        g2d.setColor(Color.black);
        g2d.drawString(String.valueOf(format),143,70);

        g2d.dispose();

        return product;
    }

    private BufferedImage scaleImage(BufferedImage songBackground, int width, int height) {
        int imgWidth = songBackground.getWidth();
        int imgHeight = songBackground.getHeight();
        if(imgWidth*height < imgHeight*width){
            width = imgWidth*height / imgHeight;
        }
        else{
            height = imgHeight*width / imgWidth;
        }
        BufferedImage newBG = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newBG.createGraphics();
        try{
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(songBackground,0,0,width,height,null);
        }
        finally{
            g.dispose();
        }
        return newBG;

    }
}
