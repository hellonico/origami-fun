import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static java.lang.System.loadLibrary;
import static java.lang.System.out;
import static org.opencv.imgcodecs.Imgcodecs.CV_IMWRITE_PNG_COMPRESSION;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class CV1_Loading_Saving {

    static {
        loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[]  args) {
//        sample0();
//        sample1();
//        sample2();
//        sample3();
          sample4();
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
        Mat mat = Imgcodecs.imread("images/glasses.jpg");
        Imgcodecs.imwrite("target/output_nc.png", mat);

        MatOfInt moi1 = new MatOfInt(CV_IMWRITE_PNG_COMPRESSION, 1);
        Imgcodecs.imwrite("target/output_1.png", mat, moi1);
        MatOfInt moi9 = new MatOfInt(CV_IMWRITE_PNG_COMPRESSION, 9);
        Imgcodecs.imwrite("target/output_9.png", mat, moi9);
    }

    static void sample3() {
      Mat mat = Imgcodecs.imread("images/cat.jpg");
      out.println(mat);
      Mat submat = mat.submat(250,650,600,1000);
      out.println(submat);
      Imgcodecs.imwrite("output/subcat.png", submat);
    }

    static void sample4() {
      Mat mat = Imgcodecs.imread("images/cat.jpg");
      out.println(mat);
      Mat submat = mat.submat(250,650,600,1000);
      Imgproc.blur(submat,submat, new Size(35.0, 35.0));
      out.println(submat);
      Imgcodecs.imwrite("output/blurcat.png", mat);


      Mat submat2 = mat.submat(new Range(250,650), new Range(600,1000));
      Imgcodecs.imwrite("output/submat2.png", submat2);
      Mat submat3 = mat.submat(new Rect(600, 250, 400, 400));
      Imgcodecs.imwrite("output/submat3.png", submat3);

      submat3.setTo(new Scalar(255,0,0));
      Imgcodecs.imwrite("output/submat3_2.png", submat3);
      Imgcodecs.imwrite("output/submat3_3.png", submat2);
      Imgcodecs.imwrite("output/submat3_4.png", mat); 
    }
}
