import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import tracer.Camera;
import tracer.Ray;
import tracer.Tracer;
import tracer.scene.Scene;
import tracer.scene.material.Material;
import tracer.shape.Plane;
import tracer.shape.Shape;
import tracer.shape.Sphere;
import tracer.shape.Triangle;
import util.Color;
import util.Light;
import util.LightSource;
import util.Pixel;
import util.Vector3D;

public class Main {

	private int width = 800;
    private int height = 800;
    private File image1 = new File("diffuse.png");
    private File image2 = new File("SceneII.png");
    private File image3 = new File("MyScene.png");
    private BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private Scene scene = new Scene();
    private List<LightSource> lights = new ArrayList<LightSource>();
    private Color ambient;
    private Color backgroundColor;
    private List<Shape> shapes;
    
    private static final int MAX_DEPTH = 20;
	
    public static void main(String[] args) {
    	try {
            Vector3D cameraLookAt = new Vector3D(0, 0, 0);
            Vector3D cameraLookFrom = new Vector3D(0, 0, 1);
            Vector3D cameraLookUp = new Vector3D(0, 1, 0).negative();

            double fov = Double.parseDouble(28);
            
            Vector3D directionToLight = new Vector3D(1, 0, 0);
            Color lightColor = new Color(1, 1, 1);

            Light light = new Light(directionToLight, lightColor);
            lights.add(light);
            
            ambient = new Color(.1, .1, .1);
            backgroundColor = new Color(.2, .2, .2);
            
            //Sphere
            
            Vector3D center = new Vector3D(.35, 0, -.1);
            double radius = Double.parseDouble(.05);
            
            color = new Color(1, 1, 1);
            material.setColor(color);
            material.setDiffuse((1 + 1 + 1) / 3);
            Vector3D specular = new Vector3D(1, 1, 1);
            material.setSpecular((1 + 1 + 1) / 3);
            double phong = Double.parseDouble(4);
            
            Sphere sphere = new Sphere(center, radius, material);
            shapes.add(sphere);
            
            //End of the sphere
            
            //Sphere
            
            Vector3D center = new Vector3D(.2, 0, -.1);
            double radius = Double.parseDouble(.075);
            
            color = new Color(1, 0, 0);
            material.setColor(color);
            material.setDiffuse((1 + 0 + 0) / 3);
            Vector3D specular = new Vector3D(.5, 1, .5);
            material.setSpecular((.5 + 1 + .5) / 3);
            double phong = Double.parseDouble(32);
            
            Sphere sphere = new Sphere(center, radius, material);
            shapes.add(sphere);
            
            //End of the sphere
            
            //Sphere
            
            Vector3D center = new Vector3D(-.6, 0, 0);
            double radius = Double.parseDouble(.3);
            
            color = new Color(0, 1, 0);
            material.setColor(color);
            material.setDiffuse((0 + 1 + 0) / 3);
            Vector3D specular = new Vector3D(.5, 1, .5);
            material.setSpecular((.5 + 1 + .5) / 3);
            double phong = Double.parseDouble(32);
            
            Sphere sphere = new Sphere(center, radius, material);
            shapes.add(sphere);
            
            //End of the sphere
            
            //Triange
            
            Vector3D pointA = new Vector3D(.3, -.3, -.4);
            Vector3D pointB = new Vector3D(0, .3, -.1);
            Vector3D pointC = new Vector3D(-.3, -.3, .2);
            
            color = new Color(0, 0, 1);
            
            color = new Color(1, 1, 1);
            Vector3D specular = new Vector3D(1, 1, 1);
            double phong = Double.parseDouble(32);
            
            Triangle triangle = new Triangle(pointA, pointB, pointC, color);
            shapes.add(triangle);
            
            //End of the triange
            
            //Triange
            
            Vector3D pointA = new Vector3D(-.2, .1, .1);
            Vector3D pointB = new Vector3D(-.2, -.5, .2);
            Vector3D pointC = new Vector3D(-.2, .1, -.3);
            
            color = new Color(1, 1, 0);
            
            color = new Color(1, 1, 1);
            Vector3D specular = new Vector3D(1, 1, 1);
            double phong = Double.parseDouble(4);
            
            Triangle triangle = new Triangle(pointA, pointB, pointC, color);
            shapes.add(triangle);
            
            //End of the triange
            
            camera = new Camera(cameraLookAt, cameraLookFrom, cameraLookUp);
            
            /*color = new Color(x, y, z);
            material.setColor(color);
            material.setReflective((x + y + z) / 3);
            
            color = new Color(0, 0, 0);
            material.setColor(color);
            material.setRefractive((x + y + z) / 3);*/
            
    } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
    }
    }

    public static void runTracer(String scene) {
        Tracer tracer = new Tracer(800, 800, scene);
        tracer.trace();
        tracer.saveImage();
    }
    
    

    

    public void saveImage() {
        try {
            ImageIO.write(buffer, "PNG", image);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void saveImage(String fileName) {
        try {
            image = new File(fileName);
            ImageIO.write(buffer, "PNG", image);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int getClosestObject(ArrayList<Double> intersections) {
        int index = -1;
        if(intersections.size() == 0) {
            index = -1;
        } else if(intersections.size() == 1) {
            if(intersections.get(0) > 0) {
                index = 0;
            }
        } else {
            double max = 0;
            for (Double intersection : intersections) {
                if (max < intersection) {
                    max = intersection;
                }
            }
            if(max > 0) {
                for (int i = 0; i < intersections.size(); i++) {
                    if(intersections.get(i) > 0 && intersections.get(i) <= max) {
                        max = intersections.get(i);
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    private Color getColorAt(Vector3D intersectionPosition, Vector3D intersectionDirection, int closestObject, int depth) {
        depth += 1;
        Shape shape = scene.getShapes().get(closestObject);
        Color shapeColor = shape.getColor();
        Vector3D shapeNormal = scene.getShapes().get(closestObject).getNormalAt(intersectionPosition);

        Color finalColor = shapeColor.multiply(scene.getAmbient());


        // Diffuse & Specular
        for (int lightIndex = 0; lightIndex < scene.getLights().size(); lightIndex++) {
            Vector3D lightDirection = scene.getLights().get(lightIndex).getPosition().add(intersectionPosition.negative()).normalize();
            double cosine = shapeNormal.dot(lightDirection);
            if(cosine > 0) {
                boolean inShadow = false;

                Vector3D distanceToLight = scene.getLights().get(lightIndex).getPosition().add(intersectionPosition.negative()).normalize();
                double lightMagnitude = distanceToLight.magnitude();
                Ray shadowRay = new Ray(intersectionPosition, scene.getLights().get(lightIndex).getPosition().add(intersectionPosition.negative()).normalize());

                ArrayList<Double> shadowIntersections = new ArrayList<>();
                for (int shapeIndex = 0; shapeIndex < scene.getShapes().size(); shapeIndex++) {
                    // No shadow from my refractive shapes
                    if(!(scene.getShapes().get(shapeIndex).getMaterial().getRefractive() > 0)) {
                        shadowIntersections.add(scene.getShapes().get(shapeIndex).findIntersection(shadowRay));
                    }
                }

                for (Double secondaryIntersection : shadowIntersections) {
                    if (secondaryIntersection > Shape.EPSILON) {
                        if (secondaryIntersection <= lightMagnitude) {
                            inShadow = true;
                            break;
                        }
                    }
                }

                if(!inShadow) {
                    finalColor = finalColor.add(shapeColor.multiply(scene.getLights().get(lightIndex).getColor()).scale(cosine));
                    if(shape.getMaterial().getReflective() > 0 && shape.getMaterial().getReflective() <= 1) {
                        double dot1 = shapeNormal.dot(intersectionDirection.negative());
                        Vector3D scalar1 = shapeNormal.multiply(dot1);
                        Vector3D add1 = scalar1.add(intersectionDirection);
                        Vector3D scalar2 = add1.multiply(2);
                        Vector3D add2 = intersectionDirection.negative().add(scalar2);
                        Vector3D reflection = add2.normalize();

                        double specular = reflection.dot(lightDirection);
                        if(specular > 0) {
                            specular = Math.pow(specular, 20);
                            finalColor = finalColor.add(scene.getLights().get(lightIndex).getColor().scale(specular * shape.getMaterial().getReflective()));
                        }
                    }
                }
            }
        }

        // Reflection
        if(shape.getMaterial().getReflective() > 0 && shape.getMaterial().getReflective() <= 1 && depth < MAX_DEPTH) {
            double dot1 = shapeNormal.dot(intersectionDirection.negative());
            Vector3D scalar1 = shapeNormal.multiply(dot1);
            Vector3D add1 = scalar1.add(intersectionDirection);
            Vector3D scalar2 = add1.multiply(2);
            Vector3D add2 = intersectionDirection.negative().add(scalar2);
            Vector3D reflection = add2.normalize();

            Ray reflectionRay = new Ray(intersectionPosition, reflection);
            ArrayList<Double> reflectionIntersections = new ArrayList<>();
            for(int i = 0; i < scene.getShapes().size(); i++) {
                reflectionIntersections.add(scene.getShapes().get(i).findIntersection(reflectionRay));
            }

            int reflectionObject = getClosestObject(reflectionIntersections);
            if(reflectionObject != -1) {
                if(reflectionIntersections.get(reflectionObject) > Shape.EPSILON) {
                    Vector3D reflectionPosition = intersectionPosition.add(reflection.multiply(reflectionIntersections.get(reflectionObject)));
                    Color reflectionColor = getColorAt(reflectionPosition, reflection, reflectionObject, depth);
                    finalColor = finalColor.add(reflectionColor.scale(shape.getMaterial().getReflective()));
                }
            }
        }

        // Refraction
        double refract = shape.getMaterial().getRefractive();
        if(refract > 0 && depth < MAX_DEPTH) {
            Ray refractionRay = new Ray(intersectionPosition, intersectionDirection);
            double cos = shapeNormal.dot(refractionRay.getDirection().negative());
            double cos2 = 1 - refract * refract * (1 - cos * cos);
            if(cos2 > 0) {
                Vector3D refraction = refractionRay.getDirection().multiply(refract).add(shapeNormal.multiply(refract * cos - Math.sqrt(cos2)));
                ArrayList<Double> refractionIntersections = new ArrayList<>();
                for(int i = 0; i < scene.getShapes().size(); i++) {
                    refractionIntersections.add(scene.getShapes().get(i).findIntersection(refractionRay));
                }
                int refractionObject = getClosestObject(refractionIntersections);
                if(refractionObject != -1) {
                    Vector3D refractionPosition = intersectionPosition.add(refraction.multiply(refractionIntersections.get(refractionObject)));
                    Color refractionColor = getColorAt(refractionPosition, refraction, refractionObject, depth);
                    finalColor = finalColor.add(refractionColor);
                }
            }


        }

        finalColor = finalColor.clip();
        return finalColor;
    }

    public void trace() {
        double aspectRatio = width / height;

        double xOffset = 0;
        double yOffset = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = new Pixel();

                if (width > height) {
                    xOffset = ((x + 0.5) / width) * aspectRatio - (((width - height) / height) / 2);
                    yOffset = ((height - y) + 0.5) / height;
                } else if (height > width) {
                    xOffset = (x + 0.5) / width;
                    yOffset = (((height - y) + 0.5) / height) / aspectRatio - (((height - width) / width) / 2);
                } else {
                    xOffset = (x + 0.5) / width;
                    yOffset = ((height - y) + 0.5) / height;
                }

                Vector3D rayDirection = scene.getCamera().getDirection().add(
                        scene.getCamera().getRight().multiply(xOffset - .5).add(
                                scene.getCamera().getUp().multiply(yOffset - .5))).normalize();
                Ray ray = new Ray(scene.getCamera().getPosition(), rayDirection);

                ArrayList<Double> intersections = new ArrayList<>();
                for (int i = 0; i < scene.getShapes().size(); i++) {
                    intersections.add(scene.getShapes().get(i).findIntersection(ray));
                }

                int closestObject = getClosestObject(intersections);
                if(closestObject == -1) {
                    pixel.red = scene.getBackgroundColor().getRed();
                    pixel.green = scene.getBackgroundColor().getGreen();
                    pixel.blue = scene.getBackgroundColor().getBlue();
                } else {
                    if(intersections.get(closestObject) > Shape.EPSILON) {
                        Vector3D intersectionPosition = ray.getOrigin().add(ray.getDirection().multiply(intersections.get(closestObject)));
                        Vector3D intersectionDirection = ray.getDirection();

                        Color intersectionColor = getColorAt(intersectionPosition, intersectionDirection, closestObject, 0);
                        pixel.red = intersectionColor.getRed();
                        pixel.green = intersectionColor.getGreen();
                        pixel.blue = intersectionColor.getBlue();
                    }
                }
                buffer.setRGB(x, y, pixel.toInteger());
            }
        }
    }
    
}
