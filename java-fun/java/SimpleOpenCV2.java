import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;
import static java.lang.System.loadLibrary;
import static org.opencv.core.CvType.CV_8UC3;

public class SimpleOpenCV2 {
	static {
		loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

  public static void setColor(Mat mat, boolean comp, int row) {

      for(int i = 0 ; i < 3 ; i ++) {
        Mat sub = mat.submat(row*100, row*100+100, i*100, i*100+100);
        if(comp) {
          if(i==0) sub.setTo(new Scalar(0, 0, 255));
          if(i==1) sub.setTo(new Scalar(0, 255, 0));
          if(i==2) sub.setTo(new Scalar(255, 0, 0));
        } else {
          if(i==0) sub.setTo(new Scalar(255, 255, 0));
          if(i==1) sub.setTo(new Scalar(255, 0, 255));
          if(i==2) sub.setTo(new Scalar(0, 255, 255));
        }
      }
  }

	public static void main(String[] args) {
        Mat mat = new Mat(200,300,CV_8UC3);
        setColor(mat, true, 0);
        setColor(mat, false, 1);
        Imgcodecs.imwrite("output/mat2.jpg", mat);
	}
}
