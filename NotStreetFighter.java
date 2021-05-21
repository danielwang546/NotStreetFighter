import javax.swing.JFrame;
import java.awt.Component;

public class NotStreetFighter extends JFrame{
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 1200;

    public NotStreetFighter() {
        super("NotStreetFighter");
        setSize(WIDTH, HEIGHT);

        NotStreetFighterGame game = new NotStreetFighterGame();
        ((Component)game).setFocusable(true);

        getContentPane().add(game);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        NotStreetFighter run = new NotStreetFighter();
    }
}
