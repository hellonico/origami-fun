import clojure.lang.RT.loadLibrary
import org.opencv.core._
import org.opencv.imgcodecs.Imgcodecs._
import org.opencv.imgproc.Imgproc._

object SimpleOpenCV2 {

  loadLibrary(Core.NATIVE_LIBRARY_NAME)

  def main(args: Array[String]) {
    val neko = imread("images/bored-cat.jpg")
    imwrite("output/blurred_cat.png", blur_(neko, 20))
  }

  def blur_(input: Mat, numberOfTimes:Integer) : Mat = {
    for(_ <- 1 to numberOfTimes ) //{
      blur(input, input, new Size(11.0, 11.0))
    // }
    input
  }

}
