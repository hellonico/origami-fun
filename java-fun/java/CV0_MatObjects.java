import org.opencv.core.Core;
import org.opencv.core.Mat;

import static java.lang.System.loadLibrary;
import static java.lang.System.out;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;
import org.opencv.core.CvType;

public class CV0_MatObjects {
  
	static {
		loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		    Mat mat = Mat.eye(3, 3, CV_8UC1);
        out.println("mat = ");
        out.println(mat.dump());

        Mat mat2 = Mat.zeros(3,3,CV_8UC1);
        out.println("mat2 = ");
        out.println(mat2.dump());

        Mat mat3 = Mat.ones(3,3,CV_8UC1);
        out.println("mat3 = " );
        out.println(mat3.dump());

        Mat mat4 = Mat.ones(1,1,CV_8UC3);
        out.println("mat4 = " );
        out.println(mat4.dump());

        Mat mat5 = Mat.ones(3,3,CvType.CV_64FC3);
        out.println("mat5 = " );
        out.println(mat5.dump());

	}
}
