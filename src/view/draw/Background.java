package view.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.imageio.ImageIO;

import logic.utils.Constants;
import view.GamePanel;
import view.loadImages.BubbleImg;

public class Background {

    //private BufferedImage background = null;

    // dimensioni del frame
    public static int WIDTH = GamePanel.WIDTH;
    public static int HEIGHT = GamePanel.HEIGHT;

    public static final int FIELD_SIZE_X = 600;
    public static final int FIELD_SIZE_Y = 600;
    //Parametri arrow
    private static final int TIP_LENGTH = 20;
    private static final int LENGTH = 80;
    private Point p = new Point(Constants.FIELD_SIZE_X/2, 0);

    private Image img;
    private Image cannonImg;
    private Image white, black, wormhole, valle;
    private double scaleFactor;
    //Posizione
    private double x;
    private double y;

    //Specifiche background
    private String path;

    private boolean menu;

    public Background(String path, int width, int height, Boolean menu) {
        this.path = path;
        this.menu = menu;

        try {
          
        	this.img = ImageIO.read(getClass().getResourceAsStream(path));
        	this.black = ImageIO.read(getClass().getResourceAsStream("/Cannons/cannone2.png"));            
        	this.white = ImageIO.read(getClass().getResourceAsStream("/Cannons/cannone1.png"));            
        	this.wormhole = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/sfondo1scaled.png"));
        	this.valle = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/sfondo2scaled.png"));
        	
    
        }catch(Exception e) {
        	System.out.println(e);
            //JOptionPane.showMessageDialog(null, "Error loading background image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCannonImage(String cannonPath){
        try{
        	this.cannonImg = ImageIO.read(getClass().getResourceAsStream(cannonPath));
        }catch(Exception e) {
        	System.out.println(e);
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * scaleFactor);
        this.y = (y * scaleFactor);
    }

    public void setVector(double dx, double dy) {
        //Do nothing
    }

    public void update() {
        //Do nothing
    }

    public void draw(Graphics2D g) {

        g.drawImage(img, (int)x, (int)y, null);
    }

    public void clearBackground(Graphics2D g, Color color) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(color);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void drawChoice(Graphics2D g, String[] options, int currentChoice) {

        Font myFont = new Font ("Courier New", 1, 20);
        g.setFont(myFont);

        for(int i = 0; i < options.length; i++) { //Menu state
            if(i == currentChoice) {
                g.setColor(Color.YELLOW);
            }
            else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 87*2 + 50, 120*3 + i * 15*4);
        }
        g.setColor(Color.WHITE);
        
    }

    public String getName() {
        return path;
    }

    public void drawTextAtPosition(Graphics2D g, String text, int x, int y, int size) {
        Font myFont = new Font ("Courier New", 1, size);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString(text,x,y);
    }

    public void drawBubble(Graphics2D g, BubbleImg b) {
        if(b.isVisible()){
            //g.setColor(b.getColor());
            //g.fillOval(b.loc.x, b.loc.y, b.RADIUS*2, b.RADIUS*2);
            g.drawImage(b.getImage(), b.loc.x-3, b.loc.y-5, null);
        }
    }

    public void drawCannon(Graphics2D g, Point base) {
        g.setColor(Color.red);
        java.awt.Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        int x = mouseLoc.x-base.x;
        int y = mouseLoc.y-base.y;
        if(y > 575){
            y=575;
            x = -x;
        }

        if((0<=x) && (x<FIELD_SIZE_X) && (0<=y) && (y<FIELD_SIZE_Y)){
            p=mouseLoc;
        }
        x = p.x-base.x;
        y = p.y-base.y;
        double angle = Math.atan((double)(x-FIELD_SIZE_X/2)/(FIELD_SIZE_Y-y));
        g.rotate(angle, FIELD_SIZE_X/2,FIELD_SIZE_Y);
        g.drawLine(FIELD_SIZE_X/2, FIELD_SIZE_Y,
                FIELD_SIZE_X/2, FIELD_SIZE_Y-LENGTH);
        g.drawLine(FIELD_SIZE_X/2, FIELD_SIZE_Y-LENGTH,
                FIELD_SIZE_X/2-TIP_LENGTH, FIELD_SIZE_Y-LENGTH+TIP_LENGTH);
        g.drawLine(FIELD_SIZE_X/2, FIELD_SIZE_Y-LENGTH,
                FIELD_SIZE_X/2+TIP_LENGTH, FIELD_SIZE_Y-LENGTH+TIP_LENGTH);
        g.rotate(-angle,FIELD_SIZE_X/2,FIELD_SIZE_Y);

        g.drawImage(cannonImg, 242, 605
                , null);
    }

    public void drawScore(Graphics2D g, String best_player, int best_score, String player, String score) {
        Font myFont = new Font ("Courier New", 1, 20);
        g.setFont(myFont);
        g.drawString(best_player, 87*2 + 50, 220);
        g.drawString(String.valueOf(best_score), 320, 220);
        g.drawString(player, 87*2 + 50, 280);
        g.drawString(score, 320, 280);
    }

    public void drawConfiguration(Graphics2D g, String[] options, String[] names, String player, int bkg, int cannon) {
        g.setColor(Color.WHITE);
        Font myFont = new Font ("Courier New", 1, 20);
        g.setFont(myFont);
        g.drawString(options[0], 80, 60);
        g.drawString(player, 360, 60);
        g.drawString(options[1], 80, 120);
        g.drawString(options[2], 80, 360);

        if(cannon == 0){
            g.setColor(Color.yellow);
            g.drawString(names[0], 80, 180);
            g.setColor(Color.white);
            g.drawString(names[1], 350, 180);
        }else{
            g.setColor(Color.white);
            g.drawString(names[0], 80, 180);
            g.setColor(Color.yellow);
            g.drawString(names[1], 350, 180);
        }

        if(bkg == 0){
            g.setColor(Color.yellow);
            g.drawString(names[2], 80, 400);
            g.setColor(Color.white);
            g.drawString(names[3], 350, 400);
        }else{
            g.setColor(Color.white);
            g.drawString(names[2], 80, 400);
            g.setColor(Color.yellow);
            g.drawString(names[3], 350, 400);
        }


        g.drawImage(white, 80, 200, null );
        g.drawImage(black, 350, 200, null);
        g.drawImage(wormhole, 80, 410, null );
        g.drawImage(valle, 350, 410, null );
    }
}
