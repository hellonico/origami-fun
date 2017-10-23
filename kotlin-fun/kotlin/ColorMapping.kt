import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.core.Core.*
import org.opencv.imgcodecs.Imgcodecs.*
import org.opencv.core.CvType.*
import clojure.lang.RT

object ColorMapping {
    init {
        RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @JvmStatic fun main(args: Array<String>) {
        println("changing color")

        val mat = imread("images/kepler.jpg")

        applyColorMap(mat,mat,COLORMAP_WINTER)
        imwrite("output/winter.png", mat)

        applyColorMap(mat,mat,COLORMAP_BONE)
        imwrite("output/bone.png", mat)

        applyColorMap(mat,mat,COLORMAP_HOT)
        val mat2 = mat.clone()
        resize(mat2,mat2,Size(300.0,200.0))
        imwrite("output/hot.png", mat2)

    }

}
