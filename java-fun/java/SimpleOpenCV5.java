import org.opencv.core.*;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.core.Core.*;
import static org.opencv.imgcodecs.Imgcodecs.*;
import clojure.lang.RT;
import static org.opencv.core.CvType.CV_8UC3;
import org.opencv.imgproc.Imgproc;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SimpleOpenCV5 {
    static {
        RT.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

      // sample01();
      // sample02();
      sample03();
    }

    static void sample01() {
      Mat kittens = imread("images/three_black_kittens.jpg");

      cvtColor(kittens,kittens,COLOR_RGB2GRAY);
      Canny(kittens,kittens,100.0,300.0,3,true);
      imwrite("output/kittens-01.png", kittens);

      Mat invertedKittens = kittens.clone();
      bitwise_not(invertedKittens, invertedKittens);

      imwrite("output/kittens-02.png", invertedKittens);
    }

    static Scalar WHITE = new Scalar(255,255,255);
    static void sample02() {

      Mat kittens = imread("images/three_black_kittens.jpg");

      cvtColor(kittens,kittens,COLOR_RGB2GRAY);
      Canny(kittens,kittens,100.0,300.0,3, true);
      bitwise_not(kittens,kittens);
      // System.out.println(kittens.dump());

      Mat target = new Mat(kittens.height(), kittens.width(), CV_8UC3, WHITE );
      Mat bg = imread("images/light-blue-gradient.jpg");
      Imgproc.resize(bg, bg, target.size());
      bg.copyTo(target, kittens);
      imwrite("output/kittens-03.png", target);

    }

    static void sample03() {
      Mat kittens = imread("images/three_black_kittens.jpg");
      List contours = find_contours(kittens, true);

      Mat target = draw_contours(kittens, contours, 7);
      imwrite("output/kittens-contours-7.png", target);

      Mat masked = mask_on_bg(target, "images/light-blue-gradient.jpg");
      imwrite("output/kittens-masked.png", masked);

      target = draw_contours(kittens, contours, 3);
      imwrite("output/kittens-contours-3.png", target);


    }

    static Scalar BLACK = new Scalar(0,0,0) ;

    static Mat draw_contours(Mat originalMat, List contours, int thickness) {
        Mat target =
          new Mat(originalMat.height(), originalMat.width(), CV_8UC3, WHITE);

        for (int i = 0; i < contours.size(); i++)
            Imgproc.drawContours(target, contours, i, BLACK, thickness);

        return target;
    }

    static Mat mask_on_bg(Mat mask, String backgroundFilePath) {
        Mat target = new Mat(mask.height(),mask.width(),CV_8UC3,WHITE);
        Mat bg = imread(backgroundFilePath);
        Imgproc.resize(bg, bg, target.size());
        bg.copyTo(target, mask);
        return target;
    }

    static Mat draw_colored_contours(Mat originalMat, List contours, int thickness) {
        Mat target =
          new Mat(originalMat.height(), originalMat.width(), CV_8UC3, WHITE);
        Random rand = new Random();


        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            Imgproc.drawContours(target, contours, i, color, thickness);
          }

        return target;
    }

    static List find_contours(Mat image, boolean onBlank) {
        Mat imageBW = new Mat();

        Imgproc.cvtColor(image, imageBW, Imgproc.COLOR_BGR2GRAY);
        Canny(imageBW,imageBW,100.0,300.0,3, true);

        List contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imageBW, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
        return contours;

    }
}
