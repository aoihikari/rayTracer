import tracer.Tracer;

public class Main {

    public static void main(String[] args) {
        //runTracer("diffuse.rayTracing");
        //runTracer("SceneII.rayTracing");
        runTracer("MyScene.rayTracing");
    }

    public static void runTracer(String scene) {
        Tracer tracer = new Tracer(800, 800, scene);
        tracer.trace();
        tracer.saveImage();
    }
}
