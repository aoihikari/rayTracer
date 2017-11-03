package scenes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import shapes.Shape;
import shapes.Sphere;
import shapes.Triangle;
import miscellaneous.Camera;
import miscellaneous.Color;
import miscellaneous.Light;
import miscellaneous.Vector3D;

public class SceneI extends Scene {

	private File image = new File("diffuse.png");
	private Color ambient  = new Color(.1, .1, .1);
    private Color backgroundColor = new Color(.2, .2, .2);
    private List<Shape> shapes = new ArrayList<Shape>();

	private Vector3D cameraLookAt = new Vector3D(0, 0, 0);
    private Vector3D cameraLookFrom = new Vector3D(0, 0, 1);
    private Vector3D cameraLookUp = new Vector3D(0, 1, 0).negative();
    
    private double fieldOfView = 28;
    
    private Vector3D directionToLight = new Vector3D(1, 0, 0);
    private Color lightColor = new Color(1, 1, 1);
    
    private Light light = new Light(directionToLight, lightColor);
    
    private Sphere sphere1 = new Sphere(new Vector3D(.35, 0, -.1), .05);
    private Sphere sphere2 = new Sphere(new Vector3D(.2, 0, -.1), .075);
    private Sphere sphere3 = new Sphere(new Vector3D(-.6, 0, 0), .3);
    
    private Triangle triangle1 = new Triangle(
    		new Vector3D(.3, -.3, -.4), 
    		new Vector3D(0, .3, -.1),
    		new Vector3D(-.3, -.3, .2));
    
    private Triangle triangle2 = new Triangle(
    		new Vector3D(-.2, .1, .1), 
    		new Vector3D(-.2, -.5, .2),
    		new Vector3D(-.2, .1, -.3));
    
    private Camera camera = new Camera(cameraLookAt, cameraLookFrom, cameraLookUp);
    
    public SceneI() {
    	
    	sphere1.setColor(new Color(1, 1, 1));
    	sphere1.setDiffuse((1 + 1 + 1) / 3);
    	sphere1.setSpecular((1 + 1 + 1) / 3);
    	sphere1.setPhong(4);
    	
    	shapes.add(sphere1);
    	
    	sphere2.setColor(new Color(1, 0, 0));
    	sphere2.setDiffuse((1 + 0 + 0) / 3);
    	sphere2.setSpecular((.5 + 1 + .5) / 3);
    	sphere2.setPhong(32);
    	
    	shapes.add(sphere2);
    	
    	sphere3.setColor(new Color(0, 1, 0));
    	sphere3.setDiffuse((0 + 1 + 0) / 3);
    	sphere3.setSpecular((.5 + 1 + .5) / 3);
    	sphere3.setPhong(32);
    	
    	shapes.add(sphere3);
    	
    	triangle1.setColor(new Color(0, 0, 1));
    	triangle1.setSpecular(new Vector3D(1, 1, 1));
    	triangle1.setPhong(32);
    	
    	shapes.add(triangle1);
    	
    	triangle2.setColor(new Color(1, 1, 0));
    	triangle2.setSpecular(new Vector3D(1, 1, 1));
    	triangle2.setPhong(4);
    	
    	shapes.add(triangle2);
    }

    public List<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
    
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public Color getAmbient() {
		return ambient;
	}

	public void setAmbient(Color ambient) {
		this.ambient = ambient;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public double getFieldOfView() {
		return fieldOfView;
	}

	public void setFieldOfView(double fieldOfView) {
		this.fieldOfView = fieldOfView;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}	
}
