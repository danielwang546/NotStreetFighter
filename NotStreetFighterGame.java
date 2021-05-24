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
import java.util.Arrays;

public class NotStreetFighterGame extends Canvas implements KeyListener, Runnable 
{

    private boolean[] keys;
    private boolean[] tapKeys;
    private boolean[] tapKeysPrev;
    private BufferedImage back;

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

    public NotStreetFighterGame() {
        setBackground(Color.WHITE);

        keys = new boolean[keyCodes.length];
        tapKeys = new boolean[tapKeyCodes.length];
        tapKeysPrev = new boolean[tapKeyCodes.length];

        this.addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        Graphics2D twoDGraph = (Graphics2D) window;
        if(back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));

        Graphics graphToBack = back.createGraphics();
        //Overwrites screen with white every frame
        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(0, 0, getWidth(), getHeight());

        graphToBack.setColor(Color.BLACK);
        graphToBack.drawString(Arrays.toString(tapKeys), 500, 300);
        graphToBack.drawString(Arrays.toString(tapKeysPrev), 500, 400);
        
        twoDGraph.drawImage(back, null, 0, 0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < keyCodes.length; i++) {
            if(e.getKeyCode() == keyCodes[i]) {
                keys[i] = true;
            }
        }  

        for(int i = 0; i < tapKeys.length; i++) {
            //only updates if tapKeysPrev was previously false and the key is pressed
            tapKeys[i] = !tapKeysPrev[i] && e.getKeyCode() == tapKeyCodes[i];
        }

        for(int i = 0; i < tapKeyCodes.length; i++) {
            if(e.getKeyCode() == tapKeyCodes[i]) {
                tapKeysPrev[i] = true;
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
                tapKeysPrev[i] = false;
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
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
