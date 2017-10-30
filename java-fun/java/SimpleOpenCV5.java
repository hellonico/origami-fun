import org.opencv.core.*;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.core.Core.*;
import static org.opencv.imgcodecs.Imgcodecs.*;
import clojure.lang.RT;
import java.util.Arrays;

public class SimpleOpenCV5 {
    static {
        RT.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
      Mat cat = imread("images/cat.jpg");

      cvtColor(cat,cat,COLOR_RGB2GRAY);
      Canny(cat,cat,150.0,100.0,3,true);

      Mat cat2 = cat.clone();
      bitwise_not(cat2,cat2);

      Mat target = new Mat();
      hconcat(Arrays.asList(new Mat[]{cat,cat2}), target);

      imwrite("output/canny-cat.png", target);
    }
}
