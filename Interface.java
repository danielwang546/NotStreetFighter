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

public class Interface {

    private int healthBarWidthP1;
    private int healthBarWidthP2;
    
    public Interface(int h1, int h2 ){
        healthBarWidthP1 = h1 * 7;
        healthBarWidthP2 = h2 * 7;
    }

    public void setHealthBar(int p1, int p2){
        healthBarWidthP1 = p1 * 7;
        healthBarWidthP2 = p2 * 7;
    }

    public void drawHealth(Graphics window){
        window.setColor(Color.RED);
        window.fillRect(25, 10, healthBarWidthP1, 30);
        window.fillRect(825, 10, healthBarWidthP2, 30);
        //window.drawString("Player 1 health: " + healthBarWidthP1/7, 725, 20);
    }

}
