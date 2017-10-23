import org.opencv.core._
import org.opencv.core.CvType._
import clojure.lang.RT.loadLibrary

object SimpleOpenCV {
 	  loadLibrary(Core.NATIVE_LIBRARY_NAME)

    def main(args: Array[String]) {
    	val mat = Mat.eye(3, 3, CV_8UC1)
      println("mat = " + mat.dump())
    }

}