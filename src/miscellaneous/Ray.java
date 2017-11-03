package miscellaneous;

public class Ray {

    private Vector3D origin;
    private Vector3D direction;

    public Ray(Vector3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3D getDirection() {
        return direction;
    }
    
    public Vector3D getOrigin() {
        return origin;
    }
}
