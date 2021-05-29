
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
    private boolean[] stillPressed;
    private BufferedImage back;
    private Player player1;
    private Player player2;
    private Ground platform;

    private int[] keyCodes = {
        KeyEvent.VK_ENTER,
        KeyEvent.VK_SHIFT,
        KeyEvent.VK_A,
        KeyEvent.VK_S,
        KeyEvent.VK_D,
        KeyEvent.VK_UP,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_RIGHT
    };

    private int[] tapKeyCodes = {
        KeyEvent.VK_W,
        KeyEvent.VK_UP,
        KeyEvent.VK_SPACE
    };

    public NotStreetFighterGame() {
        setBackground(Color.WHITE);

        beforeTime = 0;
        initTime = System.currentTimeMillis();

        keys = new boolean[keyCodes.length];
        tapKeys = new boolean[tapKeyCodes.length];
        stillPressed = new boolean[tapKeyCodes.length];

        player1 = new Player(1, 400, 400);
        player2 = new Player(2, 1000, 400);
        player2.addState(Player.PlayerState.PUNCHING);

        platform = new Ground(0,600,1600,20);

        this.addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
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

        graphToBack.drawString(frameRate + " FPS", 5, 10);
        
        if(keys[2]) {
            player1.setXSpeed(-5);
            player1.setFacingRight(false);
            player1.addState(Player.PlayerState.WALKING);
            //player1.setState(Player.PlayerState.WALKING);
        }
        if(keys[4]) {
            player1.setFacingRight(true);
            player1.setXSpeed(5);
            player1.addState(Player.PlayerState.WALKING);
        }
        if (!keys[2] && !keys[4]) {
            player1.setXSpeed(0);
            //player1.addState(Player.PlayerState.IDLE);
        }

        if(keys[6]) {
            player2.setXSpeed(-5);
            player2.addState(Player.PlayerState.WALKING);
            player2.setFacingRight(false);
        }
        
        if(tapKeys[2]) {
        	player1.addState(Player.PlayerState.PUNCHING);
        	tapKeys[2] = false;
        }
        /*if(keys[7]) {
            player2.setYSpeed(1);
        }*/
        if(keys[8]) {
            player2.setXSpeed(5);
            player2.addState(Player.PlayerState.WALKING);
            player2.setFacingRight(true);
        }
        if (!keys[6] && !keys[8]) {
            player2.setXSpeed(0);
            //player2.addState(Player.PlayerState.IDLE);
        }

        

        if (!player1.touching(platform)) {
            player1.applyGravity();
        } else {
            player1.setYSpeed(0);
            if(tapKeys[0]){
                player1.setYSpeed(-20);
                tapKeys[0] = false;
            }
        }

        if (!player2.touching(platform)) {
            player2.applyGravity();
        } else {
            player2.setYSpeed(0);
            if(tapKeys[1]){
                player2.setYSpeed(-20);
                tapKeys[1] = false;
            }
        }

        if(currTime - initTime < 10000 && counter < 10000) {
            counter++;
        } else {
            graphToBack.drawString(counter + " frames in 10s", 5, 30);
        }

        player1.draw(graphToBack);
        player2.draw(graphToBack);
        platform.draw(graphToBack);

        //draws everything from graphToBack to the image (put all draws before this line)
        twoDGraph.drawImage(back, null, 0, 0);
        
        player1.printStates();

        beforeTime = currTime;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < keyCodes.length; i++) {
            if(e.getKeyCode() == keyCodes[i]) {
                keys[i] = true;
            }
        }
        for(int i = 0; i < tapKeyCodes.length; i++) {
        	
            if(e.getKeyCode() == tapKeyCodes[i] && !stillPressed[i]) {
                tapKeys[i] = true;
            }
            if(e.getKeyCode() == tapKeyCodes[i]) {
                stillPressed[i] = true;
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
                tapKeys[i] = false;
            }
            if(e.getKeyCode() == tapKeyCodes[i]) {
                stillPressed[i] = false;
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
