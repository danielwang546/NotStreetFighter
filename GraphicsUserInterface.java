import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.net.URL;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.io.IOException;

import java.awt.*;
import javax.imageio.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class GraphicsUserInterface extends JFrame implements ActionListener{

    private int healthBarWidthP1;
    private int healthBarWidthP2;
    private int scoreP1;
    private int scoreP2;
    private boolean startCalled;
    private boolean startScreen = true;
    private ScoreFileWriter scoreFW;
    private boolean endCalled;
    private boolean endScreen = true;

    
    public GraphicsUserInterface(int h1, int h2, int s1, int s2){
        healthBarWidthP1 = h1 * 3;
        healthBarWidthP2 = h2 * 3;
        scoreP1 = s1;
        scoreP2 = s2;
        scoreFW = new ScoreFileWriter();
    }

    public void setHealthBar(int p1, int p2){
        healthBarWidthP1 = p1 * 3;
        healthBarWidthP2 = p2 * 3;
    }

    public void setScore(int p1, int p2){
        scoreP1 = p1;
        scoreP2 = p2;
    }
    
    public int getHealthBar1() {
        return healthBarWidthP1;
    }

    public int getHealthBar2() {
        return healthBarWidthP2;
    }

    public int getScore1() {
        return scoreP1;
    }

    public int getScore2() {
        return scoreP2;
    }

    public void draw(Graphics window){
    	
    	window.setFont(new Font("Impact", 0, 30));
        window.setColor(Color.RED);
        window.fillRect(25, 10, healthBarWidthP1, 30);
        window.fillRect(425, 10, healthBarWidthP2, 30);
        window.setColor(Color.BLACK);
        window.drawString(""+healthBarWidthP1/3, 25, 35);
        window.drawString(""+healthBarWidthP2/3, 425, 35);
        String p1out = ""+scoreP1;
        while(p1out.length() < 6) {
        	p1out="0"+p1out;
        }
        String p2out = ""+scoreP2;
        while(p2out.length() < 6) {
        	p2out="0"+p2out;
        }
        
        window.drawString(p1out, 25, 75);
        window.drawString(p2out, 425, 75);
        
    }

    public boolean start(){
        if(!startCalled){
            URL url = getClass().getResource("Background.jpg");
            setContentPane(new JLabel(new ImageIcon(url)));

            JButton b = new JButton("Click To Play");
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setBackground(Color.RED);
            b.setBounds(200,400,400,80);  
            add(b);  
            setSize(800,600);  
            setLayout(null);  
            setVisible(true);
            b.addActionListener(this);  
            startCalled = true;
        } 
        return startScreen;
        

    }

    public boolean end(){

        if(!endCalled){

            URL url;

            if(healthBarWidthP1 <= 0) {
                //window.drawString("Player 2 wins!", 800, 200);
                url = getClass().getResource("Player2Win.jpg");
                
            } else{
                url = getClass().getResource("Player1Win.jpg");
            }
            

            

            setContentPane(new JLabel(new ImageIcon(url)));
            
            String[] topScores = scoreFW.getTopScores();
            

            JLabel labelM = new JLabel("High Scores:");
            labelM.setBounds(1000, 500, 200, 30);


            for(int i = 0; i < topScores.length; i++) {
                if(topScores[i] == null)
                    break;
                JLabel test = new JLabel(String.valueOf(i+1) + ". " + topScores[i]);
                test.setBounds(1000, 400 + 400 + 20*(i+1), 200, 30);
                add(test);
                //print = String.valueOf(i+1) + ". " + topScores[i];
            }
            

            //JLabel labelM = new JLabel("Not Only of Sight\n, but of: ");
            //labelM.setBounds(50, 50, 200, 30);


            JButton b = new JButton("Click To Reset");
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setBackground(Color.red);
            b.setBounds(1000,300,400,80);  
            add(b); 
            add(labelM); 

            setSize(800,600);  
            setLayout(null);  
            setVisible(true);
            b.addActionListener(this);  
            endCalled = true;

        } 

        return endScreen;
    }

    public void writeToFile(int points, String name) {
        scoreFW.writePtsToFile(points, name);
    }

    public void resetEnd(){
        endScreen = true;
        endCalled = false;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Click To Play")) {
            startScreen = false;
            dispose();
        }
        if(e.getActionCommand().equals("Click To Reset")){
            endScreen = false;
            dispose();
        }
        
        
    }
    
}
