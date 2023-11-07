package view.loadImages;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.imageio.ImageIO;

public class BubbleImg {

    //Raggio
    public static final int RADIUS = 20;
    //Colore
    Color color;

    private Image bubble;

    //Array di colori
    ConstantColorsConverter conv;

    //Posizione del top left corner
    public Point loc;

    //Visibilità
    private boolean visible;

    public BubbleImg(int c){
        conv = new ConstantColorsConverter();
        this.color = conv.isColor(c);
        visible = true;

        try {
        	String path = "/Bubbles/"+c+".png";
        	this.bubble = ImageIO.read(getClass().getResourceAsStream(path));

        }catch(Exception e) {
            //JOptionPane.showMessageDialog(null, "Error loading background image", "Error", JOptionPane.ERROR_MESSAGE);
        	System.out.println(e);
        }
    }

    public void setVisible(Boolean v){ this.visible = v;}

    public Boolean isVisible(){return visible;}

    public Color getColor(){return this.color;}

    public void setLocation(int x, int y){
        Point p = new Point(x,y);
        this.loc = p;
    }

    public Image getImage(){
        return bubble;
    }


}
