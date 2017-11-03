package miscellaneous;

public class Color {

    private double red;
    private double green;
    private double blue;

    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public Color scale(double s) {
        return new Color(red * s, green * s, blue * s);
    }

    public Color add(Color c) {
        return new Color(red + c.red, green + c.green, blue + c.blue);
    }

    public Color multiply(Color c) {
        return new Color(red * c.red, green * c.green, blue * c.blue);
    }

    public Color clip() {
        double all = red + green + blue;
        double excess = all - 3;
        if (excess > 0) {
            red = red + excess * (red / all);
            green = green + excess * (green / all);
            blue = blue + excess * (blue / all);
        }

        if(red > 1) {
            red = 1;
        } else if (red < 0) {
            red = 0;
        }

        if(green > 1) {
            green = 1;
        } else if(green < 0) {
            green = 0;
        }

        if(blue > 1) {
            blue = 1;
        } else if(blue < 0) {
            blue = 0;
        }

        return new Color(red, green, blue);
    }
}
