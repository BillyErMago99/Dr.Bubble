package logic;

import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

import view.View;

public class Logic implements ILogic{

    //Variabili statiche della classe
    private static Logic instance = null;
    public static GameStateManager gsm;

    //Preferences
    public Preferences prefs;

    public Logic(){}

    //Metodi per GSM
    @Override
    public void createGSM() {
        gsm = new GameStateManager();
    }

    @Override
    public void GSMUpdate() {
        gsm.update();
    }

    @Override
    public void GSMDraw() {
        gsm.draw();
    }

    @Override
    public int getCurrentState() {
        return gsm.getState();
    }

    //Metodi per input
    @Override
    public void keyPressed(int key) {
        gsm.keyPressed(key);
    }

    @Override
    public void keyReleased(int key) {
        gsm.keyReleased(key);
    }

    @Override
    public void mouseMoved(int x, int y) {
        gsm.mouseMoved(x, y);
    }

    @Override
    public void mouseClicked(int x, int y) {
        gsm.mouseClicked(x, y);
    }

    public void importPreferences() {

        prefs = Preferences.userNodeForPackage(Logic.class);
        System.out.println("Cerco preferences");
        //Controllo se esiste gi� il file preferences per importare i propri settaggi
        if(prefs.getBoolean("exists", false))
            System.out.println("File preferences esistente");
        else {
            //Creo file preferences con impostazioni di default (vedere su README.txt)
            System.out.println("Creo file preferences");
            importDefaultPreferences();

            try {
                //Stampo le preferences
                prefs.exportSubtree(System.out);

            }catch (Exception e) {
                System.out.println("Impossibile stampare le preferences "+ e.getMessage());
            }
        }
    }

    @Override
    public void importDefaultPreferences() {

        prefs.putInt("UP", KeyEvent.VK_UP);
        prefs.putInt("DOWN", KeyEvent.VK_DOWN);
        prefs.putInt("FIRE", KeyEvent.VK_F);
        prefs.putInt("ESCAPE", KeyEvent.VK_ESCAPE);
        prefs.putInt("ENTER", KeyEvent.VK_ENTER);
        prefs.putInt("VOLUME", 80);
        prefs.putInt("EFFECTS_VOLUME", 80);
        prefs.putBoolean("exists", true);
        prefs.putInt("CANNON", 0);
        prefs.putInt("BKG", 0);
        prefs.putInt("Numbers of levels", 2);
        prefs.putInt("Level start", 1);
        prefs.putInt("rows", 4);
        prefs.putInt("colors", 4);
        prefs.putInt("best_score", 1000);
        prefs.put("best_name", "Billy");
        prefs.put("player", "default");

    }

    @Override
    public Preferences getPreferences() {
        return prefs;
    }

    //Crea una finestra con un messaggio specifico
    @Override
    public void createMessageWindow(String text) {
        View.getInstance().createMessageWindow(text);
    }

    @Override
    public String createDialogInputWindow(String string){
        return View.getInstance().createDialogInputWindow(string);
    }

    @Override
    public void createGameWindow() {
        importPreferences();
        View.getInstance().createGameWindow();
    }

    //View -> background
    @Override
    public void setBackground(String path, int width, int height, boolean menu) {
        View.getInstance().setBackground(path, width, height, menu);
    }

    @Override
    public void drawBackground() {
        View.getInstance().drawBackground();
    }

    @Override
    public void clearBackground() {
        View.getInstance().clearBackground();
    }

    @Override
    public String getNameOfBackground() {
        return View.getInstance().getNameOfBackground();
    }

    @Override
    public void drawTextAtPosition(String text, int x, int y, int size) {
        View.getInstance().drawTextAtPosition(text, x, y, size);
    }

    @Override
    public void drawChoice(String[] options, int currentChoice) {
        View.getInstance().drawChoice(options, currentChoice);
    }

    @Override
    public void createBubble(String name, int color) {
        View.getInstance().createBubble(name, color);
    }

    @Override
    public void drawBubble(String name) {
        View.getInstance().drawBubble(name);
    }

    @Override
    public void drawCannonLine() {
        View.getInstance().drawCannonLine();
    }

    @Override
    public void setBubbleLocation(String name, int x, int y) {
        View.getInstance().setBubbleLocation(name, x, y);
    }

    @Override
    public void setBubbleVisibility(String name, boolean v) {
        View.getInstance().setBubbleVisibility(name, v);
    }

    @Override
    public void setCannonImg(String cannonPath) {
        View.getInstance().setCannonImg(cannonPath);
    }

    @Override
    public void setScore(long score) {
        View.getInstance().setScore(score);
    }

    @Override
    public void drawScore(String best_player, int best_score, String player) {
        View.getInstance().drawScore(best_player, best_score, player);
    }

    @Override
    public void drawConfiguration(String[] options, String[] names, String player, int bkg, int cannon) {
        View.getInstance().drawConfiguration(options, names, player, bkg, cannon);
    }

    //Metodi statici
    public static ILogic getInstance() {
        if(instance == null)
            instance = new Logic();
        return instance;
    }

}
