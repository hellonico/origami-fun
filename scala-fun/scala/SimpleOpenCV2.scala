import org.opencv.core._
import org.opencv.core.CvType._
import org.opencv.imgproc.Imgproc._
import org.opencv.imgcodecs.Imgcodecs._
import clojure.lang.RT.loadLibrary

 object SimpleOpenCV2 {

 	loadLibrary(Core.NATIVE_LIBRARY_NAME)

    def main(args: Array[String]) {
    	val lena = imread("images/lena.png")
        imwrite("target/blurred100.png", blur_(lena,100))
    }

    def blur_(input: Mat, numberOfTimes:Integer) : Mat = {
    	var a = 0
        for( a <- 1 to numberOfTimes ) {
        	 blur(input, input, new Size(9.0, 9.0))
		}
        return input
    }
    
  }
