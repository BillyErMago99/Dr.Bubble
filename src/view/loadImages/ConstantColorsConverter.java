package view.loadImages;

import java.awt.Color;

public class ConstantColorsConverter {

    private Color[] colors = {Color.blue, Color.red, Color.yellow, Color.green, Color.cyan,
            Color.magenta, Color.orange, Color.darkGray};

    public ConstantColorsConverter(){

    }

    public Color isColor(int c){
        return colors[c];
    }
}
