package util;

public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public double dot(Vector3D v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3D normalize() {
        double mag = magnitude();
        return new Vector3D(x / mag, y / mag, z / mag);
    }

    public Vector3D negative() {
        return new Vector3D(-x, -y, -z);
    }

    public Vector3D cross(Vector3D v) {
        return new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public Vector3D add(Vector3D v) {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    public Vector3D multiply(double s) {
        return new Vector3D(x * s, y * s, z * s);
    }

    public static Vector3D Origin() {
        return new Vector3D(0, 0, 0);
    }

    public static Vector3D X() {
        return new Vector3D(1, 0, 0);
    }

    public static Vector3D Y() {
        return new Vector3D(0, 1, 0);
    }

    public static Vector3D Z() {
        return new Vector3D(0, 0, 1);
    }


}
