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
import java.io.File;
import java.io.FileInputStream;

public class Player extends GameElement{

    public enum PlayerState {
        //values will be filled in when more animations are added
        IDLE(1, "Idle"),
        CROUCHING(0, ""),
        IDLE_CROUCH(1, ""),
        WALKING(9, "Walk"),
        JUMPING(0, ""),
        PUNCHING(9, "Punch"),
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
    private int pID;

    private Image image;
    private int currFrame = 0;
    private PlayerState state;

    public Player(int id){
        super(400, 400, 170, 200);
        xSpeed = 0;
        ySpeed = 0;
        yAcceleration = 1;

        pID = id;
        
        state = PlayerState.WALKING;

        try{
            image = ImageIO.read(new FileInputStream(new File("Animations/Player" + pID + "Idle/Player" + pID + "Idle0000.png")));

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void move(int x, int y){
       setX(getX()+x);
       setY(getY()+y);
    }

    public void draw(Graphics window){
        
        move(xSpeed, ySpeed);
        updateImage();
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }

    private void updateImage() {
        //increments currFrame, possible values 0 - state.frames-1
        currFrame = (currFrame + 1) % state.frames;
        try{
            image = ImageIO.read(new FileInputStream(new File("Animations/Player" + pID + state.fileName + "/Player" + pID + state.fileName + "000" + currFrame + ".png")));
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
