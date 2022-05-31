package view.main;

import model_old.action.Test2;
import states.GameStateManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen setting
    final int originalTitleSize = 16; // 16 x 16 size
    final int scale = 3;

    public final int titleSize = originalTitleSize * scale;
    public final int maxScreenCol = 32;     // 16
    public final int maxScreenRow = 16;     // 12
    public final int screenWidth = titleSize * maxScreenCol;   // 768px
    public final int screenHeight = titleSize * maxScreenRow;  // 576px
    final int FPS = 60;
    // debug
    private long maxDrawTime;
    private long counter;
    private int fps;

    Thread gameThread;
    private KeyHandler keyH = new KeyHandler(this);
    private MouseHandler mouseH = new MouseHandler(this);

    // world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = titleSize * maxWorldCol;
    public final int worldHeight = titleSize * maxWorldRow;
    GameStateManager gsm = new GameStateManager((Graphics2D) this.getGraphics(), this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // call in main
    public void setupGame () {
        gsm.setup();
        // ...when start game
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run () {
        double drawInterval = 1000000000f / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int drawCount = 0;
        int timer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime ) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta > 1) {
                System.out.println("X: " + mouseH.getX() + ", Y: " + mouseH.getY() + " -> " + mouseH.getButton());

                input(mouseH, keyH);
                update(currentTime);
                repaint();
                delta--;
                drawCount++;
            }

            if(timer > 1000000000) {
                // System.out.println("FPS: " + drawCount);
                fps = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(double time) {
        gsm.update(time);
        // ...etc
    }

    public void input (MouseHandler mouseH, KeyHandler keyH) {
        gsm.input(mouseH, keyH);
    }

    public void paintComponent (Graphics g) {
        // initial
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // config string
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(12F));

        // debug
        long drawStart = System.nanoTime();

        // Draw view.object
        gsm.render(g2);

        // debug
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        drawDebugInfo(g2, g, passed);

        g2.dispose();
    }

    public void drawDebugInfo (Graphics2D g2, Graphics g, long passed) {
        //
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        g2.drawString(passed/1000000f + "ms", this.screenWidth - 100, 28);
        g2.drawString("Draw time: ", this.screenWidth - 200, 28);

        Test2.commonNumber++;
        g.drawString("" + Test2.commonNumber,this.screenWidth - 100, 14);
        g.drawString("Value: ",this.screenWidth - 200, 14);

        counter++;
        if(maxDrawTime < passed || counter > 60) {
            counter = 0;
            maxDrawTime = passed;
        }
        g2.drawString(maxDrawTime/1000000f + "ms", this.screenWidth - 100, 42);
        g2.drawString("Max Draw time: ", this.screenWidth - 200, 42);

        g2.drawString(fps + "ms", this.screenWidth - 100, 56);
        g2.drawString("FPS: ", this.screenWidth - 200, 56);
    }

}