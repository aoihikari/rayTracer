package tracer;

import tracer.scene.Scene;
import tracer.shape.Shape;
import util.Color;
import util.Pixel;
import util.Vector3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tracer {

    private int width;
    private int height;
    private File image;
    private BufferedImage buffer;
    private Scene scene;
    private String sceneName;

    private static final int MAX_DEPTH = 20;

    public Tracer() {
        width = 1600;
        height = 1600;
        image = new File("default.png");
        sceneName = "test3.scene";
        init();
    }

    public Tracer(int width, int height, String sceneName) {
        this.width = width;
        this.height = height;
        this.sceneName = sceneName;
        String[] tokens = sceneName.split(".rayTracing");
        String filename = tokens[0] + ".png";
        this.image = new File(filename);
        init();
    }

    private void init() {
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        scene = new Scene();
        try {
            scene.loadScene(sceneName);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
