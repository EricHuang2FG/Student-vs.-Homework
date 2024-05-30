import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentVsHomework extends JPanel {

    private static final int SCREEN_WIDTH = 1020;
    private static final int SCREEN_HEIGHT = 640;
    private int levelNumber = 1;
    private Level level = new Level();
    private static String gameState = "game screen"; // Temporary

    public StudentVsHomework() {

    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Student vs. Homework");
        StudentVsHomework window = new StudentVsHomework();
        frame.add(window);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            if (gameState.equals("start screen")) {

            }
            if (gameState.equals("game screen")) {
                window.level.behave();
                if (window.level.levelIsLost()) {

                } else if (window.level.levelIsWon()) {

                }
            }
            if (gameState.equals("level transition screen")) {

            }
            window.repaint();
            Thread.sleep(20);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (gameState.equals("start screen")) {

        }
        if (gameState.equals("game screen")) {
            level.paint(g2d);
        }
        if (gameState.equals("level transition screen")) {

        }
    }

}