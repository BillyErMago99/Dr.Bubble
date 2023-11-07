package logic.state;

import logic.GameStateManager;
import logic.Logic;
import logic.audio.AudioPlayer;
public class PauseState extends GameState{
   
    //Scelta e opzioni menu'
    private int currentChoice = 0;
    private String[] options = {"Resume","Menu","Quit"};
    
    private AudioPlayer selectSound;

    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
        selectSound = new AudioPlayer("/SFX/select.wav");
    }

    public void init() {

    }

    public void update() {
        //Do nothing
    }

    @Override
    public void draw() {

        // draw bg
        Logic.getInstance().drawBackground();
        //draw opzioni e scelte
        Logic.getInstance().drawChoice(options, currentChoice);
        //draw score e best score
        Logic.getInstance().drawScore(gsm.prefs.get("best_name", "Billy"), gsm.prefs.getInt("best_score", 1000),
                gsm.prefs.get("player", "default"));
    }

    private void select() {


        if(currentChoice == 0) { //Continue
            //ritorna al gioco prima che mettessi pausa
            gsm.setPaused(false);
            gsm.restartMusic();
            
        }
        if(currentChoice == 1) { //Menu
            gsm.setPaused(false);
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if(currentChoice == 2) { //Exit
            System.exit(0);
        }
        currentChoice = 0;
    }

    @Override
    public void keyPressed(int k) {

        switch(k){
            case 10:
                selectSound.play();
                select();
                break;

            case 38:
                currentChoice--;
                selectSound.play();
                System.out.println(currentChoice);
                if(currentChoice == -1) {
                    currentChoice = options.length - 1;
                }
                break;

            case 40:
                currentChoice++;
                selectSound.play();
                if(currentChoice == options.length) {
                    currentChoice = 0;
                }
                break;

            default:
                System.out.println("Ma che problemi ti affliggono");
                break;

        }
    }

    @Override
    public void keyReleased(int k) {
        //Do nothing
    }

    @Override
    public void mouseClicked(int x, int y) {
        //Do nothing
    }

    @Override
    public void mouseMoved(int x, int y) {
        //Do nothing
    }

    @Override
    public void restartMusic() {
        //Do nothing
    }


}
