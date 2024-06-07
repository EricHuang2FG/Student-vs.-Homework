import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class StudentVsHomework extends JPanel {

    private static final int SCREEN_WIDTH = 1020;
    private static final int SCREEN_HEIGHT = 640;
    private int levelNumber = 1;
    private static BufferedImage startScreen = null;
    private static BufferedImage instructionsScreen = null;
    private static BufferedImage levelLostScreen = null;
    private static BufferedImage levelWonScreen = null;
    private static boolean showStartScreenPlayButton = false;
    private static boolean startScreenPlayButtonIsClicked = false;
    private static boolean showInstructionsScreenPlayButton = false;
    private static boolean instructionsScreenPlayButtonIsClicked = false;
    private static boolean showNextLevelButton = false;
    private static boolean nextLevelButtonIsClicked = false;
    private static boolean showRestartLevelButton = false;
    private static boolean restartLevelButtonIsClicked = false;
    private static Button startScreenPlayButton = new Button(0, 330, "res\\buttons\\play_button.png", true);;
    private static Button instructionsScreenPlayButton = new Button(0, StudentVsHomework.getScreenHeight() - 100, "res\\buttons\\play_button.png", true);
    private static Button nextLevelButton = new Button(0, 390, "res\\buttons\\next_level_button.png", true);
    private static Button restartLevelButton = new Button(0, 410, "res\\buttons\\restart_level_button.png", true);
    private Level level = new Level();
    private static String gameState = "start_screen";

    public StudentVsHomework() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                level.collectMotivation(e);
                level.clickCard(e);
                if (showStartScreenPlayButton) {
                    clickStartScreenPlayButton(e);
                }
                if (showInstructionsScreenPlayButton) {
                    clickInstructionsScreenPlayButton(e);
                }
                if (showNextLevelButton) {
                    clickNextLevelButton(e);
                }
                if (showRestartLevelButton) {
                    clickRestartLevelButton(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setFocusable(true);
        try {
            startScreen = ImageIO.read(new File("res\\screens\\start_screen.png"));
            instructionsScreen = ImageIO.read(new File("res\\screens\\instructions_screen.png"));
            levelLostScreen = ImageIO.read(new File("res\\screens\\level_lost_screen.png"));
            levelWonScreen = ImageIO.read(new File("res\\screens\\level_won_screen.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: \n" + e);
        }
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
        frame.setResizable(false);
        frame.setSize(SCREEN_WIDTH + 15, SCREEN_HEIGHT + 35);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            if (gameState.equals("start_screen")) {
                showStartScreenPlayButton = true;
                if (startScreenPlayButtonIsClicked) {
                    gameState = "instructions_screen";
                    showStartScreenPlayButton = false;
                    startScreenPlayButtonIsClicked = false;
                }
            }
            if (gameState.equals("instructions_screen")) {
                showInstructionsScreenPlayButton = true;
                if (instructionsScreenPlayButtonIsClicked) {
                    gameState = "game_screen";
                    window.level.setLevelNumber(window.levelNumber);
                    showInstructionsScreenPlayButton = false;
                    instructionsScreenPlayButtonIsClicked = false;
                }
            }
            if (gameState.equals("game_screen")) {
                if (window.level.levelIsLost()) {
                    gameState = "level_lost_screen";
                } else if (window.level.levelIsWon()) {
                    gameState = "level_won_screen";
                } else {
                    window.level.behave();
                }
            }
            if (gameState.equals("level_won_screen")) {
                showNextLevelButton = true;
                if (nextLevelButtonIsClicked) {
                    window.levelNumber++;
                    window.level.setLevelNumber(window.levelNumber);
                    gameState = "game_screen";
                    showNextLevelButton = false;
                    nextLevelButtonIsClicked = false;
                }
            }
            if (gameState.equals("level_lost_screen")) {
                showRestartLevelButton = true;
                if (restartLevelButtonIsClicked) {
                    window.level.setLevelNumber(window.levelNumber);
                    gameState = "game_screen";
                    showRestartLevelButton = false;
                    restartLevelButtonIsClicked = false;
                }
            }
            window.repaint();
            Thread.sleep(20);
        }
    }

    public void clickStartScreenPlayButton(MouseEvent e) {
        if (startScreenPlayButton.isClicked(e.getX(), e.getY())) {
            startScreenPlayButtonIsClicked = true;
        }
    }

    public void clickInstructionsScreenPlayButton(MouseEvent e) {
        if (instructionsScreenPlayButton.isClicked(e.getX(), e.getY())) {
            instructionsScreenPlayButtonIsClicked = true;
        }
    }

    public void clickNextLevelButton(MouseEvent e) {
        if (nextLevelButton.isClicked(e.getX(), e.getY())) {
            nextLevelButtonIsClicked = true;
        }
    }

    public void clickRestartLevelButton(MouseEvent e) {
        if (restartLevelButton.isClicked(e.getX(), e.getY())) {
            restartLevelButtonIsClicked = true;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (gameState.equals("start_screen")) {
            g2d.drawImage(startScreen, 0, 0, startScreen.getWidth(), startScreen.getHeight(), null);
            if (showStartScreenPlayButton) {
                startScreenPlayButton.paint(g2d);
            }
        }
        if (gameState.equals("instructions_screen")) {
            g2d.drawImage(instructionsScreen, 0, 0, instructionsScreen.getWidth(), instructionsScreen.getHeight(), null);
            if (showInstructionsScreenPlayButton) {
                instructionsScreenPlayButton.paint(g2d);
            }
        }
        if (gameState.equals("game_screen")) {
            level.paint(g2d);
        }
        if (gameState.equals("level_won_screen")) {
            g2d.drawImage(levelWonScreen, 0, 0, levelWonScreen.getWidth(), levelWonScreen.getHeight(), null);
            if (showNextLevelButton) {
                nextLevelButton.paint(g2d);
            }
        }
        if (gameState.equals("level_lost_screen")) {
            g2d.drawImage(levelLostScreen, 0, 0, levelLostScreen.getWidth(), levelLostScreen.getHeight(), null);
            if (showRestartLevelButton) {
                restartLevelButton.paint(g2d);
            }
        }
    }

}