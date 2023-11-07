package logic.state;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.GameStateManager;
import logic.Logic;
import logic.audio.AudioPlayer;
import logic.utils.Bubble;
import logic.utils.Constants;
import logic.utils.MovingBubble;
import logic.utils.Point;
import logic.utils.RowList;

public class LevelState extends GameState{

    //Audio player
    private AudioPlayer bgMusic, addrow, attach, fire, pop;

    // events
    private boolean blockInput = false;
    private int eventCount = 0;

    private boolean eventStart;
    private boolean eventWon;
    private boolean eventFinish;
    private boolean paused;

    private String bgPath;
    private String cannonPath;

    //Numero di righe iniziali
    private int row;

    //Numero di colori delle bubble
    private int colors;

    //Contenitore delle bubble presenti
    private ArrayList<RowList> bubbles;

    //Contenitore delle 4 prossime bubbles
    private LinkedList<Bubble> upcoming;

    //Bubble in movimento sullo schermo
    private MovingBubble moving_bubble;

    //Numero di righe iniziali del livello
    private int initial_rows;

    //Numero dei possibili colori
    private int colorsNumber;

    //Numero di volte in cui non sono scoppiate bolle
    //Quando raggiunge 5 aggiunge una riga sopra
    private int shotCount;

    //Numero di bubble sullo schermo
    private int numOfBubbles;

    //Punteggio
    private long score;

    //Numero massimo di file di bubble sullo schermo
    public static final int ROW_COUNT = 16;

    //Numero massimo di bubble in una riga
    public static final int COL_COUNT_FULL = 14;

    //Numero massimo di bolle che non riempiono il campo in larghezza
    public static final int COL_COUNT = 13;

    //Punteggio per aver sparato una bubble
    public static final int SCORE_SHOT = 10;

    //Punteggio per aver rimosso bolle dello stesso colore del colpo
    public static final int SCORE_COHERENT = 20;

    //Punteggio per aver rimosso bubble che non sono più attaccate alle altre
    public static final int SCORE_FLOATING = 40;

    public LevelState(GameStateManager gsm, String bgPath, String cannonPath) {
        this.gsm = gsm;
        this.bgPath = bgPath;
        this.cannonPath = cannonPath;
        this.row = initial_rows = gsm.prefs.getInt("rows", 4);
        this.colors = gsm.prefs.getInt("colors", 4);
        shotCount = 0;
        numOfBubbles = 0;
        score = 0;
        bubbles = new ArrayList<RowList>();
        init();
    }

    @Override
    public void init() {

        //inizializzazione background
        Logic.getInstance().setBackground(bgPath, 320, 240, false);
        Logic.getInstance().setCannonImg(cannonPath);

        //Inizializzazione bubble
        for(int i = 0; i<ROW_COUNT; i++){
            RowList r = new RowList((i%2==0 ? true : false));
            bubbles.add(r);
            for(int j=0; j<(r.isFull() ? 14 : 13); j++){

                Bubble b = new Bubble(Bubble.getRandomColor(colors));
                b.setLocation(
                        new Point(r.isFull() ?
                                j*2*(Bubble.RADIUS+1) :
                                j*2*(Bubble.RADIUS+1)+(Bubble.RADIUS+1),
                                r.isFull() ?
                                        (i/2)* Constants.ROW_DISTANCE :
                                        (i/2)*Constants.ROW_DISTANCE+Constants.ROW_DISTANCE/2));
                r.add(b);
                if(i<initial_rows){
                    b.setVisible(true);
                    numOfBubbles++;
                }
                else
                    b.setVisible(false);
            }
        }

        //Inizializzazione bubbles in arrivo
        upcoming = new LinkedList<Bubble>();
        for(int i=0; i<4; i++){
            Bubble b = new Bubble(Bubble.getRandomColor(colors));
            upcoming.add(b);
        }
        arrangeUpcoming();

        //inizializzo musica
        
        bgMusic = new AudioPlayer("/SFX/game.wav");
        bgMusic.setVolume(100);
        bgMusic.playContinuosly();

        addrow = new AudioPlayer("/SFX/addrow.wav");
        addrow.setVolume(gsm.prefs.getInt("VOLUME", 50));

        attach = new AudioPlayer("/SFX/attach.wav");
        attach.setVolume(gsm.prefs.getInt("VOLUME", 50));

        fire = new AudioPlayer("/SFX/fire.wav");
        fire.setVolume(gsm.prefs.getInt("VOLUME", 50));

        pop = new AudioPlayer("/SFX/pop.wav");
        pop.setVolume(100);
        
        // start event
        eventStart = true;
        paused = false;
        eventStart();
    }

