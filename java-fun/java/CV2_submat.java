import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;
import static java.lang.System.loadLibrary;
import static org.opencv.core.CvType.CV_8UC3;
import org.opencv.imgproc.Imgproc;

public class CV2_submat {
	static {
		loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}


  	public static void main(String[] args) {
      // sample1();
      sample2();
  	}

  static void sample1() {
    Mat mat = new Mat(200,300,CV_8UC3);
    setColors(mat, false, 1);
    setColors(mat, true, 0);
    Imgcodecs.imwrite("output/mat2.jpg", mat);
  }

  static Scalar RED     = new Scalar(0, 0, 255);
  static Scalar GREEN   = new Scalar(0, 255, 0);
  static Scalar BLUE    = new Scalar(255, 0, 0);
  static Scalar CYAN    = new Scalar(255, 255, 0);
  static Scalar MAGENTA = new Scalar(255, 0, 255);
  static Scalar YELLOW  = new Scalar(0, 255, 255);

  static void setColors(Mat mat, boolean comp, int row) {
      for(int i = 0 ; i < 3 ; i ++) {
        Mat sub = mat.submat(row*100, row*100+100, i*100, i*100+100);
        if(comp) {  // RGB
          if(i==0) sub.setTo(RED);
          if(i==1) sub.setTo(GREEN);
          if(i==2) sub.setTo(BLUE);
        } else { // CMY
          if(i==0) sub.setTo(CYAN);
          if(i==1) sub.setTo(MAGENTA);
          if(i==2) sub.setTo(YELLOW);
        }
      }
  }

  static void sample2() {
      int width = 200,height = 200;
      Mat mat = new Mat(height,width,CV_8UC3);
      Mat top = mat.submat(0,height/2,0,width);
      Mat bottom = mat.submat(height/2,height,0,width);

      Mat small = Imgcodecs.imread("images/kitten2.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_8);
      Imgproc.resize(small,small,top.size());

      small.copyTo(top);
      small.copyTo(bottom);

      Imgcodecs.imwrite("output/matofpictures.jpg", mat);
  }

}
