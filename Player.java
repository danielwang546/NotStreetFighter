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

public class Player extends gameElement{

    private int speed;
    private Image image;
    
    public Player(){
        super();
        speed = 2;

        try{
            URL url = getClass().getResource("ship.jpg");
            image = ImageIO.read(url);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void move(String direction){
        if(direction.equals("LEFT")){
            setX(getX() - speed);
        } else if(direction.equals("RIGHT")){
            setX(getX() + speed);
        } else if(direction.equals("UP")){
            setY(getY() - speed);
        } else if(direction.equals("DOWN")){
            setY(getY() + speed);
        }
    }

    public void draw(Graphics window){
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }
}
