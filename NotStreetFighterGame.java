import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.net.URL;
import javax.swing.*;

import java.awt.*;
import javax.imageio.*;
import java.util.Arrays;

public class NotStreetFighterGame extends Canvas implements KeyListener, Runnable 
{
    private long beforeTime, deltaTime, currTime, initTime;
    private int counter = 0;

    private boolean[] keys;
    private boolean[] tapKeys;
    private BufferedImage back;
    private Player player1;

    private int[] keyCodes = {
        KeyEvent.VK_ENTER,
        KeyEvent.VK_SHIFT,
        KeyEvent.VK_W,
        KeyEvent.VK_A,
        KeyEvent.VK_S,
        KeyEvent.VK_D,
        KeyEvent.VK_UP,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_RIGHT
    };

    private int[] tapKeyCodes = {
        KeyEvent.VK_SPACE
    };

    private ArrayList<Player.PlayerState> p1Queue;

    public NotStreetFighterGame() {
        setBackground(Color.WHITE);

        beforeTime = 0;
        initTime = System.currentTimeMillis();

        keys = new boolean[keyCodes.length];
        tapKeys = new boolean[tapKeyCodes.length];

        player1 = new Player();

        this.addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        Image im = null;
        try {
            URL url = getClass().getResource("Person.png");
            im = ImageIO.read(url);
        } catch(Exception e) {

        }
        currTime = System.currentTimeMillis();
        deltaTime = currTime - beforeTime;
        double frameRate = ((int)(100000.0/deltaTime))/100.0; //magic to get the framerate in Hz, truncated to 2 decimals

        Graphics2D twoDGraph = (Graphics2D) window;
        if(back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));

        Graphics graphToBack = back.createGraphics();
        //Overwrites screen with white every frame
        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(0, 0, getWidth(), getHeight());

        graphToBack.setColor(Color.BLACK);

        graphToBack.drawImage(im, 100, 100, 100, 100, null);
        graphToBack.drawString(frameRate + " FPS", 5, 10);
        
        

        if(keys[0]){
             player1.move(-player1.getXSpeed(), 0); 
        }
        if(keys[1]) {
              player1.move(player1.getXSpeed(), 0);
        }
        if(keys[2]) {
              player1.move(0, -player1.getYSpeed());
        }
        if(keys[3]) {
              player1.move(0, player1.getYSpeed());
        }

        player1.draw(graphToBack);

        if(currTime - initTime < 10000 && counter < 10000) {
            counter++;
        } else {
            graphToBack.drawString(counter + " frames in 10s", 5, 30);
        }

        //draws everything from graphToBack to the image (put all draws before this line)
        twoDGraph.drawImage(back, null, 0, 0);

        beforeTime = currTime;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < keyCodes.length; i++) {
            if(e.getKeyCode() == keyCodes[i]) {
                keys[i] = true;
            }
        }  

    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(int i = 0; i < keyCodes.length; i++) {
            if(e.getKeyCode() == keyCodes[i]) {
                keys[i] = false;
            }
        }

        for(int i = 0; i < tapKeyCodes.length; i++) {
            if(e.getKeyCode() == tapKeyCodes[i]) {
                tapKeys[i] = true;
            } else {
                tapKeys[i] = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // empty method
    }

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(5);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
