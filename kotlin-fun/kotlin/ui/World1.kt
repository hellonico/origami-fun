package ui;

import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.imgcodecs.Imgcodecs.*
import clojure.lang.RT
import tornadofx.*
import javafx.application.Application
import javafx.scene.layout.*
import javafx.scene.paint.Color


class HelloWorld1 : View() {
    override val root = VBox()
    val controller: MyController by inject()

    init {
        with(root) {
            button("Press Me") {
                textFill = Color.RED
                action {
                  println("Button was pressed!")
                  controller.writeToDb(java.util.Date().toString())
                }
            }
        }
    }

    class MyController: Controller() {
        fun writeToDb(inputValue: String) {
            println("Writing $inputValue to database!")
        }
    }
}


class MyApp1: App(HelloWorld4::class)

object World1 {
    @JvmStatic fun main(args: Array<String>) {
      RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      Application.launch(MyApp1::class.java, *args)
    }

}
