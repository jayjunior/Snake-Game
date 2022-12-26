import java.awt.*;

public class Rectangle implements Cloneable{

    private int x;
    private int y;

    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;

    public Rectangle(int x , int y){
        this.x = x;
        this.y = y;
    }

    public static int[] getWidthAndHeight(){
        return new int[]{WIDTH,HEIGHT};
    }

    public int[] getCoordinates(){
        return new int[]{this.x,this.y};
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean intersects(Rectangle other){
        return this.x == other.getCoordinates()[0] && this.y == other.getCoordinates()[1];
    }

    public void draw(Graphics g){
        g.drawRect(this.x,this.y,WIDTH,HEIGHT);
        g.fillRect(this.x,this.y,WIDTH,HEIGHT);
    }

    @Override
    public Rectangle clone() {
        try {
            Rectangle clone = (Rectangle) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}