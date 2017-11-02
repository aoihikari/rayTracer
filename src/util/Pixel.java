package util;

public class Pixel {

    public double red;
    public double green;
    public double blue;

    public Pixel() {
        red = 0;
        green = 0;
        blue = 0;
    }

    public Pixel(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Integer toInteger() {
        return (int)(red * 255) << 16 | (int)(green * 255) << 8 | (int)(blue * 255);
    }

    @Override
    public String toString() {
        return "Red: " + red + "\tGreen: " + green + "\tBlue: " + blue;
    }


}
