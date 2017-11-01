import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

import static java.lang.System.loadLibrary;
import static java.lang.System.out;
import static org.opencv.imgcodecs.Imgcodecs.CV_IMWRITE_PNG_COMPRESSION;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class SampleCV1 {

    static {
        loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[]  args) {
//        sample0();
//        sample1();
        sample2();
    }
    static void sample0() {
        Mat mat = Imgcodecs.imread("images/kitten.jpg");
        imwrite("target/bw.jpg", mat);
    }
    static void sample1() {
        Mat mat = Imgcodecs.imread("images/kitten.jpg",  Imgcodecs.IMREAD_REDUCED_COLOR_8);
        out.println("mat ="+mat.width()+" x "+mat.height()+" "+mat.type());
        imwrite("target/bw.jpg", mat);
    }

    static void sample2() {
        Mat mat = Imgcodecs.imread("images/kitten.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        MatOfInt moi = new MatOfInt();
        moi.put(CV_IMWRITE_PNG_COMPRESSION, 10);
        Imgcodecs.imwrite("target/output.png", mat, moi);
    }
}
