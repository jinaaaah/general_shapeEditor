import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import processing.core.PApplet;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShapeEditor extends PApplet {
    private static final String FILE_PATH = "/Users/ijina/IdeaProjects/shapeEditor/src/list.json";

    private static final int RECTANGLE = 1;
    private static final int CIRCLE = 2;
    private static final int TRIANGLE = 3;

    private boolean isCtrlPressed;
    private boolean isDPressed;
    private boolean isSPressed;
    private boolean isOPressed;

    private List<Shape> shapeList;
    private Shape selectedShape;
    private Point selectedPoint;

    Gson gson;

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

    @Override
    public void keyReleased() {
        isCtrlPressed = false;
        isDPressed = false;
        isSPressed = false;
        isOPressed = false;
    }

    private void saveShapes() {

        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter())
                    .create();
        }
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            String json = gson.toJson(shapeList);
            bos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readList() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter())
                    .create();
        }
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int ch;
            StringBuilder json = new StringBuilder();
            while ((ch = bis.read()) != -1) {
                json.append((char) ch);
                System.out.print((char) ch);
            }
            Type type = new TypeToken<List<Shape>>() {
            }.getType();
            shapeList = gson.fromJson(json.toString(), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void duplicateShape() {
        selectedPoint = new Point(mouseX, mouseY);
        selectedShape = detectShape(selectedPoint);

        if (selectedShape != null) {
            Shape cloneShape = selectedShape.clone();
            cloneShape.setPoint(new Point(selectedShape.getPoint().getX() + 30,
                    selectedShape.getPoint().getY() + 30));
            shapeList.add(cloneShape);
        }
    }

    private void addShape() {
        Shape shape = null;
        if (key == '1') {
            shape = new Rect(selectedPoint, RECTANGLE);
        } else if (key == '2') {
            shape = new Circle(selectedPoint, CIRCLE);
        } else if (key == '3') {
            shape = new Triangle(selectedPoint, TRIANGLE);
        }

        if (shape != null)
            shapeList.add(shape);

    }

    public Shape detectShape(Point pressedPoint) {
        for (int i = shapeList.size() - 1; i >= 0; i--) {
            if (shapeList.get(i).checkCollision(pressedPoint)) {
                Shape s = shapeList.get(i);
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
        selectedPoint = new Point(mouseX, mouseY);
        if (selectedShape != null) {
            selectedShape.maintainDistance(selectedPoint);
        }
    }

    @Override
    public void mousePressed() {
        selectedPoint = new Point(mouseX, mouseY);
        selectedShape = detectShape(selectedPoint);
        if (!keyPressed) {
            if (selectedShape != null) {
                selectedShape.highlight(true);
            }
            return;
        }

        addShape();

    }

    @Override
    public void mouseReleased() {
        if (selectedShape != null) {
            selectedShape.highlight(false);
        }
        selectedShape = null;
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
