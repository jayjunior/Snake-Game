import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Apple {
    private int x;
    private int y;
    private BufferedImage image;

    public Apple(String imagePath){
        if(imagePath != null){
            try {
                this.image = ImageIO.read(new File(imagePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.x =  Rectangle.getWidthAndHeight()[0] * new Random().nextInt(Rectangle.getWidthAndHeight()[0]);
        this.y = Rectangle.getWidthAndHeight()[1] * new Random().nextInt(Rectangle.getWidthAndHeight()[1]);

    }

    public BufferedImage getImage(){
        return this.image.getSubimage(0,0,this.image.getWidth(),this.image.getHeight());
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

    public void draw(Graphics g){
        if(this.image != null){
            if(!g.drawImage(this.image,this.image.getWidth(),this.image.getHeight(),this.x,this.y,null)){
                Rectangle rec = new Rectangle(this.x,this.y);
                rec.draw(g);
            }
            return;
        }
        Rectangle rec = new Rectangle(this.x,this.y);
        rec.draw(g);

    }
}