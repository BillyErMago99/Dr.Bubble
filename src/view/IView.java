package view;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface IView extends KeyListener, MouseListener, MouseMotionListener{

    //Background
    public void setBackground(String s, int width, int height, Boolean menu);
    public void setVectorBackground(double dx, double dy);
    public void drawBackground();
    public void clearBackground();
    public String getNameOfBackground();
    public void drawTextAtPosition(String s, int x, int y, int size);
    public void drawChoice(String[] options, int currentChoice);

    //Logic -> GSM
    public void CreateGSM();
    public void GSMupdate();
    public void GSMdraw(Graphics2D g);

    //GameWindow
    public void createGameWindow();
    public String createDialogInputVindow(String text);
    public void createMessageWindow(String text);

    void createBubble(String name, int color);

    void setBubbleVisibility(String name, boolean v);

    void drawBubble(String name);

    void setBubbleLocation(String name, int x, int y);

    void drawCannonLine();

    void getLocationOnScreen(Point locationOnScreen);

    void setCannonImg(String cannonPath);

    void setScore(long score);

    void drawScore(String best_player, int best_score, String player);

    void drawConfiguration(String[] options, String[] names, String player, int bkg, int cannon);

    String createDialogInputWindow(String string);
}
