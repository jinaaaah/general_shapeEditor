import processing.core.PApplet;

class Triangle extends Shape {
    
    public Triangle(Point point, int type) {
        super(point, type);
    }

    @Override
    public void draw(PApplet pApplet) {

        if (isClicked) {
            pApplet.fill(100, 100, 250, 50);
        } else {
            pApplet.fill(100, 100, 250);
        }
        pApplet.triangle(
                point.getX() - 25, point.getY() + 25,
                point.getX(), point.getY() - 25,
                point.getX() + 25, point.getY() + 25
        );
    }

    @Override
    public Triangle clone() {
        return (Triangle) super.clone();
    }

    private boolean sign(Point pivot) {
        Point point1 = new Point(point.getX() - 25, point.getY() + 25);
        Point point2 = new Point(point.getX(), point.getY() - 25);
        Point point3 = new Point(point.getX() + 25, point.getY() + 25);

        int as_x = pivot.getX() - point1.getX();
        int as_y = pivot.getY() - point1.getY();

        boolean s_ab = (point2.getX() - point1.getX()) * as_y
                - (point2.getY() - point1.getY()) * as_x > 0;

        return (point3.getX() - point1.getX()) * as_y
                - (point3.getY() - point1.getY()) * as_x > 0 != s_ab
                && (point3.getX() - point2.getX()) * (pivot.getY()
                - point2.getY()) - (point3.getY()
                - point2.getY()) * (pivot.getX() - point2.getX()) > 0 == s_ab;

    }

    @Override
    public boolean checkCollision(Point clickedPoint) {
        return sign(clickedPoint);
    }
}