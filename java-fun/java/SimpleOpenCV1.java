import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.core.CvType;

import org.opencv.imgcodecs.Imgcodecs;

import static java.lang.System.loadLibrary;
import static java.lang.System.out;

public class SimpleOpenCV1 {
	static {
		loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
        Mat mat = Imgcodecs.imread("images/kittens.jpg");
        out.println("mat =" + mat.width() + " x " + mat.height() + " " + mat.type() );
        
        // Imgcodecs.imwrite("output/subcat.jpg", mat4);
        /*
        Mat submat = mat4.submat(250,650,600,1000);
        Imgcodecs.imwrite("output/subcat.jpg", submat);
        Imgcodecs.imwrite("output/subcat.png", submat);
        Imgcodecs.imwrite("output/subcat.tiff", submat);
        */
	}
}
