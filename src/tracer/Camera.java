package tracer;

import util.Vector3D;

public class Camera {

    private Vector3D position;
    private Vector3D direction;
    private Vector3D right;
    private Vector3D up;

    public Camera(Vector3D lookAt, Vector3D lookFrom, Vector3D lookUp) {
        position = lookFrom;
        Vector3D difference = new Vector3D(lookFrom.getX() - lookAt.getX(), lookFrom.getY() - lookAt.getY(), lookFrom.getZ() - lookAt.getZ());
        direction = difference.negative().normalize();
        right = Vector3D.Y().cross(direction).normalize().negative();
        up = lookUp.negative();
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Vector3D getRight() {
        return right;
    }

    public Vector3D getUp() {
        return up;
    }
}
