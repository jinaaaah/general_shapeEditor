import processing.core.PApplet;

class Shape implements Cloneable {

    private int type;
    protected Point point;
    protected Point offset;
    protected boolean isClicked = false;

    public Shape(Point point, int type) {
        this.point = point;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setOffset(Point pivot) {
        offset = new Point(point.getX() - pivot.getX(),
                point.getY() - pivot.getY());
    }

    public Point getOffset() {
        return offset;
    }

    public void maintainDistance(Point clickedPoint) {
        point.setX(clickedPoint.getX() - offset.getX());
        point.setY(clickedPoint.getY() - offset.getY());
    }

    public void highlight(Boolean isClicked) {
        this.isClicked = isClicked;
    }

    public void draw(PApplet pApplet) {
    }

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkCollision(Point clickedPoint) {
        return false;
    }
}
