import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


//import Player.PlayerState;

public class NotStreetFighterGame extends Canvas implements KeyListener, Runnable
{
    // private long beforeTime, deltaTime, currTime, initTime;
    // private int counter = 0;

    private boolean[] keys;
    private boolean[] tapKeys;
    private boolean[] stillPressed;
    private BufferedImage back;
    private ArrayList<GameElement> objects;
    private Player player1;
    private Player player2;
    private int p1holdUsed;
    private int p2holdUsed;
    private Ground ground;
    private Ground platform;
    private Wall wall1;
    private Wall wall2;
    private GraphicsUserInterface GUI;
    private boolean isStart = true ;
    private boolean isEnd = false;

    private int combo1 = 0;
    private int combo2 = 0;
    private boolean p1Hit = false;
    private boolean p2Hit = false;

    private int[] keyCodes = {
        KeyEvent.VK_ENTER,
        KeyEvent.VK_BACK_QUOTE,
        KeyEvent.VK_COMMA,
        KeyEvent.VK_A,
        KeyEvent.VK_S,
        KeyEvent.VK_D,
        KeyEvent.VK_UP,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_RIGHT
    };

    private int[] tapKeyCodes = {
        KeyEvent.VK_W,
        KeyEvent.VK_UP,
        KeyEvent.VK_1,
        KeyEvent.VK_PERIOD,
        KeyEvent.VK_2,
        KeyEvent.VK_SLASH
    };

