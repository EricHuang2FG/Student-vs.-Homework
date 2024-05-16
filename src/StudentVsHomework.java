import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentVsHomework extends JPanel {

    public static final int SCREEN_WIDTH = 1020;
    public static final int SCREEN_HEIGHT = 640;

    public StudentVsHomework() {
        
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student vs. Homework");
        StudentVsHomework window = new StudentVsHomework();
        frame.add(window);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            Thread.sleep(10)
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics g2d = (Graphics2D) g;
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
}