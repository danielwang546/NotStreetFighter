import java.awt.Color;
import java.awt.Graphics;

public class Ground extends GameElement {

    public Ground(int x, int y, int w, int h) {
        super(x,y,w,h);
    }

    @Override
    public void move(int x, int y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics window) {
        window.setColor(Color.GREEN);
        window.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    
}
