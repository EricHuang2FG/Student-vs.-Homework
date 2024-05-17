import java.awt.*;

public class Grid {

    private final int WIDTH = 50, HEIGHT = 50;
    private int x, y;
    private boolean isOccupied = false;
    private final Color LIGHT_GRAY = new Color(94, 94, 94);
    private final Color DARK_GRAY = new Color(60, 60, 60);
    private Color colour;

    public Grid(int x, int y, boolean isLightGray) {
        this.x = x;
        this.y = y;
        if (isLightGray) {
            this.colour = LIGHT_GRAY;
        } else {
            this.colour = DARK_GRAY;
        }
    }
    
}