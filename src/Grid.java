import java.awt.*;

public class Grid {

    private static final int WIDTH = 100, HEIGHT = 100;
    private int x, y;
    private final int[] COORDINATE;

    private final Color LIGHT_GRAY = new Color(130, 130, 130);
    private final Color DARK_GRAY = new Color(100, 100, 100);
    private int nPoints = 5;
    private int[] xCoords = new int[nPoints], yCoords = new int[nPoints];
    private Polygon grid;
    private Color colour;

    public Grid(int x, int y, boolean isLightGray, int[] coordinate) {
        this.x = x;
        this.y = y;
        this.COORDINATE = coordinate;
        if (isLightGray) {
            this.colour = LIGHT_GRAY;
        } else {
            this.colour = DARK_GRAY;
        }
        xCoords[0] = this.x;
        xCoords[1] = this.x + WIDTH;
        xCoords[2] = this.x + WIDTH;
        xCoords[3] = this.x;
        xCoords[4] = this.x;
        yCoords[0] = this.y;
        yCoords[1] = this.y;
        yCoords[2] = this.y + HEIGHT;
        yCoords[3] = this.y + HEIGHT;
        yCoords[4] = this.y;
        grid = new Polygon(xCoords, yCoords, nPoints);
    }

    public static int getWidth() {
        return WIDTH;
    }

    public boolean enemyIsOnGrid(Enemy enemy) {
        return ((this.x < enemy.getX() && this.x + Grid.WIDTH >= enemy.getX() + (enemy.getWidth() / 2)) || (this.x <= enemy.getX() + (enemy.getWidth() / 2) && this.x + Grid.WIDTH > enemy.x + enemy.getWidth())) && (this.y <= enemy.getY() && this.y + HEIGHT >= enemy.getY() + enemy.getHeight());
    }

    public int[] getCoordinate() {
        return this.COORDINATE;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(this.colour);
        g2d.fillPolygon(this.grid);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(this.grid);
        g2d.setStroke(new BasicStroke(1));
    }

}