import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.imgcodecs.Imgcodecs.*

object ColorMapping {
    @JvmStatic fun main(args: Array<String>) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val mat = imread("resources/kitten.jpg")

        applyColorMap(mat,mat,COLORMAP_WINTER)
        imwrite("output/winter.png", mat)

        applyColorMap(mat,mat,COLORMAP_BONE)
        imwrite("output/bone.png", mat)

        applyColorMap(mat,mat,COLORMAP_HOT)
        val mat2 = mat.clone()
        val newSize = Size((mat.width()/2).toDouble(),(mat.height()/2).toDouble())
        resize(mat2,mat2,newSize)

        imwrite("output/hot.png", mat2)
    }

}
