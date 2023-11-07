package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private static final long serialVersionUID = 1L;
    // dimensioni del frame
    public static final int WIDTH = 600;
    public static final int HEIGHT = 700;

    // game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    // canvas dove andiamo a disegnare sopra
    private BufferedImage image;
    private Graphics2D g;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            //Creo il thread e aggiungo i listener
            addKeyListener(View.getInstance());
            addMouseListener(View.getInstance());
            addMouseMotionListener(View.getInstance());
            thread.start();
        }
    }

    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        running = true;

        View.getInstance().CreateGSM();
    }

    public void run() {

        init();

        long start;
        long elapsed;
        long wait;

        long st1, st2, st3;
        int n = 0;

        // game loop
        while(running) {

            start = System.nanoTime();

            update();

            //Per controllare le prestazioni durante il gioco
            st1 = System.nanoTime();
            draw();
            st2 = System.nanoTime();
            drawToScreen();
            st3 = System.nanoTime();

            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if(wait < 0) wait = 0;

            try {
                Thread.sleep(wait);
            }
            catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    private void update() {
        View.getInstance().GSMupdate();
        try{
            View.getInstance().getLocationOnScreen(getLocationOnScreen());
        }catch(Exception e){
            //Do nothing
        }
    }

    private void draw() {
        View.getInstance().GSMdraw(g);
    }
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT,null);
        g2.dispose();
    }
}