    public NotStreetFighterGame() {
        setBackground(Color.WHITE);

        // beforeTime = System.currentTimeMillis();
        // initTime = System.currentTimeMillis();

        keys = new boolean[keyCodes.length];
        tapKeys = new boolean[tapKeyCodes.length];
        stillPressed = new boolean[tapKeyCodes.length];

        player1 = new Player(1, 400, 400);
        player2 = new Player(2, 1000, 400);
        p1holdUsed = 0;
        p2holdUsed = 0;

        ground = new Ground(0,700,1600,20, Color.GREEN);
        platform = new Ground(600,490,400,20,new Color(139,69,19));
        wall1 = new Wall(-1,0,1,1200, Color.BLACK);
        wall2 = new Wall(1601,0,1,1200, Color.BLACK);
        
        objects = new ArrayList<GameElement>();
        objects.add(ground);
        objects.add(platform);
        objects.add(wall1);
        objects.add(wall2);

        GUI = new GraphicsUserInterface(player1.getHealth(), player2.getHealth(), player1.getScore(), player2.getScore());

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

        if(isStart){
           
            isStart = GUI.start();
     
        } 
        else if(isEnd){
            isEnd = GUI.end();
            if(!isEnd){
                //Player 1 reset
                player1.setPos(400, 400);
                player1.setHealth(100);
                player1.setScore(0);
                combo1 = 0;

                //player 2 reset
                player2.setPos(1000, 400);
                player2.setHealth(100);
                player2.setScore(0);
                combo2 = 0;

                //reset end
                GUI.resetEnd();

            }
        } 
        else {
            
            //movement determination is first
            if(keys[3]) {
                player1.setXSpeed(-5);
                player1.setFacingRight(false);
                player1.addState(Player.PlayerState.WALKING);
            }
            if(keys[5]) {
                player1.setFacingRight(true);
                player1.setXSpeed(5);
                player1.addState(Player.PlayerState.WALKING);
            }
            
            if(keys[7]) {
                player2.setXSpeed(-5);
                player2.addState(Player.PlayerState.WALKING);
                player2.setFacingRight(false);
            }
            if(keys[9]) {
                player2.setXSpeed(5);
                player2.addState(Player.PlayerState.WALKING);
                player2.setFacingRight(true);
            }
            
            
            //then comes non-interruptible states with attacking taking higher priority
            if(tapKeys[2]) {
                player1.addState(Player.PlayerState.PUNCHING);
                tapKeys[2] = false;
            }
            if(tapKeys[4]) {
                player1.addState(Player.PlayerState.KICKING);
                tapKeys[4] = false;
            }
            if(tapKeys[3]) {
                player2.addState(Player.PlayerState.PUNCHING);
                tapKeys[3] = false;
            }
            if(tapKeys[5]) {
                player2.addState(Player.PlayerState.KICKING);
                tapKeys[5] = false;
            }
            
            //finally come the hold states, of which only one can be active at a time
            //player 1
            
            if(!keys[4] && !keys[1]) {
                p1holdUsed = 0;
            } else if(player1.getCurrState()==Player.PlayerState.STUNNED) {
            	p1holdUsed = -1;
            }
            
            if(keys[4] && (p1holdUsed==4 || p1holdUsed==0)) {
                player1.setXSpeed(0);
                p1holdUsed = 4;
                player1.enableState(Player.PlayerState.CROUCHING, Player.PlayerState.IDLE_CROUCH);
            }else {
                player1.disableState(Player.PlayerState.CROUCHING, Player.PlayerState.IDLE_CROUCH);
            }
            
            if(keys[1] && (p1holdUsed==1 || p1holdUsed==0)) {
                player1.setXSpeed(0);
                player1.setMovementDis(true);
                p1holdUsed = 1;
                player1.enableState(Player.PlayerState.BLOCKING, Player.PlayerState.IDLE_BLOCK);
            }else if(player1.getCurrState() != Player.PlayerState.STUNNED){
                player1.setMovementDis(false);
                player1.disableState(Player.PlayerState.BLOCKING, Player.PlayerState.IDLE_BLOCK);
            }
            
            //player 2
            
            if(!keys[8] && !keys[2]) {
                p2holdUsed = 0;
            } else if(player2.getCurrState()==Player.PlayerState.STUNNED) {
            	p2holdUsed = -1;
            }
            
            
            if(keys[8] && (p2holdUsed==8 || p2holdUsed==0)) {
                player2.setXSpeed(0);
                p2holdUsed = 8;
                player2.enableState(Player.PlayerState.CROUCHING, Player.PlayerState.IDLE_CROUCH);
            }else {
                player2.disableState(Player.PlayerState.CROUCHING, Player.PlayerState.IDLE_CROUCH);
            }
            
            if(keys[2] && (p2holdUsed==2 || p2holdUsed==0)) {
                player2.setXSpeed(0);
                player2.setMovementDis(true);
                p2holdUsed = 2;
                player2.enableState(Player.PlayerState.BLOCKING, Player.PlayerState.IDLE_BLOCK);
            }else if(player2.getCurrState() != Player.PlayerState.STUNNED){
                player2.setMovementDis(false);
                player2.disableState(Player.PlayerState.BLOCKING, Player.PlayerState.IDLE_BLOCK);
            }

            
            //prettier walk animations
            if (!keys[3] && !keys[5] && player1.getCurrState() == Player.PlayerState.WALKING) {
                player1.setXSpeed(0);
                player1.setCurrState(Player.PlayerState.IDLE);
            }
            if (!keys[7] && !keys[9] && player2.getCurrState() == Player.PlayerState.WALKING) {
                player2.setXSpeed(0);
                player2.setCurrState(Player.PlayerState.IDLE);
            }
            
            player1.updateHitBox();
            player2.updateHitBox();    
            
            //calculate collision
            
            if(player1.isSupported(objects) || player1.getHitBox().touchingTop(player2.getHitBox())) {
                player1.setYSpeed(0);
            } else {
                player1.applyGravity();
            }

            if(player2.isSupported(objects) || player2.getHitBox().touchingTop(player1.getHitBox())) {
                player2.setYSpeed(0);
            } else {
                player2.applyGravity();
            }
            
            if(player1.getHitBox().touchingTop(player2.getHitBox())){
            	int offset = 21;
            	if(player1.getCurrState() == Player.PlayerState.IDLE_CROUCH)
        			offset = 121;
            	else if(player1.getCurrState() == Player.PlayerState.IDLE_BLOCK)
            		offset = 41;
            	
            	player1.setY(player2.getHitBox().getY()-player1.getHitBox().getHeight()-offset);
            }
            
            if(player2.getHitBox().touchingTop(player1.getHitBox())){
            	int offset = 21;
            	if(player2.getCurrState() == Player.PlayerState.IDLE_CROUCH)
        			offset = 121;
            	else if(player2.getCurrState() == Player.PlayerState.IDLE_BLOCK)
            		offset = 41;
            	
            	player2.setY(player1.getHitBox().getY()-player2.getHitBox().getHeight()-offset);
            }
            
            if(tapKeys[0]){
                if((player1.isSupported(objects)|| player1.getHitBox().touchingTop(player2.getHitBox())))
                    player1.setYSpeed(-20);
                tapKeys[0] = false;
            }
            
            if(tapKeys[1]){
                if((player2.isSupported(objects) || player2.getHitBox().touchingTop(player1.getHitBox())))
                    player2.setYSpeed(-20);
                tapKeys[1] = false;
            }
           
            if(player1.getHitBox().touchingSide(player2.getHitBox()) || player1.isObstructed(objects)) {
                player1.setXSpeed(0);
            }
            if(player2.getHitBox().touchingSide(player1.getHitBox()) || player2.isObstructed(objects)) {
                player2.setXSpeed(0);
            }
         
            //attack collision

        	//player 1
            if (player1.attackBox().touching(player2.getHitBox())) {
                p1Hit = true;
                player1.deleteAttackBox();
                if (player1.getCurrState() == Player.PlayerState.PUNCHING) {
                    if (player2.getCurrState() == Player.PlayerState.IDLE_BLOCK) {
                        player2.decreaseHealth(5);
                        player1.increaseScore(20);
                    } else {
                        player2.decreaseHealth(10);
                        player1.increaseScore(100);
                        if (combo1 < 2)
                            combo1++;
                    }
                } else if(player1.getCurrState() == Player.PlayerState.KICKING) {
                    if (player2.getCurrState() == Player.PlayerState.IDLE_BLOCK) {
                        player2.decreaseHealth(2);
                        player1.increaseScore(10);
                    } else {
                        player2.decreaseHealth(5);
                        player1.increaseScore(50);
                        if (combo1 == 2)
                            combo1++;
                    }
                }
            } else {
                if (!player1.attacked() && player1.getCurrFrame() == 6) {
                    if (!p1Hit)
                        combo1 = 0;
                    p1Hit = false;
                }
            }
            
            //player 2
            if (player2.attackBox().touching(player1.getHitBox())) {
                player2.deleteAttackBox();
                p2Hit = true;
                if (player2.getCurrState() == Player.PlayerState.PUNCHING) {
                    if (player1.getCurrState() == Player.PlayerState.IDLE_BLOCK) {
                        player1.decreaseHealth(5);
                        player2.increaseScore(20);
                    } else {
                        player1.decreaseHealth(10);
                        player2.increaseScore(100);
                        if (combo2 < 2)
                            combo2++;
                    }
                }  else if(player2.getCurrState() == Player.PlayerState.KICKING) {
                    if (player1.getCurrState() == Player.PlayerState.IDLE_BLOCK) {
                        player1.decreaseHealth(2);
                        player2.increaseScore(10);
                    } else {
                        player1.decreaseHealth(5);
                        player2.increaseScore(50);
                        if (combo2 == 2)
                            combo2++;
                    }
                }
            }  else {
                if (!player2.attacked() && player2.getCurrFrame() == 6) {
                    if (!p2Hit)
                        combo2 = 0;
                    p2Hit = false;
                }
            }
            

            if (combo1 == 3) {
                player2.setCurrState(Player.PlayerState.STUNNED);
                player2.setMovementDis(true);
                player1.increaseScore(1000);
                combo1 = 0;
            }
            if (combo2 == 3) {
                player1.setCurrState(Player.PlayerState.STUNNED);
                player1.setMovementDis(true);
                player2.increaseScore(1000);
                combo2 = 0;
            }
            
            GUI.setHealthBar(player1.getHealth(),player2.getHealth());
            GUI.setScore(player1.getScore(),player2.getScore());

            player1.draw(graphToBack);
            player2.draw(graphToBack);
            for(GameElement ge : objects)
            	ge.draw(graphToBack);
            GUI.draw(graphToBack);

            //draws everything from graphToBack to the image (put all draws before this line)
            twoDGraph.drawImage(back, null, 0, 0);

            if(player1.getHealth() <= 0 || player2.getHealth() <= 0) {
                isEnd = true;
            }
            
            // player1.printStates();

            // beforeTime = currTime;
        }
        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < keyCodes.length; i++) {
            if(e.getKeyCode() == keyCodes[i]) {
                keys[i] = true;
            }
        }
        for(int i = 0; i < tapKeyCodes.length; i++) {
        	
            if(e.getKeyCode() == tapKeyCodes[i] && !stillPressed[i]) {
                tapKeys[i] = true;
            }
            if(e.getKeyCode() == tapKeyCodes[i]) {
                stillPressed[i] = true;
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
                tapKeys[i] = false;
            }
            if(e.getKeyCode() == tapKeyCodes[i]) {
                stillPressed[i] = false;
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
                Thread.sleep(10);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
