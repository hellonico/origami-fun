import org.opencv.core.*;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.core.CvType.*;
import static clojure.lang.RT.loadLibrary;

public class SimpleOpenCV4 {
    static {
        loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat mat = Mat.zeros(10, 10, CV_8UC1);
        for(int i = 0 ; i < 10 ;  i++) {
            mat.put(i,i, new double[]{255});
        }
        imwrite("output/mat1.png", mat);

        Mat mat2 = Mat.zeros(10, 10, CV_8UC1);
        for(int i = 0 ; i < 10 ;  i++) {
            mat2.put(i,10-i-1, new double[]{255});
        }
        imwrite("output/mat2.png", mat2);

        Mat target = new Mat();
        Core.add(mat, mat2, target);
        imwrite("output/add.png", target);

        Mat target2 = new Mat();
        blur(mat, target2, new Size(1,1));
        imwrite("output/blur1.png", target2);

        blur(mat, target2, new Size(3,3));
        imwrite("output/blur3.png", target2);

        blur(mat, target2, new Size(5,5));
        imwrite("output/blur5.png", target2);


    }
}
