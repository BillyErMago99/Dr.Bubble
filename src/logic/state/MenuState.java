package logic.state;

import logic.GameStateManager;
import logic.Logic;
import logic.audio.AudioPlayer;

public class MenuState extends GameState {

        // dimensioni del frame
        public static final int W = 320;
        public static final int H = 240;
        public static final int S = 3;
        //Scelta corrente e scelte disponibili
        private int currentChoice = 0;
        private String[] options = {"","Personalize","Quit"};
        //Background path
        public String bg = "/Backgrounds/menu.png";
        //Suono selezione
        private AudioPlayer selectSound;

        private long startTime;
        //Livello di partenza
        private int LEVEL_START;

        public MenuState(GameStateManager gsm) {
            this.gsm = gsm;
            Logic.getInstance().setBackground(bg, 320, 240, true);
            selectSound = new AudioPlayer("/SFX/select.wav");
            //selectSound.setVolume(gsm.prefs.getInt("VOLUME", 50));
            this.LEVEL_START = gsm.prefs.getInt("Level start", 1);
            options[0]= "Start "+ "(level "+ LEVEL_START +")";
        }

        public void init() {
            this.LEVEL_START = gsm.prefs.getInt("Level start", 1);
            options[0]= "Start "+ "(level "+ LEVEL_START +" )";
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
            Logic.getInstance().drawChoice(options, currentChoice);

        }

        private void select() {
            if(currentChoice == 0) { //Play
                gsm.setLevel();
                gsm.setState(GameStateManager.LEVELSTATE);
            }
            if(currentChoice == 1) { //Options
                // options
                gsm.setState(GameStateManager.OPTIONSSTATE);
            }
            if(currentChoice == 2) { //Exit
                System.exit(0);
            }
        }

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
