import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.awt.Color;

import javax.imageio.ImageIO;

public class Player extends GameElement{

    public static enum PlayerState {
        //values will be filled in when more animations are added
        IDLE(1, 1, true, "Idle"),
        CROUCHING(9, 1, false, "Crouch"),
        IDLE_CROUCH(1, 1, false, "Idle_Crouch"),
        WALKING(9, 3, true, "Walk"),
        JUMPING(0, 1, true, ""),
        PUNCHING(9, 2, false, "Punch"),
        KICKING(9, 2, false, "Kick"),
        BLOCKING(9, 1, false, "Block"),
        IDLE_BLOCK(1, 1, false, "Idle_Block"),
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

        public int frames() {
            return frames;
        }

        public int frameTime() {
            return frameTime;
        }

        public String fileName() {
            return fileName;
        }
    }
    
    private int yAcceleration;
    private boolean facingRight = true;
    private int pID;
    private int health = 100;
    private int score = 0;

    private Image image;
    private int currFrame = 0;
    private int frameCount = 0;
    private HitBox hitBox;
    private AttackBox attackBox;
    private boolean attacked;
    private boolean movementDisabled;
    private PlayerState currState;
    private ArrayList<PlayerState> stateQueue;
    

    public Player(int id, int x, int y){
        super(x, y, 0, 0, 170, 200);
        
        yAcceleration = 30;

        pID = id;
        
        hitBox = new HitBox(getX()+40,getY()+20,getXSpeed(), getYSpeed(), getWidth()-80,getHeight()-20);
        attackBox = new AttackBox(0,0,getXSpeed(), getYSpeed(),0,0);
        attacked = false;
        movementDisabled = false;
        
        stateQueue = new ArrayList<PlayerState>();
        
        currState = PlayerState.IDLE;

        try{
            image = ImageIO.read(new FileInputStream(new File("Animations/Player" + pID + "Idle/Player" + pID + "Idle0000.png")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics window){
        //applyGravity();
        move(getXSpeed(), getYSpeed());
        updateImage();
        if(facingRight) {
            window.drawImage(image, getX(), getY(), getWidth(), getHeight(),null);
        } else {
            window.drawImage(image, getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
        }
        hitBox.draw(window);
        punch();
        window.setColor(Color.RED);
        attackBox.draw(window);
        window.setColor(Color.BLACK);
    }

    private void updateImage() {
        //increments currFrame, possible values 0 - state.frames-1
    	frameCount = (frameCount+1) % currState.frameTime;
    	if(frameCount%currState.frameTime == 0) {
    		currFrame = (currFrame + 1) % currState.frames;
    		if(currFrame==0) {
    			currState = nextState();
    		}
    	}
        try{
            image = ImageIO.read(new FileInputStream(new File("Animations/Player" + pID + currState.fileName + "/Player" + pID + currState.fileName + "000" + currFrame + ".png")));
        } catch(Exception e){
            e.printStackTrace();
        }
        
        
    }

    public HitBox getHitBox() {
    	return hitBox;
    }
    
    public void updateHitBox() {
    	hitBox = new HitBox(getX()+40,getY()+20,getXSpeed(), getYSpeed(), getWidth()-80,getHeight()-20);
    	if(currState.fileName.equals("Idle_Crouch"))
			hitBox = new HitBox(getX()+40,getY()+120,getXSpeed(), getYSpeed(), getWidth()-80,getHeight()-120);
    	else if(currState.fileName.equals("Idle_Block"))
    		hitBox = new HitBox(getX()+40,getY()+40,getXSpeed(), getYSpeed(), getWidth()-80,getHeight()-40);
    }

    public AttackBox getAttackBox() {
    	return attackBox;
    }

    public void deleteAttackBox() {
        attacked = true;
    }
    
    public void punch() {
        if (currState == PlayerState.PUNCHING && currFrame == 0) {
            attacked = false;
        }
        if (currState == PlayerState.PUNCHING && currFrame >= 4 && currFrame <= 6 && !attacked) {
            if (facingRight) {
                attackBox.setPos(getX()+getWidth()-10, getY()+100);
            } else {
                attackBox.setPos(getX(), getY()+100);
            }
            
            attackBox.setWidth(10);
            attackBox.setHeight(10);
        } else {
            attackBox.setWidth(0);
            attackBox.setHeight(0);
        }
    }

    public void setYAccel(int s){
        yAcceleration = s;
    }

    public int getYAccel(){
        return yAcceleration;
    }
    
    @Override
    public void setXSpeed(int s) {
    	if(!movementDisabled || s==0)
    		super.setXSpeed(s);
    }
    
    @Override
    public void setYSpeed(int s) {
    	if(!movementDisabled || s==0)
    		super.setYSpeed(s);
    }
    
    public boolean isSupported(ArrayList<GameElement> objects) {
    	for(GameElement ge : objects) 
    		if(hitBox.touchingTop(ge)) 
    			return true;
		return false;
    }
   

    public void applyGravity(){
        super.setYSpeed(getYSpeed() + 1);
    }

    public void setFacingRight(boolean isFacingRight) {
        facingRight = isFacingRight;
    }

    public void decreaseHealth(int h){
        health -= h;
    }

    public void increaseScore(int s){
        score += s;
    }
    
    public void enableState(PlayerState ps, PlayerState idlePs) {
    	if(currState.fileName.equals(ps.fileName)  || currState.fileName.equals(idlePs.fileName)) {
			fillQueue(idlePs);
    	} else {
    		setCurrState(ps);
    	}
    }
    
    public void setMovementDis(boolean b) {
    	movementDisabled = b;
    }
    
    public void disableState(PlayerState ps, PlayerState idlePs) {
    	stateQueue.remove(idlePs);
    	stateQueue.remove(ps);
    }
    
    public void printStates() {
    	System.out.println(currState.fileName + " curr");
    	for(int i=0; i < stateQueue.size(); i++) 
    		System.out.println(stateQueue.get(i).fileName+" "+i);
    }
    
    public boolean emptyQueue() {
    	return stateQueue.size() < 1;
    }
    
    public void setCurrState(PlayerState ps) {
    	currState = ps;
    }
    
    public PlayerState getCurrState() {
    	return currState;
    }
    
    public void fillQueue(PlayerState ps) {
    	for(int i = 0; i < stateQueue.size(); i++)
			stateQueue.set(i, ps);
    	for(int i = 0; i < 5 - stateQueue.size(); i++)
    		stateQueue.add(ps);
    }

    public int getHealth(){
        return health;
    }

    public int getScore(){
        return score;
    }

    public void addState(PlayerState state) {
    	if(currState.interruptible && !state.fileName.equals(currState.fileName)) {
    		System.out.println(state.fileName +" is interrupting " + currState.fileName);
    		setCurrState(state);
    	} else {
    		if(stateQueue.size() < 5 && !(stateQueue.contains(state) && state.fileName.equals(currState.fileName))) {
    			stateQueue.add(state);
    		}
    	}
    }
    
    public PlayerState nextState() {
    	if(emptyQueue()) {
    		return PlayerState.IDLE;
    	} else {
    		return stateQueue.remove(0);
    	}
    }
}

