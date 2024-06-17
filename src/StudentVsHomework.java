import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudentVsHomework extends JPanel {

    private static final int SCREEN_WIDTH = 1020;
    private static final int SCREEN_HEIGHT = 640;
    private static final String GAME_PROGRESS_FILE_PATH = "res\\game_progress\\game_progress.txt";
    private static int levelNumber = 1;
    private static boolean progressSaved = false;
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
    private static boolean showLevelWonScreenEraseProgressButton = false;
    private static int levelWonScreenEraseProgressButtonClickCounter = 0;
    private static boolean showRestartLevelButton = false;
    private static boolean restartLevelButtonIsClicked = false;
    private static boolean showLevelLostScreenEraseProgressButton = false;
    private static int levelLostScreenEraseProgressButtonClickCounter = 0;
    private static boolean displayProgressErasedMessage = false;
    private static int fontSize = 20;
    private static Button startScreenPlayButton = new Button(0, 330, "res\\buttons\\play_button.png", true, 0.3);;
    private static Button instructionsScreenPlayButton = new Button(0, StudentVsHomework.getScreenHeight() - 100, "res\\buttons\\play_button.png", true, 0.3);
    private static Button nextLevelButton = new Button(0, 390, "res\\buttons\\next_level_button.png", true, 0.3);
    private static Button restartLevelButton = new Button(0, 410, "res\\buttons\\restart_level_button.png", true, 0.3);
    private static Button levelWonScreenEraseProgressButton = new Button(0, 500, "res\\buttons\\erase_progress_button.png", true, 0.3);
    private static Button levelLostScreenEraseProgressbutton = new Button(0, 500, "res\\buttons\\erase_progress_button.png", true, 0.3);
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
                level.clickHelpButton(e);
                level.clickContinueButton(e);
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
                if (showLevelWonScreenEraseProgressButton) {
                    clickLevelWonScreenEraseProgressButton(e);
                }
                if (showLevelLostScreenEraseProgressButton) {
                    clickLevelLostScreenEraseProgressButton(e);
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
        readProgress();
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
                    window.level.setLevelNumber(levelNumber);
                    showInstructionsScreenPlayButton = false;
                    instructionsScreenPlayButtonIsClicked = false;
                }
            }
            if (gameState.equals("game_screen")) {
                if (window.level.levelIsLost()) {
                    gameState = "level_lost_screen";
                } else if (window.level.levelIsWon()) {
                    gameState = "level_won_screen";
                    progressSaved = false;
                } else {
                    window.level.behave(window);
                }
            }
            if (gameState.equals("level_won_screen")) {
                showNextLevelButton = true;
                showLevelWonScreenEraseProgressButton = true;
                if (!progressSaved) {
                    levelNumber++;
                    saveProgress();
                    progressSaved = true;
                }
                if (levelWonScreenEraseProgressButtonClickCounter == 3) {
                    eraseProgress();
                    levelWonScreenEraseProgressButtonClickCounter = 0;
                    displayProgressErasedMessage = true;
                }
                if (nextLevelButtonIsClicked) {
                    readProgress();
                    window.level.setLevelNumber(levelNumber);
                    gameState = "game_screen";
                    displayProgressErasedMessage = false;
                    showNextLevelButton = false;
                    nextLevelButtonIsClicked = false;
                    showLevelWonScreenEraseProgressButton = false;
                    levelWonScreenEraseProgressButtonClickCounter = 0;
                }
            }
            if (gameState.equals("level_lost_screen")) {
                showRestartLevelButton = true;
                showLevelLostScreenEraseProgressButton = true;
                if (levelLostScreenEraseProgressButtonClickCounter == 3) {
                    eraseProgress();
                    levelLostScreenEraseProgressButtonClickCounter = 0;
                    displayProgressErasedMessage = true;
                }
                if (restartLevelButtonIsClicked) {
                    readProgress();
                    window.level.setLevelNumber(levelNumber);
                    gameState = "game_screen";
                    displayProgressErasedMessage = false;
                    showRestartLevelButton = false;
                    restartLevelButtonIsClicked = false;
                    showLevelLostScreenEraseProgressButton = false;
                    levelLostScreenEraseProgressButtonClickCounter = 0;
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

    public void clickLevelWonScreenEraseProgressButton(MouseEvent e) {
        if (levelWonScreenEraseProgressButton.isClicked(e.getX(), e.getY())) {
            levelWonScreenEraseProgressButtonClickCounter++;
        }
    }

    public void  clickLevelLostScreenEraseProgressButton(MouseEvent e) {
        if (levelLostScreenEraseProgressbutton.isClicked(e.getX(), e.getY())) {
            levelLostScreenEraseProgressButtonClickCounter++;
        }
    }

    public static void readProgress() {
        ArrayList<Tower> towers = new ArrayList<Tower>();
        try {
            FileReader fr = new FileReader(GAME_PROGRESS_FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                if (lineNum == 1) {
                    levelNumber = Integer.parseInt(line);
                } else {
                    String[] information = line.trim().split(" ");
                    String type = information[0];
                    int[] coordinate = {Integer.parseInt(information[1]), Integer.parseInt(information[2])};
                    if (type.equals("eraser")) {
                        Eraser tower = new Eraser(coordinate);
                        towers.add(tower);
                    } else if (type.equals("mechanical_pencil")) {
                        MechanicalPencil tower = new MechanicalPencil(coordinate);
                        towers.add(tower);
                    } else if (type.equals("pen")) {
                        Pen tower = new Pen(coordinate);
                        towers.add(tower);
                    } else if (type.equals("pencil")) {
                        Pencil tower = new Pencil(coordinate);
                        towers.add(tower);
                    } else if (type.equals("shredder")) {
                        Shredder tower = new Shredder(coordinate);
                        towers.add(tower);
                    } else if (type.equals("water_bottle")) {
                        WaterBottle tower = new WaterBottle(coordinate);
                        towers.add(tower);
                    } else if (type.equals("robotic_pencil")) {
                        RoboticPencil tower = new RoboticPencil(coordinate);
                        towers.add(tower);
                    } else if (type.equals("super_eraser")) {
                        SuperEraser tower = new SuperEraser(coordinate);
                        towers.add(tower);
                    }
                }
                lineNum++;
            }
            Level.setTowers(towers);
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: \n" + e);
        }
    }

    public static void saveProgress() {
        ArrayList<Tower> towers = Level.getTowers();
        try {
            FileWriter fw = new FileWriter(GAME_PROGRESS_FILE_PATH);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(levelNumber);
            for (int i = 0; i < towers.size(); i++) {
                Tower tower = towers.get(i);
                int[] coordinate = tower.getCoordinate();
                String information = tower.getType() + " " + coordinate[0] + " " + coordinate[1];
                pw.println(information);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: \n" + e);
        }
    }

    public static void eraseProgress() {
        try {
            FileWriter fw = new FileWriter(GAME_PROGRESS_FILE_PATH);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(1);
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: \n" + e);
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
            if (showLevelWonScreenEraseProgressButton) {
                levelLostScreenEraseProgressbutton.paint(g2d);
            }
        }
        if (gameState.equals("level_lost_screen")) {
            g2d.drawImage(levelLostScreen, 0, 0, levelLostScreen.getWidth(), levelLostScreen.getHeight(), null);
            if (showRestartLevelButton) {
                restartLevelButton.paint(g2d);
            }
            if (showLevelLostScreenEraseProgressButton) {
                levelLostScreenEraseProgressbutton.paint(g2d);
            }
        }
        if (displayProgressErasedMessage) {
            g2d.setFont(new Font("Century Schoolbook", Font.BOLD, fontSize));
            g2d.setColor(Color.RED);
            g2d.drawString("Progress erased!", StudentVsHomework.getScreenWidth() / 2, 600);
        }
    }

}