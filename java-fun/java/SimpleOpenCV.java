import org.opencv.core.*;
import static org.opencv.core.CvType.*;
import static java.lang.System.*;
import clojure.lang.RT;

public class SimpleOpenCV {
	static {
		RT.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat mat = Mat.eye(3, 3, CV_8UC1);
        out.println("mat = " + mat.dump());

        Mat mat2 = Mat.zeros(3,3,CV_8UC1);
        out.println("mat2 = " + mat2.dump());
	}
}