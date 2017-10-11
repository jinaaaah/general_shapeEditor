import processing.core.PApplet;
import processing.core.PConstants;

class Rect extends Shape {

    public Rect(Point point, int type) {
        super(point, type);
    }

    @Override
    public void draw(PApplet pApplet) {
        pApplet.rectMode(PConstants.CENTER);
        if (isClicked) {
            pApplet.fill(250, 100, 100, 50);
        } else {
            pApplet.fill(250, 100, 100);
        }
        pApplet.rect(point.getX(), point.getY(), 50, 50);
    }

    @Override
    public Rect clone() {
        return (Rect) super.clone();
    }

    @Override
    public boolean checkCollision(Point clickedPoint) {
        return clickedPoint.getX() - 25 < point.getX() &&
                clickedPoint.getX() + 25 > point.getX() &&
                clickedPoint.getY() + 25 > point.getY() &&
                clickedPoint.getY() - 25 < point.getY();
    }
}