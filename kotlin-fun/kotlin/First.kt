import org.opencv.core.*
import org.opencv.core.CvType.*
import clojure.lang.RT

object First {
    @JvmStatic fun main(args: Array<String>) {
        RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        val mat = Mat.eye(3, 3, CV_8UC1)
        println(mat.dump())
    }
}
