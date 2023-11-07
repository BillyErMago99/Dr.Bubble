package logic.state;

import logic.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;
    //Vite player
    protected int lives;
    //Metodi principali
    public abstract void init();
    public abstract void update();
    public abstract void draw();
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
    public abstract void mouseClicked(int x, int y);
    public abstract void mouseMoved(int x, int y);
    public abstract void restartMusic();

}