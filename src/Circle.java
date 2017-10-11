import processing.core.PApplet;

class Circle extends Shape {

    public Circle(Point point, int type) {
        super(point, type);

    }

    @Override
    public void draw(PApplet pApplet) {
        if (isClicked) {
            pApplet.fill(100, 250, 100, 50);
        } else {
            pApplet.fill(100, 250, 100);
        }
        pApplet.ellipse(point.getX(), point.getY(), 30, 30);
    }

    @Override
    public Circle clone() {
        return (Circle) super.clone();
    }

    @Override
    public boolean checkCollision(Point clickedPoint) {
        return (point.getX() - clickedPoint.getX()) * (point.getX() - clickedPoint.getX())
                + (point.getY() - clickedPoint.getY()) * (point.getY() - clickedPoint.getY()) <= 900;
    }

}