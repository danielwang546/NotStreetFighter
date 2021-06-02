import java.awt.*;
import java.util.ArrayList;

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
  
  public boolean touching(GameElement other) {
	Rectangle thisRect = new Rectangle();
	
	thisRect.width= width;
	thisRect.x = x;
	
  
	if(xSpeed > 0) {
		thisRect.width= width + xSpeed;
	} else if(xSpeed < 0) {
		thisRect.x = x + xSpeed;
	}
  
	
	thisRect.height = height;
	thisRect.y = y;
	
  
	if(ySpeed > 0) {
		thisRect.height= height + ySpeed;
	} else if(ySpeed < 0) {
		thisRect.y = y + ySpeed;
	}
  
	
    Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
    return thisRect.intersects(otherRect);
    // return ((this.x >= other.x && this.x <= other.x + other.width) ||
    //     (this.x + this.width >= other.x && this.x <= other.x) &&
    //     (this.y >= other.y && this.y <= other.y + other.height) ||
    //     (this.y + this.height >= other.y && this.y <= other.y));
  }

  public void move(int x, int y){
      this.x += x;
      this.y += y;
  }
  public boolean touchingTop(GameElement other){
    Rectangle thisRect = new Rectangle();
	
    thisRect.width= width;
    thisRect.x = x;
  
    
    
    thisRect.height = height;
    thisRect.y = y-1;
    
    
    if(ySpeed > 0) {
      thisRect.height+=ySpeed;
    } else if(ySpeed < 0) {
      thisRect.y+=ySpeed;
    }

    return thisRect.intersectsLine(other.x, other.y, other.x + other.width, other.y);
  }

  public boolean touchingSide(GameElement other){
    Rectangle thisRect = new Rectangle();
	
    thisRect.width= width;
    thisRect.x = x;
    
    
    if(xSpeed > 0) {
      thisRect.width = width + xSpeed;
    } else if(xSpeed < 0) {
      thisRect.x = x + xSpeed;
    }
    
    
    thisRect.height = height;
    thisRect.y = y;
    
    
    if(ySpeed > 0) {
      thisRect.height= height + ySpeed;
    } else if(ySpeed < 0) {
      thisRect.y = y + ySpeed;
    }

    return thisRect.intersectsLine(other.x, other.y, other.x, other.y + other.height) || thisRect.intersectsLine(other.x + other.width, other.y, other.x + other.width , other.y + other.height);
  }
  
  public abstract void draw(Graphics window);

  public String toString()
  {
    return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
  }
}

