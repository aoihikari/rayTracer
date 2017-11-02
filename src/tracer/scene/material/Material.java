package tracer.scene.material;

import util.Color;

public class Material {

    private Color color;
    private double diffuse;
    private double specular;
    private double reflective;
    private double refractive;

    public Material() {
        color = new Color();
        diffuse = .2;
        specular = .8;
        reflective = 0;
        refractive = 0;
    }

    public Material(Color color, double diffuse, double specular, double reflective, double refractive) {
        this.color = color;
        this.diffuse = diffuse;
        this.specular = specular;
        this.reflective = reflective;
        this.refractive = refractive;
    }

    public Color getColor() {
        return color;
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

    public double getReflective() {
        return reflective;
    }

    public void setReflective(double reflective) {
        this.reflective = reflective;
    }

    public double getRefractive() {
        return refractive;
    }

    public void setRefractive(double refractive) {
        this.refractive = refractive;
    }
}
