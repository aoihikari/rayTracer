package tracer.shape;

import tracer.Ray;
import util.Color;
import util.Vector3D;

public class Sphere extends Shape {

    private Vector3D center;
    private double radius;
    private Color color;
    private double diffuse;
    private double specular;
    private double reflective;
    private int phong;

    public Sphere(Vector3D center, double radius) {
        this.center = center;
        this.radius = radius;

    }

    public Vector3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Vector3D getNormalAt(Vector3D intersection) {
        return intersection.add(center.negative()).normalize();
    }

    @Override
    public double findIntersection(Ray ray) {
        double xRayOrigin = ray.getOrigin().getX();
        double yRayOrigin = ray.getOrigin().getY();
        double zRayOrigin = ray.getOrigin().getZ();

        double xRayDirection = ray.getDirection().getX();
        double yRayDirection = ray.getDirection().getY();
        double zRayDirection = ray.getDirection().getZ();

        double xSphereCenter = center.getX();
        double ySphereCenter = center.getY();
        double zSphereCenter = center.getZ();

        double a = 1; // not needed
        double b = (2 * (xRayOrigin - xSphereCenter) * xRayDirection) + (2 * (yRayOrigin - ySphereCenter) * yRayDirection) + (2 * (zRayOrigin - zSphereCenter) * zRayDirection);
        double c = Math.pow(xRayOrigin - xSphereCenter, 2) + Math.pow(yRayOrigin - ySphereCenter, 2) + Math.pow(zRayOrigin - zSphereCenter, 2) - Math.pow(radius, 2);

        double discriminant = Math.pow(b, 2) - (4 * a * c);

        if(discriminant > 0) {
            double firstRoot = ((-1 * b - Math.sqrt(discriminant)) / 2) - .0000001;
            if(firstRoot > 0) {
                return firstRoot;
            } else {
                return ((-1 * b + Math.sqrt(discriminant)) / 2) - .0000001;
            }
        }
        return -1;
    }

	public void setColor(Color color) {
		this.color = color;
	}

	public double getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(double diffuse) {
		this.diffuse = diffuse;
	}

	public double getSpecular() {
		return specular;
	}

	public void setSpecular(double specular) {
		this.specular = specular;
	}

	@Override
	public double getReflective() {
		return reflective;
	}

	public void setReflective(double reflective) {
		this.reflective = reflective;
	}

	public int getPhong() {
		return phong;
	}

	public void setPhong(int phong) {
		this.phong = phong;
	}
}
