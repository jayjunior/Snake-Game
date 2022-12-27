import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

enum Direction {
    LEFT , RIGHT , DOWN ,UP
}


public class Snake extends JPanel {

    private Color color ;
    private ArrayList<Rectangle> body;
    private int startX;
    private int startY;


    Direction direction ;
    Apple apple;
    Main window ;

    boolean eaten = false;

    public Snake(Color color, Main window, Apple apple){

        this.apple = apple;
        this.window = window;
        this.body = new ArrayList<>();
        this.color = color;
        this.startX = Rectangle.getWidthAndHeight()[0] * new Random().nextInt(Rectangle.getWidthAndHeight()[0]);
        this.startY = Rectangle.getWidthAndHeight()[1] * new Random().nextInt(Rectangle.getWidthAndHeight()[1]);
        Rectangle head = new Rectangle(startX ,startY);
        Rectangle next_1 = new Rectangle(startX - Rectangle.getWidthAndHeight()[0],startY );
        Rectangle next_2 = new Rectangle(next_1.getCoordinates()[0] -  Rectangle.getWidthAndHeight()[0], startY);

        this.body.add(head);
        this.body.add(next_1);
        this.body.add(next_2);


        direction = Direction.RIGHT;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public void addPart(){
        Rectangle last = this.body.get(this.body.size() - 1);
        switch(this.direction){
            case RIGHT -> this.body.add(new Rectangle(last.getCoordinates()[0] - Rectangle.getWidthAndHeight()[0], last.getCoordinates()[1]));
            case LEFT -> this.body.add(new Rectangle(last.getCoordinates()[0] + Rectangle.getWidthAndHeight()[1], last.getCoordinates()[1]));
            case UP -> this.body.add(new Rectangle(last.getCoordinates()[0],last.getCoordinates()[1] + Rectangle.getWidthAndHeight()[1]));
            case DOWN -> this.body.add(new Rectangle(last.getCoordinates()[0],last.getCoordinates()[1] - Rectangle.getWidthAndHeight()[1]));
        }
    }



    public void gameOver(){
        this.window.setVisible(true);

        JFrame parent = new JFrame("Game over!");
        JOptionPane.showMessageDialog(parent, "Your score: " + this.body.size());

        this.window.dispatchEvent(new WindowEvent(this.window, WindowEvent.WINDOW_CLOSING));


        System.exit(0);
    }

    public void checkCollision(){
        Rectangle head = this.body.get(0);

        // collisions  between body parts

        for(int i = 1 ; i < body.size() ; i++){
            Rectangle part = this.body.get(i);
            if(head.intersects(part)){
                gameOver();
            }
        }

        // collision between any body part and the apple
        Rectangle dummy = new Rectangle(this.apple.getCoordinates()[0],this.apple.getCoordinates()[1]);
        if(head.intersects(dummy)){
            this.eaten = true;
            this.addPart();
        }

        // collision between head and game boundaries
        if(head.getCoordinates()[0] < 0 || head.getCoordinates()[0] >= Main.getBoardDimensions()[0]){
            gameOver();
        }

        if(head.getCoordinates()[1] < 0 || head.getCoordinates()[1] >= Main.getBoardDimensions()[1]){
            gameOver();
        }

    }

    public void moveSnake() {

        Rectangle head = body.get(0);
        Rectangle headCopy = head.clone();

        ArrayList<Rectangle> tmpList = new ArrayList<>();

        // change head coordinates
        switch (this.direction) {
            case RIGHT -> headCopy.setX(head.getCoordinates()[0] + Rectangle.getWidthAndHeight()[0]);
            case LEFT -> headCopy.setX(head.getCoordinates()[0] - Rectangle.getWidthAndHeight()[0]);
            case UP -> headCopy.setY(head.getCoordinates()[1] - Rectangle.getWidthAndHeight()[1]);
            case DOWN -> headCopy.setY(head.getCoordinates()[1] + Rectangle.getWidthAndHeight()[1]);
        }

        tmpList.add(headCopy);

        // change coordinates of every other part
        for (int i = 1; i < this.body.size(); i++) {
            Rectangle previous = this.body.get(i-1);
            Rectangle current = this.body.get(i);
            Rectangle currentCopy = current.clone();
            currentCopy.setX(previous.getCoordinates()[0]);
            currentCopy.setY(previous.getCoordinates()[1]);
            tmpList.add(currentCopy);
        }

        this.body = new ArrayList<>(tmpList);


        checkCollision();
    }

    private void drawSnake(Graphics g) {
        moveSnake();

        /**
         * Optionally We can add horizontal and vertical grids
         */

        /*
        // draw horizontal and vertical lines

        g.setColor(Color.WHITE);

        // horizontal lines


        for(int i = 25 ; i <= Main.getBoardDimensions()[1] - Rectangle.getWidthAndHeight()[1] ; i+=Rectangle.getWidthAndHeight()[1]){
            g.drawLine(0,i,Main.getBoardDimensions()[0],i);
        }

        // vertical lines

        for(int i = 25 ; i <= Main.getBoardDimensions()[0] - Rectangle.getWidthAndHeight()[0] ; i+=Rectangle.getWidthAndHeight()[0]){
            g.drawLine(i,0,i,Main.getBoardDimensions()[1]);
        }

         */

        // meaning apple has been eaten create new apple and set it back
        if (this.eaten) {
            this.apple.setX(Rectangle.getWidthAndHeight()[0] * new Random().nextInt(Rectangle.getWidthAndHeight()[0]));
            this.apple.setY(Rectangle.getWidthAndHeight()[1] * new Random().nextInt(Rectangle.getWidthAndHeight()[1]));
            this.eaten = false;
        }

        g.setColor(new Color(214,54,223));
        this.apple.draw(g);


        // draw head
        Rectangle head = body.get(0);
        g.setColor(new Color(251,181,4));
        head.draw(g);


        // draw rest of snake
        g.setColor(this.color);

        for(int i = 1 ; i < this.body.size() ; i++){
            Rectangle current = this.body.get(i);
            current.draw(g);
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(4,50,134));
        drawSnake(g);
    }

}