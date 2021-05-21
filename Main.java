import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;


public class Main extends JFrame {
  private Main(String arg){
    if (arg == null ) {
        arg = "Person.png";
    } 
    JPanel panel = new JPanel(); 
    panel.setSize(500,640);
    panel.setBackground(Color.CYAN); 
    try {
      Image image = ImageIO.read(new File(arg));
    } catch (IOException e) {
      e.printStackTrace();
    }
    ImageIcon icon = new ImageIcon(arg); 
    JLabel label = new JLabel(); 
    label.setIcon(icon); 
    panel.add(label);
    
    this.getContentPane().add(panel); 
  }

  
  public static void main(String[] args) {
      new Main(args.length == 0 ? null : args[0]).setVisible(true); 
  }
}