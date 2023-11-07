package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import logic.Logic;
import view.draw.Background;
import view.loadImages.BubbleImg;

public class View implements IView{
    //Istanza
    private static View instance = null;
    //Elementi grafici
    public Background bg = null;
    public GameWindow gameWindow;
    public Graphics2D g;
    private String score = "0";

    private BubbleImg moving_bubble;

    public HashMap<String, BubbleImg> bubbles;

    public Point locationOnScreen;
    public View(){
        bubbles = new HashMap<>();
    }

    public void createGameWindow() {
        gameWindow = new GameWindow();
    }

    //Background
    @Override
    public void setBackground(String path, int width, int height, Boolean menu) {
        this.bg = new Background(path, width, height, menu);
    }

    @Override
    public void setVectorBackground(double dx, double dy) {
        bg.setVector(dx, dy);
    }

    @Override
    public void drawBackground() {
        bg.draw(g);
    }

    @Override
    public void clearBackground() {
        bg.clearBackground(g, Color.black);
    }

    @Override
    public String getNameOfBackground() {
        return bg.getName();
    }

    //Logic -> GSM
    public void CreateGSM() {
        Logic.getInstance().createGSM();
    }

    @Override
    public void GSMdraw(Graphics2D g) {
        this.g = g;
        Logic.getInstance().GSMDraw();
    }

    @Override
    public void GSMupdate() {
        Logic.getInstance().GSMUpdate();
    }

    @Override
    public void drawChoice(String[] options, int currentChoice) {
        bg.drawChoice(g, options, currentChoice);
    }

    @Override
    public void drawTextAtPosition(String s, int x, int y, int size) {
        bg.drawTextAtPosition(g, s, x, y, size);
    }

    @Override
    public String createDialogInputVindow(String text) {
        return gameWindow.inputWindow(text);
    }

    @Override
    public void createMessageWindow(String text) {
        gameWindow.messageWindow(text);
    }

    @Override
    public void createBubble(String name, int color) {
        bubbles.put(name, new BubbleImg(color));
    }

    @Override
    public void setBubbleVisibility(String name, boolean v) {
        bubbles.get(name).setVisible(v);
    }

    @Override
    public void drawBubble(String name) {
        bg.drawBubble(g, bubbles.get(name));
    }

    @Override
    public void setBubbleLocation(String name, int x, int y) {
        bubbles.get(name).setLocation(x, y);
    }

    @Override
    public void drawCannonLine(){
        bg.drawCannon(g, locationOnScreen);
    }

    @Override
    public void getLocationOnScreen(Point location) {
        this.locationOnScreen = location;
    }

    @Override
    public void setCannonImg(String cannonPath) {
        bg.setCannonImage(cannonPath);
    }

    @Override
    public void setScore(long score) {
        this.score = Long.toString(score);
    }

    @Override
    public void drawScore(String best_player, int best_score, String player) {
        bg.drawScore(g, best_player, best_score, player, this.score);
    }

    @Override
    public void drawConfiguration(String[] options, String[] names, String player, int bkg, int cannon) {
        bg.drawConfiguration(g, options, names, player, bkg, cannon);
    }

    @Override
    public String createDialogInputWindow(String string) {
        return gameWindow.inputWindow(string);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            Logic.getInstance().keyPressed(e.getKeyCode());
        }catch(NullPointerException ee) {
            //Do nothing
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Logic.getInstance().keyReleased(e.getKeyCode());
        }catch(NullPointerException ee) {
            //Do nothing
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Logic.getInstance().mouseClicked(e.getX(), e.getY());
        }catch(NullPointerException ee) {
            //Do nothing
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseMoved(MouseEvent e){
        try {
            Logic.getInstance().mouseMoved(e.getX(), e.getY());
        }catch(NullPointerException ee) {
            //Do nothing
        }
    }

    //Metodi statici
    public static IView getInstance(){
        if(instance == null)
            instance = new View();
        return instance;
    }

}
