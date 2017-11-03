package scenes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import shapes.Shape;
import shapes.Sphere;
import miscellaneous.Camera;
import miscellaneous.Color;
import miscellaneous.Light;
import miscellaneous.Vector3D;

public class SceneIII extends Scene {

	private File image = new File("MyScene.png");
	private Color ambient  = new Color(.1, .1, .1);
    private Color backgroundColor = new Color(.2, .2, .2);
    private List<Shape> shapes = new ArrayList<Shape>();

	private Vector3D cameraLookAt = new Vector3D(0, 0, 0);
    private Vector3D cameraLookFrom = new Vector3D(0, 0, -2);
    private Vector3D cameraLookUp = new Vector3D(0, 1, 0).negative();
    
    private double fieldOfView = 30;
    
    private Vector3D directionToLight = new Vector3D(0, 5, -10);
    private Color lightColor = new Color(1, 1, 1);
    
    private Light light = new Light(directionToLight, lightColor);
    
    private Sphere sphere1 = new Sphere(new Vector3D(.35, 0, -.1), .1);
    private Sphere sphere2 = new Sphere(new Vector3D(.2, 0, -.1), .1);
    private Sphere sphere3 = new Sphere(new Vector3D(-.6, 0, 0), .4);
    private Sphere sphere4 = new Sphere(new Vector3D(0, .3, 0), .3);
    private Sphere sphere5 = new Sphere(new Vector3D(1, 1, 1), .9);
    private Sphere sphere6 = new Sphere(new Vector3D(1, .5, .3), .2);
    private Sphere sphere7 = new Sphere(new Vector3D(-.3, -.4, 0), .1);
    private Sphere sphere8 = new Sphere(new Vector3D(-.1, -.3, -.3), .1);
    private Sphere sphere9 = new Sphere(new Vector3D(-.5, .5, 0), .2);
    private Sphere sphere10 = new Sphere(new Vector3D(-.2, -.5, .2), .2);
    private Sphere sphere11 = new Sphere(new Vector3D(-.2, .1, -.3), .2);
    private Sphere sphere12 = new Sphere(new Vector3D(0, -.5, .5), .3);
    private Sphere sphere13 = new Sphere(new Vector3D(1, .5, 0), .1);
    private Sphere sphere14 = new Sphere(new Vector3D(0, -.5, -.5), .2);
    
    private Camera camera = new Camera(cameraLookAt, cameraLookFrom, cameraLookUp);
    
    public SceneIII() {
    	
    	sphere1.setColor(new Color(1, 1, 1));
    	sphere1.setReflective((1 + 1 + 1) / 3);
    	
    	shapes.add(sphere1);
    	
    	sphere2.setColor(new Color(1, 0, 0));
    	sphere2.setReflective((1 + 0 + 0) / 3);
    	
    	shapes.add(sphere2);
    	
    	sphere3.setColor(new Color(1, 1, 1));
    	sphere3.setReflective((1 + 1 + 1) / 3);
    	
    	shapes.add(sphere3);
    	
    	sphere4.setColor(new Color(.75, .75, .75));
    	sphere4.setReflective((.75 + .75 + .75) / 3);
    	
    	shapes.add(sphere4);
    	
    	sphere5.setColor(new Color(1, .5, .5));
    	sphere5.setReflective((1 + .5 + .5) / 3);
    	
    	shapes.add(sphere5);
    	
    	sphere6.setColor(new Color(.9, .9, .98));
    	sphere6.setReflective((.9 + .9 + .98) / 3);
    	
    	shapes.add(sphere6);
    	
    	sphere7.setColor(new Color(0, 0, 1));
    	sphere7.setReflective((0 + 0 + 1) / 3);
    	
    	shapes.add(sphere7);
    	
    	sphere8.setColor(new Color(1, 1, 0));
    	sphere8.setReflective((1 + 1 + 0) / 3);
    	
    	shapes.add(sphere8);
    	
    	sphere9.setColor(new Color(1, .62, .47));
    	sphere9.setReflective((1 + .62 + .47) / 3);
    	
    	shapes.add(sphere9);
    	
    	sphere10.setColor(new Color(.74, .71, .42));
    	sphere10.setReflective((.74 + .71 + .42) / 3);
    	
    	shapes.add(sphere10);
    	
    	sphere11.setColor(new Color(.84, .74, .84));
    	sphere11.setReflective((.84 + .74 + .84) / 3);
    	
    	shapes.add(sphere11);
    	
    	sphere12.setColor(new Color(1, 0, 1));
    	sphere12.setReflective((1 + 0 + 1) / 3);
    	
    	shapes.add(sphere12);
    	
    	sphere13.setColor(new Color(.6, .19, .8));
    	sphere13.setReflective((.6 + .19 + .8) / 3);
    	
    	shapes.add(sphere13);
    	
    	sphere14.setColor(new Color(0, 1, 1));
    	sphere14.setReflective((0 + 1 + 1) / 3);
    	
    	shapes.add(sphere14);    	
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
