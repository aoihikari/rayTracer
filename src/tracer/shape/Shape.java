package tracer.shape;

import tracer.Ray;
import tracer.scene.material.Material;
import util.Color;
import util.Vector3D;

public abstract class Shape {

    public static double EPSILON = 0.0000001;

    public Color getColor() {
        return new Color(0, 0, 0);
    }

    public Material getMaterial() { return new Material(); }

    public Vector3D getNormalAt(Vector3D intersection) {
        return new Vector3D(0, 0, 0);
    }

    public double findIntersection(Ray ray) {
        return 0;
    }

}
