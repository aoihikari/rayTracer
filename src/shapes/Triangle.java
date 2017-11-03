package shapes;

import miscellaneous.Color;
import miscellaneous.Vector3D;
import miscellaneous.Ray;

public class Triangle extends Shape {

    private Vector3D vertex1;
    private Vector3D vertex2;
    private Vector3D vertex3;
    private Color color;
    private double diffuse;
    private double specular;
    private double reflective;
    private int phong = 1;

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
        double b = normal.dot(ray.getOrigin().add(normal.multiply(distance).negative()));
        
        double distanceToTriangle =  -1 * b / a;

        double x = ray.getDirection().multiply(distanceToTriangle).getX() + ray.getOrigin().getX();
        double y = ray.getDirection().multiply(distanceToTriangle).getY() + ray.getOrigin().getY();
        double z = ray.getDirection().multiply(distanceToTriangle).getZ() + ray.getOrigin().getZ();

        Vector3D newVector = new Vector3D(x, y, z);
        Vector3D oneMinusTwo = vertex1.add(vertex2.negative());
        Vector3D newMinusOne = newVector.add(vertex1.negative());
        Vector3D twoMinusThree = vertex2.add(vertex3.negative());
        Vector3D newMinusTwo = newVector.add(vertex2.negative());
        Vector3D threeMinusOne = vertex3.add(vertex1.negative());
        Vector3D newMinusThree = newVector.add(vertex3.negative());

        double check1 = oneMinusTwo.cross(newMinusTwo).dot(normal);
        double check2 = twoMinusThree.cross(newMinusThree).dot(normal);
        double check3 = threeMinusOne.cross(newMinusOne).dot(normal);
        
        if(a != 0) {     
            if(check1 >= 0 && check2 >= 0 && check3 >= 0) {
                return distanceToTriangle;
            }
        }
        return -1;
    }

	public double getSpecular() {
		return specular;
	}

	public void setSpecular(double specular) {
		this.specular = specular;
	}

	public int getPhong() {
		return phong;
	}

	public void setPhong(int phong) {
		this.phong = phong;
	}

	public double getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(double diffuse) {
		this.diffuse = diffuse;
	}

	public double getReflective() {
		return reflective;
	}

	public void setReflective(double reflective) {
		this.reflective = reflective;
	}
}
