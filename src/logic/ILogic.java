package logic;
import java.util.prefs.Preferences;
public interface ILogic {

    //GSM
    GameStateManager gsm = null;
    void createGSM();
    void GSMUpdate();
    void GSMDraw();
    int getCurrentState();

    //Preferences
    Preferences getPreferences();
    void importPreferences();
    void importDefaultPreferences();

    //View -> Listener
    void keyReleased(int key);
    void keyPressed(int key);
    void mouseClicked(int x, int y);
    void mouseMoved(int x, int y);



    void createMessageWindow(String text);
    String createDialogInputWindow(String string);

    void createGameWindow();

    //Background
    void setBackground(String path, int width, int height, boolean menu);
    void drawBackground();
    void clearBackground();
    String getNameOfBackground();
    void drawTextAtPosition(String text, int x, int y, int size);
    void drawChoice(String[] options, int currentChoice);

    void createBubble(String name, int color);

    void drawBubble(String name);

    void drawCannonLine();

    void setBubbleLocation(String name, int x, int y);

    void setBubbleVisibility(String name, boolean v);

    void setCannonImg(String cannonPath);

    void setScore(long score);

    void drawScore(String best_player, int best_score, String player);

    void drawConfiguration(String[] options, String[] names, String player, int bkg, int cannon);
}
