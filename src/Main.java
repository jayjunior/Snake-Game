import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements KeyListener , ActionListener {

    private static final int BOARD_WIDTH = Rectangle.getWidthAndHeight()[0] * Rectangle.getWidthAndHeight()[0];
    private static final int BOARD_HEIGHT = Rectangle.getWidthAndHeight()[1] * Rectangle.getWidthAndHeight()[1];
    private Snake snake;

    public Main(){


        this.snake = new Snake(new Color(251,181,4),this,new Apple(null));
        Timer timer = new Timer(1000, this);
        timer.start();


        add(this.snake);
        setTitle("Snake Game");
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.addKeyListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(Main::new);
    }

    public static int[] getBoardDimensions(){
        return new int[]{BOARD_WIDTH,BOARD_HEIGHT};
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == 39 && (this.snake.getDirection() != Direction.LEFT)) {
            this.snake.setDirection(Direction.RIGHT); // right arrow pressed
        }

        else if (c == 37 && (this.snake.getDirection() != Direction.RIGHT) ){
            this.snake.setDirection(Direction.LEFT); // left arrow pressed
        }

        else if (c == 38 && (this.snake.getDirection() != Direction.DOWN)) {
            this.snake.setDirection(Direction.UP); // up arrow pressed
        }

        else if (c == 40 && (this.snake.getDirection() != Direction.UP)) {
            this.snake.setDirection(Direction.DOWN); // down arrow pressed
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}