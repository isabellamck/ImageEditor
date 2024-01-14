import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class ImageEditorPanel extends JPanel implements KeyListener {

    Color[][] pixels;

    public ImageEditorPanel() {
        BufferedImage imageIn = null;
        try {
            imageIn = ImageIO.read(new File("images.jpg"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        pixels = makeColorArray(imageIn);
        setPreferredSize(new Dimension(pixels.length, pixels[0].length));
        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }
        System.out.println("Loaded image: width: " +width + " height: " + height);
        return result;
    }

    public Color[][] flipHorizontal(Color[][] pixels){
        
        Color[][] horizontal = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++){
                horizontal[r][c] = pixels[r][pixels[r].length-c-1];
            }
        }
        return horizontal;
    }

     public Color[][] flipVertical(Color[][] pixels){
        
        Color[][] vertical = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
              vertical[r][c] = pixels[pixels.length - r - 1][c]; 
            }
            
        }
        return vertical;
    }
    public Color[][] grayscale(Color[][] pixels){
      
        Color[][] gray = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < pixels.length; r++) {
          for (int c = 0; c < pixels[0].length; c++) {
            Color colour = pixels[r][c];
            int colorVal = (colour.getRed() + colour.getGreen() + colour.getBlue()) / 3;
            gray[r][c] = new Color(colorVal, colorVal, colorVal);
            }
        }
      return gray;

   } 
    public Color[][] blur(Color[][] pixels, int radius) {
        
        Color[][] blurred = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < blurred.length; r++) {
          for (int c = 0; c < pixels[0].length; c++) {
            int red = 0;
            int green = 0;
            int blue = 0;
            int count = 0;
                for (int i = 0 - radius; i < radius; i++) {
                    for (int j = 0 - radius; j < radius ; j++) {
                   if(i <= pixels.length && j <= pixels[0].length && i >= 0 && j >= 0){
                        count++;
                        red += pixels[r + i][c + j].getRed();
                        green += pixels[r + i][c + j].getGreen();
                        blue += pixels[r + i][c + j].getBlue();
                    }   
                    red = red/count;
                    green = green/count;
                    blue = blue/count;
                    blurred[r][c] = new Color(red, green, blue);
                }
            }
          }
        }
        return blurred;
    }
    public Color[][] rotate90(Color[][] pixels){
        Color[][] rotate = new Color[pixels[0].length][pixels.length];
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
              rotate[c][r] = pixels[r][c]; 
            }
            
        } 
        return rotate;
    }
    public Color[][] contrast(Color[][] pixels){
     
        Color[][] contrast= new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < pixels.length; r++) {
          for (int c = 0; c < pixels[0].length; c++) {
            Color colour = pixels[r][c];
            if(colour.getRed() <= 160 && colour.getBlue() <= 160){
            contrast[r][c] = new Color(colour.getRed() + 95, colour.getGreen(), colour.getBlue() + 95);
            }else{
                contrast[r][c] = new Color(colour.getRed() , colour.getGreen(), colour.getBlue());
                }
            }
        }
      return contrast;
   }
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' '){
            pixels = rotate90(pixels);
            setPreferredSize(new Dimension(800,600));
            JFrame jf = (JFrame)SwingUtilities.getAncestorOfClass(JFrame.class, this);
            jf.pack();


        }
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) { 
    }
    @Override
    public void keyReleased(KeyEvent e) {  
    } 
    public Color[][] blackWhite(Color[][] pixels){
        Color[][] blackWhite = new Color[pixels.length][pixels[0].length];
    
        for (int r = 0; r < pixels.length; r++) {
          for (int c = 0; c < pixels[0].length; c++) {
            Color colour = pixels[r][c];
            int red = colour.getRed();
            int green = colour.getGreen();
            int blue = colour.getBlue();
            if (red < 127){
                red = 0;
            }else{
                red = 255;
            }
            if (green < 127){
                green = 0;
            }else{
                green = 255;
            }
            if (blue < 127){
                blue = 0;
            }else{
                blue = 255;
            }

            blackWhite[r][c] = new Color(red, green, blue);
            }
        }
        return blackWhite;
        }
    }

// fix dimension
//finish blur
//add to github
// save edited file as jpg

