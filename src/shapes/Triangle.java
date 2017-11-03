package shapes;

import miscellaneous.Color;
import miscellaneous.Vector3D;
import miscellaneous.Ray;

public class Triangle extends Shape {

    private Vector3D vertex1;
    private Vector3D vertex2;
    private Vector3D vertex3;
    private Color color;
    private Vector3D specular;
    private int phong;

    private double distance;
    private Vector3D normal;


    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        
        Vector3D threeMinusOne = vertex3.add(vertex1.negative());
        Vector3D TwoMinusOne = vertex2.add(vertex1.negative());
        normal = threeMinusOne.cross(TwoMinusOne).normalize();
        distance = normal.dot(vertex1);
    }

    public Vector3D getVertex1() {
        return vertex1;
    }

    public Vector3D getVertex2() {
        return vertex2;
    }

    public Vector3D getVertex3() {
        return vertex3;
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
    
    public void setColor(Color color) {
        this.color = color;
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

            Vector3D AB = vertex1.add(vertex2.negative());
            Vector3D QA = Q.add(vertex1.negative());

            Vector3D BC = vertex2.add(vertex3.negative());
            Vector3D QB = Q.add(vertex2.negative());

            Vector3D CA = vertex3.add(vertex1.negative());
            Vector3D QC = Q.add(vertex3.negative());

            double test1 = AB.cross(QB).dot(normal);
            double test2 = BC.cross(QC).dot(normal);
            double test3 = CA.cross(QA).dot(normal);

            if(test1 >= 0 && test2 >= 0 && test3 >= 0) {
                return distanceToTriangle;
            }
        }
        return -1;
    }

	public Vector3D getSpecular() {
		return specular;
	}

	public void setSpecular(Vector3D specular) {
		this.specular = specular;
	}

	public int getPhong() {
		return phong;
	}

	public void setPhong(int phong) {
		this.phong = phong;
	}
}
