package util;

public class Light extends LightSource {

    private Vector3D position;
    private Color color;

    public Light() {
        this.position = new Vector3D(0, 0, 0);
        this.color = new Color(1, 1, 1);
    }

    public Light(Vector3D position, Color color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public Vector3D getPosition() {
        return position;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
