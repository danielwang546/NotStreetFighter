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
        IDLE(1, 1, true, "Idle"),
        CROUCHING(9, 1, false, "Crouch"),
        IDLE_CROUCH(1, 1, true, ""),
        WALKING(9, 3, true, "Walk"),
        JUMPING(0, 1, true, ""),
        PUNCHING(9, 2, false, "Punch"),
        BLOCKING(9, 1, false, ""),
    	STUNNED(9, 1, false, "Stunned");


        private final int frames;
        private final int frameTime;
        private final boolean interruptible;
        private final String fileName;

        PlayerState(int frames, int frameTime, boolean interruptible, String fileName) {
            this.frames = frames;
            this.frameTime = frameTime;
            this.interruptible = interruptible;
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
    private ArrayList<PlayerState> stateQueue;
    

    public Player(int id, int x, int y){
        super(x, y, 170, 200);
        xSpeed = 0;
        ySpeed = 0;
        yAcceleration = 1;

        pID = id;
        
        stateQueue = new ArrayList<PlayerState>();

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
        if(facingRight) {
            window.drawImage(image, getX(), getY(), getWidth(), getHeight(),null);
        } else {
            window.drawImage(image, getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
        }
    }

    private void updateImage() {
        //increments currFrame, possible values 0 - state.frames-1
    	frameCount = (frameCount+1) % currState().frameTime;
    	if(frameCount%currState().frameTime == 0) {
    		currFrame = (currFrame + 1) % currState().frames;
    		if(currFrame==0 && !emptyQueue())
    			stateQueue.remove(0);
    	}
        try{
            image = ImageIO.read(new FileInputStream(new File("Animations/Player" + pID + currState().fileName + "/Player" + pID + currState().fileName + "000" + currFrame + ".png")));
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

    public void setFacingRight(boolean isFacingRight) {
        facingRight = isFacingRight;
    }
    
    public void printStates() {
    	for(int i=0; i < stateQueue.size(); i++)
    		System.out.println(stateQueue.get(i).fileName+" "+i);
    }
    
    public boolean emptyQueue() {
    	return stateQueue.size() < 1;
    }

    //this logic can definitely be improved
    public void addState(PlayerState state) {
    	if(stateQueue.size() < 5) {
    		if(state.interruptible) {
    			if(currState().interruptible) {
    				if(emptyQueue())
    					stateQueue.add(state);
    				else
    					stateQueue.set(0,state);
    			}
    		} else {
    			if(currState().interruptible) {
    				if(emptyQueue())
    					stateQueue.add(state);
    				else
    					stateQueue.set(0,state);
    			} else {
    				stateQueue.add(state);
    			}
    		}
    	}
    }
    
    public PlayerState currState() {
    	if(emptyQueue())
    		return PlayerState.IDLE;
    	else
    		return stateQueue.get(0);
    }
}
