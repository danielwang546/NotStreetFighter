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

import java.awt.*;
import javax.imageio.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class GraphicsUserInterface {

    private int healthBarWidthP1;
    private int healthBarWidthP2;
    private int scoreP1;
    private int scoreP2;
    
    public GraphicsUserInterface(int h1, int h2,int s1,int s2 ){
        healthBarWidthP1 = h1 * 7;
        healthBarWidthP2 = h2 * 7;
        scoreP1 = s1;
        scoreP2 = s2;
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

    public int getHScofre2() {
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

    public void start(Graphics window){

    }

    public void end(Graphics window){
        
    }

    public void writeToFile(int points, String name) {
        try {
            //file in format: [int score] [string w/ length-3]
            File scores = new File("scores.txt");
            if(scores.createNewFile()) {
                //file does not already exist
                FileWriter fw = new FileWriter(scores);
                fw.write(points + " " + name);
                fw.close();
            } else {
                //file already exists, read file
                Scanner scan = new Scanner(scores);
                HashMap<Integer, String> unsorted = new HashMap<Integer, String>();
                while(scan.hasNextLine()) {
                    String line = scan.nextLine();
                    StringTokenizer st = new StringTokenizer(line);
                    int highScore = Integer.valueOf(st.nextToken());
                    String hsName = String.valueOf(st.nextToken());
                    unsorted.put(highScore, hsName);
                }
                unsorted.put(points, name);

                TreeMap<Integer, String> sorted = new TreeMap<Integer, String>();
                sorted.putAll(unsorted);

                FileWriter fw = new FileWriter(scores);

                scan.close();
            }
            
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
