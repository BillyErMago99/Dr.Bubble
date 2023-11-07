package logic.utils;

import logic.Logic;

//Estende la classe Bubble rendendo le istanze capaci di muoversi
public class MovingBubble extends Bubble{
    private boolean moving;
    private double step_x;
    private double step_y;
    private double loc_x;
    private double loc_y;
    private static double STEP=5;

    private String name;

    public MovingBubble(Bubble b, Point dir) {
        super(b.getColor());
        name = "bubble"+ numBubble;
        loc = new Point(b.getLocation());
        loc_x = loc.x;
        loc_y = loc.y;
        setVisible(true);
        moving = true;
        double offset_x = dir.x-Constants.FIELD_SIZE_X/2;
        double offset_y = dir.y-Constants.FIELD_SIZE_Y;
        double dist = Math.sqrt(Math.pow(offset_x, 2)+Math.pow(offset_y, 2));
        step_x = offset_x/dist*STEP;
        step_y = offset_y/dist*STEP;
    }

    public boolean isMoving(){
        return moving;
    }

    public void setMoving(boolean x){
        moving = x;
    }
    public void move(){
        if(loc_x+step_x<0){
            loc_x=(int) -(loc_x+step_x);
            step_x=-step_x;
        }
        else if(loc_x+step_x>Constants.FIELD_SIZE_X-1-2*(Bubble.RADIUS+1)){
            loc_x=(int) ((Constants.FIELD_SIZE_X-1-2*(Bubble.RADIUS+1))*2-(loc_x+step_x));
            step_x=-step_x;
        }
        else
            loc_x+=step_x;
        loc_y+=step_y;
        loc.x = (int) loc_x;
        loc.y = (int) loc_y;
        Logic.getInstance().setBubbleLocation(name, loc.x, loc.y);
    }

}
