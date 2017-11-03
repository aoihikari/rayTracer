package miscellaneous;

import scenes.Scene;
import scenes.SceneI;
import scenes.SceneII;
import scenes.SceneIII;
import shapes.Shape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class RayTracer {

	private int width = 1600;
    private int height = 1600;
    
    private BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private SceneI sceneI = new SceneI();
    private SceneII sceneII = new SceneII();
    private SceneIII sceneIII = new SceneIII();

    public void run() {     
        trace(sceneI);
        trace(sceneII);
        trace(sceneIII);
    }
    
    public void trace(Scene scene) {
        double aspectRatio = width / height;
        
        double red = 0;
        double green = 0;
        double blue = 0;

        double xOffset = 0;
        double yOffset = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

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
                    red = scene.getBackgroundColor().getRed();
                    green = scene.getBackgroundColor().getGreen();
                    blue = scene.getBackgroundColor().getBlue();
                } else {
                    if(intersections.get(closestObject) > 0.0000001) {
                        Vector3D intersectionPosition = ray.getOrigin().add(ray.getDirection().multiply(intersections.get(closestObject)));
                        Vector3D intersectionDirection = ray.getDirection();

                        Color intersectionColor = getColorAt(scene, intersectionPosition, intersectionDirection, closestObject, 0);
                        red = intersectionColor.getRed();
                        green = intersectionColor.getGreen();
                        blue = intersectionColor.getBlue();
                    }
                }
                buffer.setRGB(x, y, (int)(red * 255) << 16 | (int)(green * 255) << 8 | (int)(blue * 255));
            }
        }
        
        try {
			ImageIO.write(buffer, "PNG", scene.getImage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public RayTracer() {
        
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

    private Color getColorAt(Scene scene, Vector3D intersectionPosition, Vector3D intersectionDirection, int closestObject, int depth) {
        depth += 1;
        Shape shape = scene.getShapes().get(closestObject);
        Color shapeColor = shape.getColor();
        Vector3D shapeNormal = scene.getShapes().get(closestObject).getNormalAt(intersectionPosition);

        Color finalColor = shapeColor.multiply(scene.getAmbient());


        // Diffuse & Specular
        Vector3D lightDirection = scene.getLight().getPosition().add(intersectionPosition.negative()).normalize();
        double cosine = shapeNormal.dot(lightDirection);
        if(cosine > 0) {
            boolean inShadow = false;

            Vector3D distanceToLight = scene.getLight().getPosition().add(intersectionPosition.negative()).normalize();
            double lightMagnitude = distanceToLight.magnitude();
            Ray shadowRay = new Ray(intersectionPosition, scene.getLight().getPosition().add(intersectionPosition.negative()).normalize());

            ArrayList<Double> shadowIntersections = new ArrayList<>();
            for (int shapeIndex = 0; shapeIndex < scene.getShapes().size(); shapeIndex++) {
            	shadowIntersections.add(scene.getShapes().get(shapeIndex).findIntersection(shadowRay));
            }

            for (Double secondaryIntersection : shadowIntersections) {
                if (secondaryIntersection > 0.0000001) {
                    if (secondaryIntersection <= lightMagnitude) {
                        inShadow = true;
                        break;
                    }
                }
            }

            if(!inShadow) {
                finalColor = finalColor.add(shapeColor.multiply(scene.getLight().getColor()).scale(cosine));
                if(shape.getReflective() > 0 && shape.getReflective() <= 1) {
                    double dot1 = shapeNormal.dot(intersectionDirection.negative());
                    Vector3D scalar1 = shapeNormal.multiply(dot1);
                    Vector3D add1 = scalar1.add(intersectionDirection);
                    Vector3D scalar2 = add1.multiply(2);
                    Vector3D add2 = intersectionDirection.negative().add(scalar2);
                    Vector3D reflection = add2.normalize();

                    double specular = reflection.dot(lightDirection);
                    if(specular > 0) {
                        specular = Math.pow(specular, 20);
                        finalColor = finalColor.add(scene.getLight().getColor().scale(specular * shape.getReflective()));
                    }
                }
            }
        }

        // Reflection
        if(shape.getReflective() > 0 && shape.getReflective() <= 1 && depth < 20) {
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
                if(reflectionIntersections.get(reflectionObject) > 0.0000001) {
                    Vector3D reflectionPosition = intersectionPosition.add(reflection.multiply(reflectionIntersections.get(reflectionObject)));
                    Color reflectionColor = getColorAt(scene, reflectionPosition, reflection, reflectionObject, depth);
                    finalColor = finalColor.add(reflectionColor.scale(shape.getReflective()));
                }
            }
        }

        finalColor = finalColor.clip();
        return finalColor;
    }
}
