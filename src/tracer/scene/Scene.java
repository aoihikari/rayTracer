package tracer.scene;

import tracer.Camera;
import tracer.scene.material.Material;
import tracer.shape.Plane;
import tracer.shape.Shape;
import tracer.shape.Sphere;
import tracer.shape.Triangle;
import util.Color;
import util.Light;
import util.LightSource;
import util.Vector3D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Camera camera;
    private double fov;
    private List<LightSource> lights;
    private Color ambient;
    private Color backgroundColor;
    private List<Shape> shapes;

    public Scene() {
        fov = 60;
        ambient = new Color(0.1, 0.1, 0.1);
        backgroundColor = new Color(0.2, 0.2, 0.2);
        lights = new ArrayList<LightSource>();
        shapes = new ArrayList<Shape>();
    }

    public Scene(Camera camera, double fov, Color ambient, Color backgroundColor) {
        this.camera = camera;
        this.fov = fov;
        this.ambient = ambient;
        this.backgroundColor = backgroundColor;
        lights = new ArrayList<LightSource>();
        shapes = new ArrayList<Shape>();
    }

    public Camera getCamera() {
        return camera;
    }

    public double getFov() {
        return fov;
    }

    public List<LightSource> getLights() {
        return lights;
    }

    public Color getAmbient() {
        return ambient;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void loadScene(String sceneFile) throws IOException {
        FileReader reader = new FileReader(sceneFile);
        BufferedReader buffer = new BufferedReader(reader);
        String line = "";

        Vector3D cameraLookAt = new Vector3D(0, 0, 0);
        Vector3D cameraLookFrom = new Vector3D(0, 0, 1);
        Vector3D cameraLookUp = new Vector3D(0, 1, 0);

        Vector3D directionToLight;
        Color lightColor;

        while((line = buffer.readLine()) != null) {
            String[] rawTokens = line.split(" ");
            String[] tokens = new String[rawTokens.length];
            int counter = 0;
            
            for (String x : rawTokens) {
            	if (x.equals("")) {
            		System.out.println("ops");
            	} else {
            		System.out.println(x);
            		tokens[counter] = x;
            		counter++;
            	}
            }
            
            double x = 0;
            double y = 0;
            double z = 0;
            switch (tokens[0]) {
                case "CameraLookAt":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    cameraLookAt = new Vector3D(x, y, z);
                    break;
                case "CameraLookFrom":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    cameraLookFrom = new Vector3D(x, y, z);
                    break;
                case "CameraLookUp":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    cameraLookUp = new Vector3D(x, y, z).negative();
                    break;
                case "FieldOfView":
                    fov = Double.parseDouble(tokens[1]);
                    break;
                case "DirectionToLight":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    directionToLight = new Vector3D(x, y, z);
                    x = Double.parseDouble(tokens[5]);
                    y = Double.parseDouble(tokens[6]);
                    z = Double.parseDouble(tokens[7]);
                    lightColor = new Color(x, y, z);
                    Light light = new Light(directionToLight, lightColor);
                    lights.add(light);
                    break;
                case "AmbientLight":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    ambient = new Color(x, y, z);
                    break;
                case "BackgroundColor":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    backgroundColor = new Color(x, y, z);
                    break;
                case "Sphere":
                    x = Double.parseDouble(tokens[2]);
                    y = Double.parseDouble(tokens[3]);
                    z = Double.parseDouble(tokens[4]);
                    Vector3D center = new Vector3D(x, y, z);
                    double radius = Double.parseDouble(tokens[6]);
                    Material material = new Material();
                    Color color = new Color(0, 0, 0);
                    switch (tokens[8]) {
                        case "Reflective":
                            x = Double.parseDouble(tokens[9]);
                            y = Double.parseDouble(tokens[10]);
                            z = Double.parseDouble(tokens[11]);
                            color = new Color(x, y, z);
                            material.setColor(color);
                            material.setReflective((x + y + z) / 3);
                            break;
                        case "Diffuse":
                            x = Double.parseDouble(tokens[9]);
                            y = Double.parseDouble(tokens[10]);
                            z = Double.parseDouble(tokens[11]);
                            color = new Color(x, y, z);
                            material.setColor(color);
                            material.setDiffuse((x + y + z) / 3);
                            x = Double.parseDouble(tokens[13]);
                            y = Double.parseDouble(tokens[14]);
                            z = Double.parseDouble(tokens[15]);
                            Vector3D specular = new Vector3D(x, y, z);
                            material.setSpecular((x + y + z) / 3);

                            double phong = Double.parseDouble(tokens[17]);

                            // Todo: implement phong

                            break;
                        case "Refractive":
                            x = Double.parseDouble(tokens[9]);
                            y = Double.parseDouble(tokens[10]);
                            z = Double.parseDouble(tokens[11]);
                            color = new Color(0, 0, 0);
                            material.setColor(color);
                            material.setRefractive((x + y + z) / 3);
                            break;
                        default:
                            System.out.println("ERROR: Unknown Material Type");
                            break;
                    }

                    Sphere sphere = new Sphere(center, radius, material);

                    shapes.add(sphere);
                    break;
                case "Triangle":
                    x = Double.parseDouble(tokens[1]);
                    y = Double.parseDouble(tokens[2]);
                    z = Double.parseDouble(tokens[3]);
                    Vector3D pointA = new Vector3D(x, y, z);
                    x = Double.parseDouble(tokens[4]);
                    y = Double.parseDouble(tokens[5]);
                    z = Double.parseDouble(tokens[6]);
                    Vector3D pointB = new Vector3D(x, y, z);
                    x = Double.parseDouble(tokens[7]);
                    y = Double.parseDouble(tokens[8]);
                    z = Double.parseDouble(tokens[9]);
                    Vector3D pointC = new Vector3D(x, y, z);
                    color = new Color(1, 1, 1);
                    switch (tokens[11]) {
                        case "Reflective":
                            x = Double.parseDouble(tokens[12]);
                            y = Double.parseDouble(tokens[13]);
                            z = Double.parseDouble(tokens[14]);
                            color = new Color(x, y, z);
                            break;
                        case "Diffuse":
                            x = Double.parseDouble(tokens[12]);
                            y = Double.parseDouble(tokens[13]);
                            z = Double.parseDouble(tokens[14]);
                            color = new Color(x, y, z);

                            x = Double.parseDouble(tokens[16]);
                            y = Double.parseDouble(tokens[17]);
                            z = Double.parseDouble(tokens[18]);
                            Vector3D specular = new Vector3D(x, y, z);

                            double phong = Double.parseDouble(tokens[17]);
                            break;
                        default:
                            System.out.println("ERROR: Unknown Material Type");
                            break;
                    }
                    Triangle triangle = new Triangle(pointA, pointB, pointC, color);
                    shapes.add(triangle);
                    break;
                case "Plane":
                    x = Double.parseDouble(tokens[2]);
                    y = Double.parseDouble(tokens[3]);
                    z = Double.parseDouble(tokens[4]);
                    Vector3D normal = new Vector3D(x, y, z);
                    double distance = Double.parseDouble(tokens[6]);
                    x = Double.parseDouble(tokens[9]);
                    y = Double.parseDouble(tokens[10]);
                    z = Double.parseDouble(tokens[11]);
                    color = new Color(.5, .5, .5);

                    Plane plane = new Plane(normal, distance, color);
                    shapes.add(plane);
                    break;
                default:
                    break;
            }
            camera = new Camera(cameraLookAt, cameraLookFrom, cameraLookUp);
        }
    }



}
