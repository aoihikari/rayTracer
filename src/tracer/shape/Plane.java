package tracer.shape;

import tracer.Ray;
import util.Color;
import util.Vector3D;

public class Plane extends Shape {

    private Vector3D normal;
    private double distance;
    private Color color;

    public Plane() {
        normal = new Vector3D(0, 1, 0);
        distance = 0;
        color = new Color(.5, .5, .5);
    }

    public Plane(Vector3D normal, double distance, Color color) {
        this.normal = normal;
        this.distance = distance;
        this.color = color;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Vector3D getNormalAt(Vector3D intersection) {
        return normal;
    }

    @Override
    public double findIntersection(Ray ray) {
        double a = ray.getDirection().dot(normal);
        if(a == 0) {
            return -1;
        } else {
            double b = normal.dot(ray.getOrigin().add(normal.multiply(distance).negative()));
            return -1 * b / a;
        }
    }
}
