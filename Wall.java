import java.awt.Color;
import java.awt.Graphics;

public class Wall extends GameElement {
	
	Color color;
	
    public Wall(int x, int y, int w, int h, Color c) {
        super(x,y,0,0,w,h);
        color = c;
    }

    @Override
    public void move(int x, int y) {
        //empty method
    }

    @Override
    public void draw(Graphics window) {
        window.setColor(color);
        window.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
