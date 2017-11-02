package util;

public abstract class LightSource {

    public Color getColor() {
        return new Color(1, 1, 1);
    }

    public Vector3D getPosition() {
        return new Vector3D(0, 0, 0);
    }

}
