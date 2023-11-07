package logic.state;

import logic.GameStateManager;
import logic.Logic;
import logic.audio.AudioPlayer;
public class WonState extends GameState{

    //Scelta e opzioni menu'
    private int currentChoice = 0;
    private String[] options = {"Continue (Level 2)","Menu","Quit"};
    private String[] options2 = {"Retry","Menu","Quit"};
    //Animazione background
    private int i = 0;
    private int dim = 1;

    private AudioPlayer selectSound, won;
    private String path;

    public WonState(GameStateManager gsm, String bkg) {
        this.gsm = gsm;
        this.path = bkg;
        init();
    }

    public void init() {
        selectSound = new AudioPlayer("/SFX/select.wav");
        selectSound.setVolume(gsm.prefs.getInt("VOLUME", 50));

        won = new AudioPlayer("/SFX/won.wav");
        won.setVolume(100);
        won.play();

        Logic.getInstance().setBackground( path, 320, 240, false);
    }

    public void update() {
        //Do nothing
    }

    @Override
    public void draw() {

        // draw bg
        Logic.getInstance().drawBackground();
        //draw opzioni e scelta
        if(gsm.getPrecState() == gsm.LEVELSTATE2)
        	Logic.getInstance().drawChoice(options2, currentChoice);
        else
        	Logic.getInstance().drawChoice(options, currentChoice);
        //draw score e best score
        Logic.getInstance().drawTextAtPosition("HAI VINTOOOO!!!", 125, 125, 40);
        Logic.getInstance().drawScore(gsm.prefs.get("best_name", "Billy"), gsm.prefs.getInt("best_score", 1000), gsm.prefs.get("player", "default"));


    }

    private void select() {

        if(currentChoice == 0) { //Continue
            gsm.setState(gsm.LEVELSTATE2);
        }
        if(currentChoice == 1) { //Menu
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if(currentChoice == 2) { //Exit
            System.exit(0);
        }
        currentChoice = 0;
    }

    @Override
    public void keyPressed(int k) {

        if(k == gsm.prefs.getInt("ENTER", 0)){
            selectSound.play();
            select();
        }
        if(k == gsm.prefs.getInt("UP", 0)){
            currentChoice--;
            selectSound.play();
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(k == gsm.prefs.getInt("DOWN", 0)){
            currentChoice++;
            selectSound.play();
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
        //Do nothing
    }

    @Override
    public void mouseClicked(int x, int y) {
        System.out.println(x + " "+y);
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
