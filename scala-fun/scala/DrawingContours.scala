import org.opencv.core._
import org.opencv.core.Core
import org.opencv.imgproc.Imgproc._
import org.opencv.imgcodecs.Imgcodecs._
import clojure.lang.RT
import java.util


object DrawingContours {
  def main(args: Array[String]) {
    val image = imread("images/lena.png")
//    val image = imread("images/cat.jpg")
    val contours = findAndDrawContours(image)
    imwrite("output/contours.png", contours)
  }

  def findAndDrawContours(image: Mat): Mat = {
    val imageHSV = new Mat
    val imageBlurr = new Mat
    val imageA = new Mat

    resize(image,image,new Size(image.width()/2,image.height()/2))
    cvtColor(image, imageHSV, COLOR_BGR2GRAY)
    GaussianBlur(imageHSV, imageBlurr, new Size(5, 5), 0)
    adaptiveThreshold(imageBlurr, imageA, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 7, 5)
    val contours = new util.ArrayList[MatOfPoint]
    findContours(imageA, contours, new Mat, RETR_LIST, CHAIN_APPROX_SIMPLE)
    val contourImg = image.clone
    println(contours.size)
    for(i <- 0 to contours.size-1) {
      drawContours(contourImg, contours, i, new Scalar(255, 255, 255), -1)
    }
    contourImg
  }

  RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)

}