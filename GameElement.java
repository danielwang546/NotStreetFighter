import java.awt.*;

public abstract class GameElement
{
  private int x;
  private int y;
  private int width;
  private int height;
  private boolean isPerm;

  public GameElement()
  {
    x = 10;
    y = 10;
    width = 10;
    height = 10;
  }

  public GameElement(int x, int y)
  {
    this.x = x;
    this.y = y;
    width = 10;
    height = 10;
    isPerm = false;
  }

  public GameElement(int x, int y, int w, int h)
  {
    this.x = x;
    this.y = y;
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

  public boolean touching(GameElement other) {
    Rectangle thisRect = new Rectangle(x, y, width, height);
    Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
    return thisRect.intersects(otherRect);
    // return ((this.x >= other.x && this.x <= other.x + other.width) ||
    //     (this.x + this.width >= other.x && this.x <= other.x) &&
    //     (this.y >= other.y && this.y <= other.y + other.height) ||
    //     (this.y + this.height >= other.y && this.y <= other.y));
  }

  
  public abstract void move(int x, int y);
  public abstract void draw(Graphics window);

  public String toString()
  {
    return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
  }
}

