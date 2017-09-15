import org.opencv.core.*;
import static org.opencv.core.CvType.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs.*;
import static java.lang.System.*;
import clojure.lang.RT;

public class DrawingContours {

    public static void main(String[] args) {
         


    }

    static Mat findAndDrawContours(Mat image, List<MatOfPoint> edgeContours) {
        int method = Imgproc.CHAIN_APPROX_SIMPLE;
        int mode = Imgproc.RETR_LIST;
        Mat img = image.clone();
        int contourIdx = -1; // draw all contours
        Mat hierarchy = new Mat();

        /*
         * The canny filter finds the outlines.
         */
        Mat imgCanny = ImageCanny(image);
        /*
         * Finding contours amounts to finding "outside" edges. So of the set of
         * edges found by the canny filter, further reduce them
         */
        Imgproc.findContours(imgCanny, edgeContours, hierarchy, mode, method);
        Scalar color = new Scalar(255, 255, 0);
        Imgproc.drawContours(img, edgeContours, contourIdx, color);
        return img;
    }
}