    public void update(){
        //Salvo il punteggio
        Logic.getInstance().setScore(getScore());

        if(paused) {
            Logic.getInstance().setBackground(bgPath, 320, 240, false);
            Logic.getInstance().setCannonImg(cannonPath);
            paused = false;
        }

        //Aggiorna posizione moving bubble
        if (moving_bubble!=null && moving_bubble.isMoving()){
            moving_bubble.move();
            checkProximity();
        }
    }

    public void draw(){

        //Disegna background
        Logic.getInstance().drawBackground();

        //Disegna linea e cannone
        Logic.getInstance().drawCannonLine();

        //Disegna rowlist
        for(RowList r : bubbles){
            for(Bubble b : r){
               Logic.getInstance().drawBubble(b.getName());
           }
        }

        //Disegna upcoming
        for(Bubble b : upcoming){
            Logic.getInstance().drawBubble(b.getName());
        }

        //Disegna MovingBubble
        if (moving_bubble!=null && moving_bubble.isMoving()){
            Logic.getInstance().drawBubble(moving_bubble.getName());
        }

        //Disegna punteggio
        Logic.getInstance().drawTextAtPosition(Long.toString(getScore()), 96, 603, 20);

    }

    private void arrangeUpcoming(){
        upcoming.element().setLocation(
                new Point(Constants.FIELD_SIZE_X/2-Bubble.RADIUS,
                        Constants.FIELD_SIZE_Y-Bubble.RADIUS));
        upcoming.element().setVisible(true);
        for (int i=1; i<4; i++){
            upcoming.get(i).setLocation(new Point(
                    Constants.FIELD_SIZE_X-(4-i)*(2*(Bubble.RADIUS+6)),
                    Constants.FIELD_SIZE_Y-(Bubble.RADIUS+1)));
            upcoming.get(i).setVisible(true);
        }
    }

    //Spara la prima bubble nella upcoming list
    public void fire(Point mouseLoc, Point panelLoc){
        boolean movingExists = !(moving_bubble==null);
        movingExists = (movingExists ? moving_bubble.isMoving() : false);
        if(!movingExists){
            fire.play();
            Point dir = new Point(mouseLoc.x-panelLoc.x,
                    mouseLoc.y-panelLoc.y);
            Bubble b = upcoming.remove();
            moving_bubble = new MovingBubble(b,dir);
            upcoming.add(new Bubble(Bubble.getRandomColor(colors)));
            arrangeUpcoming();
            numOfBubbles++;
            score+=SCORE_SHOT;
        }
    }

    //Controllo se la bubble è vicino ad un'altra presente nello schermo
    //se si la fisso nella griglia
    public void checkProximity(){
        int currentPosX = moving_bubble.getCenterLocation().x;
        int currentPosY = moving_bubble.getCenterLocation().y;
        int row = (currentPosY-Bubble.RADIUS)/(Constants.ROW_DISTANCE/2);
        int col;
        if (row < ROW_COUNT){
            if (bubbles.get(row).isFull()){
                col = (currentPosX)/((Bubble.RADIUS+1)*2);
            }
            else{
                col = (currentPosX-(Bubble.RADIUS+1))/((Bubble.RADIUS+1)*2);
            }
            if(row == 0){
                fixBubble(row, col);
            }
            ArrayList<Bubble> neighbours = getNeighbours(row, col);
            for (Bubble b : neighbours){
                if (b.isVisible() && BubbleDist(moving_bubble, b)<=4+(Bubble.RADIUS+1)*2){
                    fixBubble(row, col);
                    break;
                }
            }
        }
    }

