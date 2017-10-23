import clojure.lang.RT.loadLibrary
import org.opencv.core.{Core, Mat, Size}
import org.opencv.core.CvType._
import org.opencv.imgproc.Imgproc._

object SimpleOpenCV1 {
 	  loadLibrary(Core.NATIVE_LIBRARY_NAME)

    def main(args: Array[String]) {
    	val mat = Mat.ones(new Size(10,10), CV_8UC1)
      println("original = " + mat.dump())
      blur(mat,mat,new Size(3,3))
      println("blurred = " + mat.dump())
    }

}