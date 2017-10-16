import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

class ShapeTypeAdapter extends TypeAdapter<Shape> {

    @Override
    public void write(JsonWriter writer, Shape shape) throws IOException {
        writer.beginObject();
        writer.name("type").value(shape.getClass().getName());
        writer.name("x").value(String.valueOf(shape.getPoint().getX()));
        writer.name("y").value(String.valueOf(shape.getPoint().getY()));
        writer.endObject();
    }

    @Override
    public Shape read(JsonReader reader) throws IOException {
        reader.beginObject();

        int x = -88;
        int y = -88;
        Shape s = null;
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "type":
                    String type = reader.nextString();
                    if (type.equals("Rect"))
                        s = new Rect(new Point(x,y), 1);
                    else if (type.equals("Circle"))
                        s = new Circle(new Point(x,y), 2);
                    else if (type.equals("Triangle"))
                        s = new Triangle(new Point(x,y), 3);

                    break;
                case "x":
                    if (s != null) {
                        x = reader.nextInt();
                    }
                    break;
                case "y":
                    if (s != null) {
                        y = reader.nextInt();
                        s.setPoint(new Point(x,y));
                    }
                    break;
            }
        }

        reader.endObject();
        return s;
    }
}
