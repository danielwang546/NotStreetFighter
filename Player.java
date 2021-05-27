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

    public enum PlayerState {
        //values will be filled in when more animations are added
        IDLE(0, ""),
        CROUCHING(0, ""),
        WALKING(9, "newGuyWalk"),
        JUMPING(0, ""),
        ATTACKING(0, ""),
        BLOCKING(0, "");


        private final int frames;
        private final String fileName;

        PlayerState(int frames, String fileName) {
            this.frames = frames;
            this.fileName = fileName;
        }

        private int frames() {
            return frames;
        }

        private String fileName() {
            return fileName;
        }
    }

    private int xSpeed;
    private int ySpeed;
    private int yAcceleration;

    private Image image;
    private int currFrame = 0;
    private PlayerState state;

    public Player(){
        super();
        xSpeed = 0;
        ySpeed = 0;
        yAcceleration = 1;
        
        state = PlayerState.IDLE;

        try{
            URL url = getClass().getResource("");
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
        //updateImage();
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }

    private void updateImage() {
        //increments currFrame, possible values 1 - state.frames
        currFrame = currFrame % state.frames + 1;
        try{
            URL url = getClass().getResource("/Animations/" + state.fileName + "/" + state.fileName + ".png");
            image = ImageIO.read(url);
        } catch(Exception e){
            e.printStackTrace();
        }
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

    public void setState(PlayerState state) {
        this.state = state;
        currFrame = 0;
    }

    public PlayerState getState() {
        return state;
    }
}
