import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.core.Core.*
import org.opencv.imgcodecs.Imgcodecs.*
import org.opencv.core.CvType.*
import clojure.lang.RT

object KotlinBlur {
    init {
        RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    @JvmStatic fun main(args: Array<String>) {
        println("hello opencv")

        val mat = Mat.eye(10, 10, CV_8UC1)
        for (i in 0..9) {
            mat.put(i, i, *doubleArrayOf(255.0))
        }

        imwrite("output/mat1.png", mat)

        val mat2 = Mat.eye(10, 10, CV_8UC1)
        for (i in 0..9) {
            mat2.put(i, 10 - i - 1, *doubleArrayOf(255.0))
        }
        imwrite("output/mat2.png", mat2)

        val output = Mat()
        add(mat, mat2, output)
        imwrite("output/add.png", output)

        val output2 = Mat()
        blur(mat, output2, Size(1.0, 1.0))
        imwrite("output/blur1.png", output2)

        blur(mat, output2, Size(3.0, 3.0))
        imwrite("output/blur3.png", output2)

        blur(mat, output2, Size(5.0, 5.0))
        imwrite("output/blur5.png", output2)

        blur(mat, output2, Size(7.0, 7.0))
        imwrite("output/blur70.png", output2)

    }
}