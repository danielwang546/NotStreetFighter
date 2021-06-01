import java.awt.*;

public class HitBox extends GameElement{
	
	public HitBox(int x, int y, int xS, int yS, int w, int h) {
		super(x,y,xS,yS,w,h);
	}
	
	public void draw(Graphics window) {
		window.drawRect(getX(), getY(), getWidth(), getHeight());
	}
}