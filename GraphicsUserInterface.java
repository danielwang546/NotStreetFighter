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

    
    public GraphicsUserInterface(int h1, int h2, int s1, int s2){
        healthBarWidthP1 = h1 * 7;
        healthBarWidthP2 = h2 * 7;
        scoreP1 = s1;
        scoreP2 = s2;
        scoreFW = new ScoreFileWriter();
    }

    public void setHealthBar(int p1, int p2){
        healthBarWidthP1 = p1 * 7;
        healthBarWidthP2 = p2 * 7;
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
        window.setColor(Color.RED);
        window.fillRect(25, 10, healthBarWidthP1, 30);
        window.fillRect(825, 10, healthBarWidthP2, 30);
        window.setColor(Color.BLACK);
        window.drawString("Player 1 health: " + healthBarWidthP1/7, 25, 55);
        window.drawString("Player 2 health: " + healthBarWidthP2/7, 825, 55);
        window.drawString("Player 1 score: " + scoreP1, 25, 75);
        window.drawString("Player 2 score: " + scoreP2, 825, 75);
        
    }

    public boolean start(Graphics window){
        if(!startCalled){
            JButton b = new JButton("Click To Play");  
            setSize(1600,1200);
            b.setBounds(50,100,95,30);
            b.addActionListener(this);  
            add(b);
            setVisible(true); 
            startCalled = true;
        } 
        return startScreen;

    }

    public void end(Graphics window){

        if(healthBarWidthP1 <= 0) {
            window.drawString("Player 2 wins!", 800, 200);
        }
        if(healthBarWidthP2 <= 0) {
            window.drawString("Player 1 wins!", 800, 200);
        }

        String[] topScores = scoreFW.getTopScores();
        window.drawString("High Scores:", 300, 400);
        for(int i = 0; i < topScores.length; i++) {
            if(topScores[i] == null)
                break;
            window.drawString(String.valueOf(i+1) + ". " + topScores[i], 300, 400 + 20*(i+1));
        }
    }

    public void writeToFile(int points, String name) {
        scoreFW.writePtsToFile(points, name);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Click To Play")){
            startScreen = false;
            dispose();
        }
        
        
    }
    
}
