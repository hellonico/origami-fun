import org.opencv.core.*;

import static java.lang.System.loadLibrary;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgcodecs.Imgcodecs.*;

public class SimpleOpenCV3 {
    static {
        loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat lena = imread("images/lena.png");
        imwrite("target/blurred100.png", blur_(lena,100));
    }

    public static Mat blur_(Mat input, int numberOfTimes){
        for(int i=0;i<numberOfTimes;i++){
            blur(input, input, new Size(9.0, 9.0));
        }
        return input;
    }
}
