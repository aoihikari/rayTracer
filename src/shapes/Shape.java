package shapes;

import miscellaneous.Color;
import miscellaneous.Vector3D;
import miscellaneous.Ray;

public abstract class Shape {

    public Color getColor() {
        return new Color(0, 0, 0);
    }

    public Vector3D getNormalAt(Vector3D intersection) {
        return new Vector3D(0, 0, 0);
    }

    public double findIntersection(Ray ray) {
        return 0;
    }

	public double getReflective() {
		return 0;
	}
}
