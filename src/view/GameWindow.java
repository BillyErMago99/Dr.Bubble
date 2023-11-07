package view;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameWindow {

    private Image img;
    public JFrame window;

    public GameWindow() {

        //Creo la finestra di gioco
        window = new JFrame("BubbleShooter");

        try{
            img = ImageIO.read(getClass().getResourceAsStream("/Icon/icona.jpg"));
            window.setIconImage(img);
            //window.setIconImage(new ImageIcon("Resources/Icon/icona.jpg").getImage());

        }catch(NullPointerException err) {
            messageWindow("Impossibile trovare l'icona nel percorso "+ "/Icon/icon.png");
        } catch (IOException e) {
            messageWindow("Impossibile caricare l'icona: "+ e.getMessage());
        }

        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    //Creo finestra per leggere input da tastiera
    public String inputWindow(String text) {

        return JOptionPane.showInputDialog(window, text, null);
    }

    //Visualizzo un messaggio a schermo
    public void messageWindow(String text) {
        JOptionPane.showMessageDialog(window, text);
    }
}