    //Posiziono la bubble nella posizione della griglia
    private void fixBubble(int row, int col){
        Point temp_point = bubbles.get(row).get(col).getLocation();
        moving_bubble.setLocation(temp_point);
        bubbles.get(row).set(col, moving_bubble);
        moving_bubble.setMoving(false);
        int removed = removeCoherent(row, col) + removeFloating();
        numOfBubbles-=removed;

        if(removed == 0){
            attach.play();
            shotCount++;
        }
        if(shotCount == 5){
            shotCount = 0;
            addrow.play();
            addRow();
        }

        if(numOfBubbles == 0){
            score*=1.2;
            if(score > gsm.prefs.getInt("best_score", 1000)){
                gsm.prefs.putInt("best_score", (int)score);
                gsm.prefs.put("best_name", gsm.prefs.get("player", "default"));
            }
            bgMusic.close();
            gsm.setState(gsm.WONSTATE);
        }

        for(Bubble b : bubbles.get(ROW_COUNT-1)){
            if(b.isVisible()){
                score*=0.8;
                bgMusic.close();
                gsm.setState(gsm.GAMEOVERSTATE);
                break;
            }
        }
    }

    //Aggiungi una riga sopra
    private void addRow(){
        bubbles.remove(ROW_COUNT-1);
        for (RowList r : bubbles){
            for (Bubble b : r){
                b.setLocation(new Point(b.getLocation().x,
                        b.getLocation().y+Constants.ROW_DISTANCE/2));
            }
        }
        RowList newRow = new RowList(!bubbles.get(0).isFull());
        for (int i = 0; i< (newRow.isFull() ? 14 : 13); i++){
            Bubble b = new Bubble(Bubble.getRandomColor(colors));
            b.setLocation(
                    new Point((newRow.isFull() ?
                            i*2*(Bubble.RADIUS+1) :
                            i*2*(Bubble.RADIUS+1)+(Bubble.RADIUS+1)),0));
            b.setVisible(true);
            newRow.add(b);
            numOfBubbles++;
        }
        bubbles.add(0,newRow);
    }

    //Restituisci le bubble vicine nella griglia
    private ArrayList<Bubble> getNeighbours(int row, int col){
        ArrayList<Bubble> neighbours = new ArrayList<Bubble>();
        //LEFT
        if (col>0) neighbours.add(bubbles.get(row).get(col-1));
        //RIGHT
        if (col < (bubbles.get(row).isFull() ? COL_COUNT_FULL : COL_COUNT)-1){
            neighbours.add(bubbles.get(row).get(col+1));
        }
        //UPPER LEFT
        if (bubbles.get(row).isFull() && col > 0 && row > 0){
            neighbours.add(bubbles.get(row-1).get(col-1));
        }
        if (!bubbles.get(row).isFull() && row > 0){
            neighbours.add(bubbles.get(row-1).get(col));
        }
        //UPPER RIGHT
        if (bubbles.get(row).isFull() && col < COL_COUNT_FULL-1 && row > 0){
            neighbours.add(bubbles.get(row-1).get(col));
        }
        if (!bubbles.get(row).isFull() && row > 0 && col < COL_COUNT_FULL-1){
            neighbours.add(bubbles.get(row-1).get(col+1));
        }
        //LOWER LEFT
        if (bubbles.get(row).isFull() && col > 0 && row < ROW_COUNT-1){
            neighbours.add(bubbles.get(row+1).get(col-1));
        }
        if (!bubbles.get(row).isFull() && row < ROW_COUNT-1){
            neighbours.add(bubbles.get(row+1).get(col));
        }
        //LOWER RIGHT
        if (bubbles.get(row).isFull() && col < COL_COUNT_FULL-1 && row < ROW_COUNT-1){
            neighbours.add(bubbles.get(row+1).get(col));
        }
        if (!bubbles.get(row).isFull() && row < ROW_COUNT-1 && col < COL_COUNT_FULL-1){
            neighbours.add(bubbles.get(row+1).get(col+1));
        }
        return neighbours;
    }

