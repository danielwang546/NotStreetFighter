import java.awt.*;

public class HitBox extends GameElement{
	
	public HitBox(int x, int y, int w, int h) {
		super(x,y,0,0,w,h);
	}
	
	public void draw(Graphics window) {
		window.drawRect(getX(), getY(), getWidth(), getHeight());
	}
}