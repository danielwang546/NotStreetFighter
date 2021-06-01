import java.awt.Graphics;
import java.awt.Color;
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

public class Wall extends GameElement {
    public Wall(int x, int y, int w, int h) {
        super(x,y,0,0,w,h);
    }

    @Override
    public void move(int x, int y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics window) {
        window.setColor(Color.BLACK);
        window.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public boolean touching(Player other) {
        Rectangle thisRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX() + other.getXSpeed(), other.getY(), other.getWidth(), other.getHeight());
        return thisRect.intersects(otherRect);
    }
}
