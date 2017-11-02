package tracer.shape;

import tracer.Ray;
import util.Color;
import util.Vector3D;

public class Triangle extends Shape {

    private Vector3D pointA;
    private Vector3D pointB;
    private Vector3D pointC;

    private double distance;
    private Vector3D normal;
    private Color color;

    public Triangle(Vector3D a, Vector3D b, Vector3D c, Color color) {
        pointA = a;
        pointB = b;
        pointC = c;
        this.color = color;
        init();
    }

    private void init() {
        Vector3D CA = pointC.add(pointA.negative());
        Vector3D BA = pointB.add(pointA.negative());
        normal = CA.cross(BA).normalize();
        distance = normal.dot(pointA);

    }

    public Vector3D getPointA() {
        return pointA;
    }

    public Vector3D getPointB() {
        return pointB;
    }

    public Vector3D getPointC() {
        return pointC;
    }

    public double getDistance() {
        return distance;
    }

    public Vector3D getNormal() {
        return normal;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Vector3D getNormalAt(Vector3D intersection) {
        return normal.negative();
    }

    @Override
    public double findIntersection(Ray ray) {
        double a = ray.getDirection().dot(normal);
        if(a != 0) {
            double b = normal.dot(ray.getOrigin().add(normal.multiply(distance).negative()));
            double distanceToTriangle =  -1 * b / a;

            double qX = ray.getDirection().multiply(distanceToTriangle).getX() + ray.getOrigin().getX();
            double qY = ray.getDirection().multiply(distanceToTriangle).getY() + ray.getOrigin().getY();
            double qZ = ray.getDirection().multiply(distanceToTriangle).getZ() + ray.getOrigin().getZ();

            Vector3D Q = new Vector3D(qX, qY, qZ);

            Vector3D AB = pointA.add(pointB.negative());
            Vector3D QA = Q.add(pointA.negative());

            Vector3D BC = pointB.add(pointC.negative());
            Vector3D QB = Q.add(pointB.negative());

            Vector3D CA = pointC.add(pointA.negative());
            Vector3D QC = Q.add(pointC.negative());

            double test1 = AB.cross(QB).dot(normal);
            double test2 = BC.cross(QC).dot(normal);
            double test3 = CA.cross(QA).dot(normal);

            if(test1 >= 0 && test2 >= 0 && test3 >= 0) {
                return distanceToTriangle;
            }
        }
        return -1;
    }
}
