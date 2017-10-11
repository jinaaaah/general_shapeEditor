import processing.core.PApplet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeEditor extends PApplet {
    private static final String FILE_PATH = "/Users/ijina/IdeaProjects/shapeEditor/src/shapeList.txt";

    private static final int RECTANGLE = 1;
    private static final int CIRCLE = 2;
    private static final int TRIANGLE = 3;

    private List<Shape> shapeList;

    private String shapeInfo = "";

    private boolean isCtrlPressed;
    private boolean isDPressed;
    private boolean isSPressed;
    private boolean isOPressed;

    @Override
    public void keyPressed() {
        if (keyCode == CONTROL) {
            isCtrlPressed = true;
        }
        switch ((char) keyCode) {
            case 'D':
                isDPressed = true;
                break;
            case 'S':
                isSPressed = true;
                break;
            case 'O':
                isOPressed = true;
                break;
        }

        if (isCtrlPressed) {
            if (isDPressed) {
                duplicateShape();
            }
            if (isOPressed) {
                readList();
            }
            if (isSPressed) {
                saveShapes();
            }
        }
    }

    private void saveShapes() {
        String listInfo = "";
        for (Shape s : shapeList) {
            listInfo += s.getType() + ":" + s.getPoint().getX() + ":" + s.getPoint().getY() + ":"
                    + "255:255:255:";
        }
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(listInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readList() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int ch;
            while ((ch = bis.read()) != -1) {
                shapeInfo += (char) ch;
                System.out.print((char) ch);
            }
            getInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getInfo() {
        String[] str = shapeInfo.split(":");
        Shape s = null;

        for (int i = 0; i < str.length; i += 6) {
            int type = parseInt(str[i]);
            Point point = new Point(parseInt(str[i + 1]), parseInt(str[i + 2]));

            switch (type) {
                case 1:
                    s = new Rect(point, RECTANGLE);
                    break;
                case 2:
                    s = new Circle(point, CIRCLE);
                    break;
                case 3:
                    s = new Triangle(point, TRIANGLE);
                    break;
            }
            shapeList.add(s);
        }
    }

    @Override
    public void keyReleased() {
        isCtrlPressed = false;
        isDPressed = false;
        isSPressed = false;
        isOPressed = false;
    }

    private void duplicateShape() {
        Point clickedPoint = new Point(mouseX, mouseY);
        Shape checkShape = detectShape(clickedPoint);

        if (checkShape != null) {
            Shape cloneShape = checkShape.clone();
            cloneShape.setPoint(new Point(checkShape.getPoint().getX() + 30,
                    checkShape.getPoint().getY() + 30));
            shapeList.add(cloneShape);
        }
    }

    private void addShape(Point clickedPoint) {
        Shape shape = null;
        if (key == '1') {
            shape = new Rect(clickedPoint, RECTANGLE);
        } else if (key == '2') {
            shape = new Circle(clickedPoint, CIRCLE);
        } else if (key == '3') {
            shape = new Triangle(clickedPoint, TRIANGLE);
        }

        if (shape != null)
            shapeList.add(shape);

    }

    public Shape detectShape(Point pressedPoint) {
        for (Shape s : shapeList) {
            if (s.checkCollision(pressedPoint)) {
                if (s.getOffset() == null) {
                    s.setOffset(pressedPoint);
                }
                return s;
            }
        }
        return null;
    }

    @Override
    public void mouseDragged() {
        Point point = new Point(mouseX, mouseY);
        Shape clickedShape = detectShape(point);
        if (clickedShape != null) {
            clickedShape.maintainDistance(point);
        }

    }

    @Override
    public void mousePressed() {
        Point clickedPoint = new Point(mouseX, mouseY);
        Shape clickedShape = detectShape(clickedPoint);
        if (!keyPressed) {
            if (clickedShape != null) {
                clickedShape.highlight(true);
            }
            return;
        }

        addShape(clickedPoint);

    }

    @Override
    public void mouseReleased() {
        Point clickedPoint = new Point(mouseX, mouseY);
        Shape clickedShape = detectShape(clickedPoint);
        if (clickedShape != null) {
            clickedShape.highlight(false);
        }
    }

    @Override
    public void draw() {
        background(255);

        for (Shape shape : shapeList) {
            shape.draw(this);

        }
    }

    @Override
    public void settings() {
        size(600, 600);
        shapeList = new ArrayList<>();
    }

    @Override
    public void setup() {
        background(255);
    }

    public static void main(String[] args) {
        PApplet.main("ShapeEditor");
    }

}
