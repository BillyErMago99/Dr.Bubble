package logic.utils;

import logic.Logic;

public class Bubble {

    //Per creare nome univoco bubble
    protected static int numBubble = 0;
    private String name;
    //Colore
    private int color;
    //Raggio
    public static final int RADIUS = 20;

    //Visibilità
    private boolean visible;

    //Posizione del top left corner
    Point loc;

    //Marker
    private boolean marked;

    public Bubble(int c){

        Bubble.numBubble ++;
        this.name = "bubble"+ numBubble;
        Logic.getInstance().createBubble(name, c);
        this.marked = false;
        this.color = c;
    }

    //Ritorna le coordinatre della bubble nella matrice
    public int getRow(){
        return loc.y/(Constants.ROW_DISTANCE/2);
    }
    public int getCol(){
        return loc.x/((Bubble.RADIUS+1)*2);
    }
    //Marca la bubble
    public void mark(){
        marked=true;
    }

    //Togli il marchio
    public void unmark(){
        marked = false;
    }

    //Ritrona se marchiata
    public boolean isMarked(){
        return marked;
    }

    //Prendi il colore della bolla
    public int getColor(){
        return color;
    }
    //Visibilità
    public void setVisible(boolean v){

        visible = v;
        Logic.getInstance().setBubbleVisibility(name,v);
    }
    public boolean isVisible(){
        return visible;
    }

    //Posizione top left corner
    public void setLocation(Point p){

        this.loc=p;
        Logic.getInstance().setBubbleLocation(name, p.getX(), p.getY());
    }
    public Point getLocation(){
        return loc;
    }

    //Posizione centro
    public Point getCenterLocation(){
        return new Point(loc.x+RADIUS+1, loc.y+RADIUS+1);
    }

    public static int getRandomColor(int bound){
        int rnd = (int) (bound<=8 ? Math.random()*bound : Math.random()*8);
        return rnd;
    }

    public String getName(){return name;}

}
