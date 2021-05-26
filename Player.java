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

public class Player extends GameElement{

    private int xSpeed;
    private int ySpeed;
    private int yAcceleration;
    private Image image;
    
    public Player(){
        super();
        xSpeed = 0;
        ySpeed = 0;
        yAcceleration = 1;
        

        try{
            URL url = getClass().getResource("ship.jpg");
            image = ImageIO.read(url);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void move(int x, int y){
       setX(getX() + x);
       setY(getY() + y);
    }

    public void draw(Graphics window){
        //applyGravity();
        move(xSpeed, ySpeed);
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }

    public void setXSpeed(int s){
        xSpeed = s;
    }

    public void setYSpeed(int s){
        ySpeed = s;
    }

    public void setYAcelleration(int s){
        yAcceleration = s;
    }

    public int getXSpeed(){
        return xSpeed;
    }

    public int getYSpeed(){
        return ySpeed;
    }

    public int getYAccelteration(){
        return yAcceleration;
    }

    public void applyGravity(){
        //if(touchingfloor){
            //ySpeed = 0;
        //}
        //else{
            ySpeed += yAcceleration;
        //}
    }

}