    //Ritorna la distanza tra due buuble
    public static double BubbleDist(Bubble b1, Bubble b2){
        double x_dist = b1.getCenterLocation().x-b2.getCenterLocation().x;
        double y_dist = b1.getCenterLocation().y-b2.getCenterLocation().y;
        return Math.sqrt(Math.pow(x_dist, 2)+Math.pow(y_dist, 2));
    }

    //Rimuovi le bubble dello stesso colore
    private int removeCoherent(int row, int col){
        unMarkAll();
        markColor(row, col);
        int ret = 0;
        if(countMarked()>2){
            ret = countMarked();
            removeMarked();
        }
        unMarkAll();
        score+=ret*SCORE_COHERENT;
        return ret;
    }

    //Rimuovi le bubble che rimarrebbero in aria
    private int removeFloating(){
        markAll();
        for (Bubble b : bubbles.get(0)){
            if(b.isVisible()){
                unMarkNotFloating(b.getRow(), b.getCol());
            }
        }
        int ret = countMarked();
        removeMarked();
        unMarkAll();
        score+=ret*SCORE_FLOATING;
        return ret;
    }

    //Deseleziona gli elementi che non rimangono appesi
    private void unMarkNotFloating(int row, int col){
        bubbles.get(row).get(col).unmark();
        for (Bubble b : getNeighbours(row, col)){
            if (b.isMarked() && b.isVisible()){
                unMarkNotFloating(b.getRow(), b.getCol());
            }
        }
    }

    //Segna le bolle dello stesso colore collegate
    private void markColor(int row, int col){
        bubbles.get(row).get(col).mark();
        for (Bubble b : getNeighbours(row, col)){
            if(b.isVisible() && !b.isMarked()){
                if (b.getColor() == bubbles.get(row).get(col).getColor()){
                    markColor(b.getRow(), b.getCol());
                }
            }
        }
    }

    //Conta le bubble segnate
    private int countMarked() {
        int ret = 0;
        for(RowList r : bubbles){
            for(Bubble b : r){
                if (b.isMarked() && b.isVisible()){
                    ret++;
                }
            }
        }
        return ret;
    }

    //Deseleziona tutte le bubble
    private void unMarkAll(){
        for(RowList r : bubbles){
            for(Bubble b : r){
                b.unmark();
            }
        }
    }

    //Segna tutte le bubble
    private void markAll(){
        for(RowList r : bubbles){
            for(Bubble b : r){
                b.mark();
            }
        }
    }
    //Rimuovi le bubble marcate
    private void removeMarked(){
        pop.play();
        for(RowList r : bubbles){
            for(Bubble b : r){
                if(b.isMarked()){
                    b.setVisible(false);
                }
            }
        }
    }

    //Ritorna lo score corrente
    public long getScore(){
        return score;
    }
    @Override
    public void keyPressed(int k) {
        //Se pausa -> blocca input
    	
		if(k == gsm.prefs.getInt("ESCAPE", 27)) {
            bgMusic.stop();
            paused = true;
            gsm.setPaused(true);
        }
		
        if(k == gsm.prefs.getInt("UP",0) && !paused){
        	bgMusic.increaseVolume();
        }

        if(k == gsm.prefs.getInt("DOWN",0) && !paused){
            bgMusic.decreaseVolume();
        }

    }

    @Override
    public void keyReleased(int k) {

    }

    // level started
    private void eventStart() {
        //player.setDead(false);
        if(eventCount == 0) {
            eventStart = blockInput = false;
            bgMusic.playContinuosly();
        }
    }

    private void reset() {
        //player.reset();
        blockInput = true;
        eventCount = 0;
        eventStart = true;
        eventStart();
    }

    // finished level
    private void eventFinish() {
        eventCount++;
        //System.out.println(eventCount);
    }

    @Override
    public void mouseClicked(int x, int y) {

        if(y > 575){
            int diff = y - 575;
            y = 575 - diff;
        }
        fire(new Point(x,y), new Point(0,0));
        
    }

    @Override
    public void mouseMoved(int x, int y) {
    }

    @Override
    public void restartMusic() {
        bgMusic.play();
    }


}
