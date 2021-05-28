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

    public static enum PlayerState {
        //values will be filled in when more animations are added
        IDLE(1, 1, "Idle"),
        CROUCHING(9, 1, "Crouch"),
        IDLE_CROUCH(1, 1, ""),
        WALKING(9, 3, "Walk"),
        JUMPING(0, 1, ""),
        PUNCHING(9, 2, "Punch"),
        BLOCKING(0, 1, "");


        private final int frames;
        private final int frameTime;
        private final String fileName;

        PlayerState(int frames, int frameTime, String fileName) {
            this.frames = frames;
            this.frameTime = frameTime;
            this.fileName = fileName;
        }

        private int frames() {
            return frames;
        }

        private int frameTime() {
            return frameTime;
        }

        private String fileName() {
            return fileName;
        }
    }

    private int xSpeed;
    private int ySpeed;
    private int yAcceleration;
    private boolean facingRight = true;
    private int pID;

    private Image image;
    private int currFrame = 0;
    private int frameCount = 0;
    private PlayerState state;

    public Player(int id, int x, int y){
        super(x, y, 170, 200);
        xSpeed = 0;
        ySpeed = 0;
        yAcceleration = 1;

        pID = id;
        
        state = PlayerState.IDLE;

        try{
            image = ImageIO.read(new FileInputStream(new File("Animations/Player" + pID + "Idle/Player" + pID + "Idle0000.png")));
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
        updateImage();
        if(!facingRight) {
            window.drawImage(image, getX(), getY(), getWidth(), getHeight(),null);
        } else {
            window.drawImage(image, getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
        }
    }

    private void updateImage() {
        //increments currFrame, possible values 0 - state.frames-1
    	frameCount = (frameCount+1) % state.frameTime;
    	if(frameCount%state.frameTime == 0)
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
        ySpeed += yAcceleration;
    }

    public void setFacing(boolean isFacingRight) {
        facingRight = isFacingRight;
    }

    public void setState(PlayerState state) {
        if(state != this.state) {
            this.state = state;
            currFrame = 0;
        }
    }

    public PlayerState getState() {
        return state;
    }
}
