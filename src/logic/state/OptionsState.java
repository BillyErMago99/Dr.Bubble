package logic.state;

import logic.GameStateManager;
import logic.Logic;
import logic.audio.AudioPlayer;

public class OptionsState extends GameState {

    // dimensioni del frame
    public static final int W = 320;
    public static final int H = 240;
    public static final int S = 3;
    //Scelta corrente e scelte disponibili
    private int currentChoice = 0;
    private String[] options = {"Enter your nickname:","Chose your cannon:","Chose your world:"};
    private String[] names = {"White", "Black", "Wormhole", "Valle Incantata"};
    private String player;

    //Background path
    public String bg = "/Backgrounds/conf.jpg";
    //Suono selezione
    private AudioPlayer selectSound;

    public OptionsState(GameStateManager gsm) {
        this.gsm = gsm;
        Logic.getInstance().setBackground(bg, 320, 240, true);
        selectSound = new AudioPlayer("/SFX/select.wav");
        //selectSound.setVolume(gsm.prefs.getInt("VOLUME", 50));
        player = gsm.prefs.get("player", "default");
    }

    public void init() {

    }

    public void update() {

        if(Logic.getInstance().getNameOfBackground() != bg){
            Logic.getInstance().setBackground(bg, 320, 240, true);
        }

    }

    public void draw() {

        // draw bg
        Logic.getInstance().drawBackground();
        //draw opzioni e scelta
        Logic.getInstance().drawConfiguration(options, names, gsm.prefs.get("player", "default"), gsm.prefs.getInt("BKG", 0), gsm.prefs.getInt("CANNON", 0));

    }

    private void select() {

    }

    public void keyPressed(int k) {
        if(k == gsm.prefs.getInt("ESCAPE", 27)) {
            //bgMusic.stop();
            gsm.setState(gsm.MENUSTATE);
        }
    }

    public void keyReleased(int k) {
        //Do nothing
    }

    @Override
    public void mouseClicked(int x, int y) {
        
        if((x > 75 && x < 145)&&(y > 160 && y < 180))
            gsm.prefs.putInt("CANNON", 0);
        if((x > 350 && x < 420)&&(y > 160 && y < 180))
            gsm.prefs.putInt("CANNON", 1);
        if((x > 75 && x < 180)&&(y > 380 && y < 400))
            gsm.prefs.putInt("BKG", 0);
        if((x > 345 && x < 535)&&(y > 380 && y < 400))
            gsm.prefs.putInt("BKG", 1);

        if((x > 355 && x < 450)&&(y > 42 && y < 62)){
            String txt = Logic.getInstance().createDialogInputWindow("Inserisci il nome giocatore");
            System.out.println(txt);
            gsm.prefs.put("player", txt);
        }

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