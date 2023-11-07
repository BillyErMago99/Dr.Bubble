package logic;

import java.util.prefs.Preferences;

import logic.state.GameState;
import logic.state.GameoverState;
import logic.state.LevelState;
import logic.state.LevelState2;
import logic.state.MenuState;
import logic.state.OptionsState;
import logic.state.PauseState;
import logic.state.WonState;
public class GameStateManager {

    private GameState[] gameStates;

    //Costanti del gsm
    public static final int NUMGAMESTATES = 6;
    public static final int MENUSTATE = 0;
    public static final int LEVELSTATE = 1;
    public static final int LEVELSTATE2 = 2;
    public static final int WONSTATE = 3;
    public static final int GAMEOVERSTATE = 4;
    public static final int OPTIONSSTATE = 5;

    //Parametri per LEVELSTATE
    public String[] bgPath = {"/Backgrounds/sfondo1.jpg", "/Backgrounds/sfondo2.jpg"};
    private String[] cannonPath = {"/Cannons/cannone1.png", "/Cannons/cannone2.png"};

    //Informazioni su quale livello caricare
    private int currentState;
    private int precState;
   
    public int level = 0;

    //Numero di livelli totali
    public int NUMBERS_OF_LEVELS;

    //Parametri per menu pausa
    private boolean paused;
    private PauseState pauseState;

    //Parametri per menu end
    private boolean gameover;
    //private GameOverState gameoverState;

    //Preferences
    public Preferences prefs;

    public GameStateManager() {

        //Carico preferences
        prefs = Logic.getInstance().getPreferences();
        NUMBERS_OF_LEVELS = prefs.getInt("Numbers of levels", 1);

        gameStates = new GameState[NUMGAMESTATES];
        pauseState = new PauseState(this);
        paused = false;

        //Imposto lo STATE di partenza
        currentState = MENUSTATE;
        precState = MENUSTATE;
        loadState(currentState);

    }

    //Carica il livello passatogli
    private void loadState(int state){

            if (state == MENUSTATE)
                gameStates[state] = new MenuState(this);
            if (state == OPTIONSSTATE)
                gameStates[state] = new OptionsState(this);
            if (state == LEVELSTATE) {
                //prende lo sfondo e il cannone dalle scelte del menu, quindi dalle preferences
                gameStates[state] = new LevelState(this, bgPath[prefs.getInt("BKG", 0)],
                        cannonPath[prefs.getInt("CANNON", 0)]);
            }
            if (state == LEVELSTATE2) {
                //prende lo sfondo e il cannone dalle scelte del menu, quindi dalle preferences
                gameStates[state] = new LevelState2(this, bgPath[prefs.getInt("BKG", 0)],
                        cannonPath[prefs.getInt("CANNON", 0)]);
            }
            if(state == WONSTATE)
                gameStates[state] = new WonState(this, bgPath[prefs.getInt("BKG", 0)]);
            
            if(state == GAMEOVERSTATE)
                gameStates[state] = new GameoverState(this, bgPath[prefs.getInt("BKG", 0)]);

        

        }

    //Tolgo il livello
    private void unloadState(int state) {
        gameStates[state]=null;
    }

    //Ritorna il livello corrente
    public int getState() {
        return currentState;
    }

    //Setto il livello
    public void setState(int state) {
        unloadState(currentState);
        precState = currentState;
        currentState = state;
        loadState(currentState);
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public void update(){

        prefs = Logic.getInstance().getPreferences();

        //Se pause vero -> fermo il gioco (metto in pausa)
        if(paused) {
            pauseState.update();
            return;
        }
        else{
            //Altrimenti aggiorno il livello
            try {
                gameStates[currentState].update();
            }catch(NullPointerException e) {
                //Per dare tempo di caricare il livello
                System.out.println("Sto caricando ...");
            }
        }

    }

    public void draw() {
        //Se pause vero -> disegno lo stato di pausa
        if(paused) {
            pauseState.draw();
            return;
        }else{
            //Altrimenti disegno il livello corrente
            try {
                gameStates[currentState].draw();
            }catch(NullPointerException e) {
                //Per dare tempo di caricare il livello
                System.out.println("Inizio a disegnare il livello "+ currentState);
            }
        }

    }

    //Se pause vero -> passo il valore allo stato PAUSE
    //Altrimenti passo il valore allo stato corrente
    public void keyPressed(int k) throws NullPointerException {

        if(paused) {
            pauseState.keyPressed(k);
            return;
        }
        gameStates[currentState].keyPressed(k);
    }

    public void keyReleased(int k) throws NullPointerException  {
        if(paused) {
            pauseState.keyReleased(k);
            return;
        }
        gameStates[currentState].keyReleased(k);
    }

    public void mouseClicked(int x, int y) throws NullPointerException {
        if(paused) {
            pauseState.mouseClicked(x, y);
            return;
        }
        gameStates[currentState].mouseClicked(x, y);
    }

    public void mouseMoved(int x, int y) throws NullPointerException {
        if(paused) {
            pauseState.mouseMoved(x, y);
            return;
        }
        gameStates[currentState].mouseMoved(x, y);
    }

    //Fa ripartire la musica del livello corrente
    public void restartMusic() throws NullPointerException {
        gameStates[currentState].restartMusic();
    }

    //Prendo il livello di partenza dalle preferences
    public void setLevel() {
        this.level = prefs.getInt("Level start", 1);
    }

    public Boolean getPause(){
        return paused;
    }
    
    public int getPrecState() {
    	return precState;
    }

}
