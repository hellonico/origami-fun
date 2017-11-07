package ui;

import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.imgcodecs.Imgcodecs.*
import clojure.lang.RT
import tornadofx.*
import javafx.application.Application
import javafx.scene.layout.*
import javafx.scene.paint.Color

class HelloWorld2 : View() {
  override val root = VBox()

  init {
          root += button("Press The Cat") {
              action { MyDialog().openModal() }
          }
  }

  class MyDialog: Fragment() {
      override val root = VBox()

      init {
          with(root) {
              label("Dialog")
          }
      }
  }
}

class MyApp2: App(HelloWorld4::class)

object World2 {
    @JvmStatic fun main(args: Array<String>) {
    RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      Application.launch(MyApp2::class.java, *args)
    }

}
