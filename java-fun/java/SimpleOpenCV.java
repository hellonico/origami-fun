import org.opencv.core.Core;
import org.opencv.core.Mat;

import static java.lang.System.loadLibrary;
import static java.lang.System.out;
import static org.opencv.core.CvType.CV_8UC1;

public class SimpleOpenCV {
	static {
		loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat mat = Mat.eye(3, 3, CV_8UC1);
        out.println("mat = " + mat.dump());

        Mat mat2 = Mat.zeros(3,3,CV_8UC1);
        out.println("mat2 = " + mat2.dump());
	}
}