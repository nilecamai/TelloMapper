import java.io.IOException;

public class TelloDemo {

    public static void main(String[] args) throws IOException {
        Process p = Runtime.getRuntime().exec("python Tello3.py");
    }

}
