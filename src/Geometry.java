public class Geometry {

    public static boolean isColliding(int x1, int y1, int l1, int h1, int x2, int y2, int l2, int h2) {
        if (y2 + h2 < y1 || x2 + l2 < x1 || y2 > y1 + h1 || x2 > x1 + l1) {
            return false;
        } else {
            return true;
        }
    }
}
