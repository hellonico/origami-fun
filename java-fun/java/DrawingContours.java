import org.opencv.core.*;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import clojure.lang.RT;
import java.util.List;
import java.util.ArrayList;

public class DrawingContours {
    static {
        RT.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat image = Imgcodecs.imread("images/lena.png");
        Mat contours = findAndDrawContours(image);
        Imgcodecs.imwrite("target/contours.png", contours);
    }

    static Mat findAndDrawContours(Mat image) {
        Mat imageHSV = new Mat();
        Mat imageBlurr = new Mat();
        Mat imageA = new Mat();
        Imgproc.cvtColor(image, imageHSV, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imageHSV, imageBlurr, new Size(5,5), 0);
        Imgproc.adaptiveThreshold(imageBlurr, imageA, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,7, 5);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imageA, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        Mat contourImg = image.clone();

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(contourImg, contours, i, new Scalar(255, 255, 255), -1);
        }

        return contourImg;
    }
}