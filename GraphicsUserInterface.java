import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
    private JTextField t1;
    private JTextField t2;
    private String p1Name;
    private String p2Name;

    
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

    public String getP1Name(){
        return p1Name;
    }

    public String getP2Name(){
        return p2Name;
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
        
        window.drawString(p1Name+": "+p1out, 25, 75);
        window.drawString(p2Name+": "+p2out, 425, 75);
        
    }

    public boolean start(){
        if(!startCalled){
            setContentPane(new JLabel(new ImageIcon("Background.jpg")));

            JButton b = new JButton("Click To Play");
            t1 = new JTextField("Player 1");
            t2 = new JTextField("Player 2");

            t1.setBounds(500, 450, 150, 40);
            t2.setBounds(500, 500, 150, 40);
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setBackground(Color.RED);
            b.setBounds(25,450,300,60);  
            add(b);  
            add(t1);
            add(t2);
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
        	
        	JLabel winner = new JLabel();
			winner.setFont(new Font("Impact", 0, 60));
			winner.setBounds(50,50,800,100);

            if(healthBarWidthP1 <= 0) {
                //window.drawString("Player 2 wins!", 800, 200);
                setContentPane(new JLabel(new ImageIcon("Player2Win.jpg")));
                winner.setText(p2Name+" Wins!");
                writeToFile(scoreP2, p2Name);
                
            } else{
                setContentPane(new JLabel(new ImageIcon("Player1Win.jpg")));
                winner.setText(p1Name+" Wins!");
                writeToFile(scoreP1, p1Name);
            }

            
            
            String[] topScores = scoreFW.getTopScores();
            

            JLabel labelM = new JLabel("High Scores:");
            labelM.setBounds(500, 250, 200, 30);


            for(int i = 0; i < topScores.length; i++) {
                if(topScores[i] == null)
                    break;
                JLabel scores = new JLabel(String.valueOf(i+1) + ". " + topScores[i]);
                scores.setBounds(450, 250 + 20*(i+1), 200, 30);
                add(scores);
                //print = String.valueOf(i+1) + ". " + topScores[i];
            }
            

            //JLabel labelM = new JLabel("Not Only of Sight\n, but of: ");
            //labelM.setBounds(50, 50, 200, 30);


            JButton b = new JButton("Click To Reset");
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setBackground(Color.red);
            b.setBounds(420,200,200,50);  
            add(b); 
            add(labelM); 
            add(winner);

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
    
    public String checkString(String s) {
    	if(s.length() > 10)
        	s = s.substring(0,10);
    	boolean b = true;
    	for(int i=0; i < s.length(); i++)
    		if(s.charAt(i) != ' ')
    			b = false;
    	if(b)
    		s = "Player";
    	if(s.equals(""))
        	s = "Player";
    	return s;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Click To Play")) {
            startScreen = false;
            p1Name = t1.getText();
            p2Name = t2.getText();
            p1Name = checkString(p1Name);
            p2Name = checkString(p2Name);
            
            dispose();
        }
        if(e.getActionCommand().equals("Click To Reset")){
            endScreen = false;
            dispose();
        }
        
        
    }
    
}
