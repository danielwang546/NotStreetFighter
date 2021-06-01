import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameElement {
    public Wall(int x, int y, int w, int h) {
        super(x,y,0,0,w,h);
    }

    @Override
    public void move(int x, int y, double dT) {
        //empty method
    }

    @Override
    public void draw(Graphics window) {
        window.setColor(Color.BLACK);
        window.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public boolean touching(Player other) {
        Rectangle thisRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX() + other.getXSpeed(), other.getY(), other.getWidth(), other.getHeight());
        return thisRect.intersects(otherRect);
    }
}
