import java.awt.*;

public abstract class GameElement
{
  private int x;
  private int y;
  private int xSpeed;
  private int ySpeed;
  private int width;
  private int height;
  private boolean isPerm;

  public GameElement()
  {
    this(10,10,0,0,10,10);
  }

  public GameElement(int x, int y)
  {
    this(x,y,0,0,10,10);
  }

  public GameElement(int x, int y, int xS, int yS, int w, int h)
  {
    this.x = x;
    this.y = y;
    xSpeed = xS;
    ySpeed = yS;
    width = w;
    height = h;
  }

  public void setPos( int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public void setY(int y)
  {
    this.y = y;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public void setWidth(int w)
  {
    width = w;
  }

  public void setHeight(int h)
  {
    height = h;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }
  
  public void setXSpeed(int s){
      xSpeed = s;
  }

  public void setYSpeed(int s){
      ySpeed = s;
  }
  
  public int getXSpeed(){
      return xSpeed;
  }

  public int getYSpeed(){
      return ySpeed;
  }
  
  public boolean touching(GameElement other, double dT) {
	Rectangle thisRect = new Rectangle();
	
	thisRect.width= width;
	thisRect.x = x;
	
  
	if(xSpeed > 0) {
		thisRect.width= (int)(width + xSpeed * dT);
	} else if(xSpeed < 0) {
		thisRect.x = (int)(x + xSpeed * dT);
	}
  
	
	thisRect.height = height;
	thisRect.y = y;
	
  
	if(ySpeed > 0) {
		thisRect.height= (int)(height + ySpeed * dT);
	} else if(ySpeed < 0) {
		thisRect.y = (int)(y + ySpeed * dT);
	}
  
	
    Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
    return thisRect.intersects(otherRect);
    // return ((this.x >= other.x && this.x <= other.x + other.width) ||
    //     (this.x + this.width >= other.x && this.x <= other.x) &&
    //     (this.y >= other.y && this.y <= other.y + other.height) ||
    //     (this.y + this.height >= other.y && this.y <= other.y));
  }

  public void move(int x, int y, double dT){
      this.x += x * dT;
      this.y += y * dT;
   }
  public abstract void draw(Graphics window);

  public String toString()
  {
    return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
  }
}

