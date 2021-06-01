
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
    private Wall wall1;
    private Wall wall2;
    private Interface GUI;

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
        KeyEvent.VK_SPACE,
        KeyEvent.VK_ENTER
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

        platform = new Ground(0,600,1600,20);
        wall1 = new Wall(30,0,20,1200);
        wall2 = new Wall(1200,0,20,1200);

        GUI = new Interface(player1.getHealth(), player2.getHealth());

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
        
        //player1.openQueue();
        
        //movement determination is first
        if(keys[2]) {
            player1.setXSpeed(-5);
            player1.setFacingRight(false);
            player1.addState(Player.PlayerState.WALKING);
        }
        if(keys[4]) {
            player1.setFacingRight(true);
            player1.setXSpeed(5);
            player1.addState(Player.PlayerState.WALKING);
        }
        
        if(keys[6]) {
            player2.setXSpeed(-5);
            player2.addState(Player.PlayerState.WALKING);
            player2.setFacingRight(false);
        }
        if(keys[8]) {
            player2.setXSpeed(5);
            player2.addState(Player.PlayerState.WALKING);
            player2.setFacingRight(true);
        }
        
        
        //then comes non-interruptible states with attacking taking higher priority
        if(keys[3]) {
        	player1.setXSpeed(0);
            player1.crouch();
        }else {
        	player1.uncrouch();
        }
        if(keys[7]) {
        	player2.setXSpeed(0);
            player2.crouch();
        }else {
        	player2.uncrouch();
        }
        	
        
        if(tapKeys[2]) {
        	player1.addState(Player.PlayerState.PUNCHING);
        	tapKeys[2] = false;
        }
        if(tapKeys[3]) {
        	player2.addState(Player.PlayerState.PUNCHING);
        	tapKeys[3] = false;
        }
        
        
        //after that comes idle which depend on everything else that happened  
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
        if (!keys[2] && !keys[4]) {
            player1.setXSpeed(0);
            //player1.addState(Player.PlayerState.IDLE);
        }
        if (!keys[6] && !keys[8]) {
            player2.setXSpeed(0);
            //player2.addState(Player.PlayerState.IDLE);
        }
        
        player1.updateHitBox();
        player2.updateHitBox();
        
        
        //problems arise because these need to all be executed at the exact same time, not in sequence
        if(player1.getHitBox().touching(player2.getHitBox())) {
        	player1.setXSpeed(0);
        }
        if(player2.getHitBox().touching(player1.getHitBox())) {
        	player2.setXSpeed(0);
        }

        if(currTime - initTime < 10000 && counter < 10000) {
            counter++;
        } else {
            graphToBack.drawString(counter + " frames in 10s", 5, 30);
        }

        player1.draw(graphToBack);
        player2.draw(graphToBack);
        platform.draw(graphToBack);
        wall1.draw(graphToBack);
        wall2.draw(graphToBack);
        GUI.drawHealth(graphToBack);

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
                Thread.sleep(10);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
