package logic.utils;

import java.util.ArrayList;

//Estensione della classe arrayList per contenere le bubble
public class RowList  extends ArrayList<Bubble> {

    private boolean full;

    public RowList(boolean full) {
        this.full = full;
    }

    public boolean isFull(){
        return full;
    }

    public void setFull(){
        full=true;
    }
}
