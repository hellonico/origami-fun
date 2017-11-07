package ui;

import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.imgcodecs.Imgcodecs.*
import clojure.lang.RT
import tornadofx.*
import javafx.application.Application
import javafx.scene.layout.*
import javafx.scene.paint.Color

class HelloWorld3 : View() {
  override val root = VBox()
  val bottom = HBox()
  val kittens = arrayListOf("http://i.imgur.com/DuFZ6PQb.jpg", "http://i.imgur.com/o2QoeNnb.jpg").observable()

  init {
        with(bottom) {
          button("Clear")
            { action {kittens.clear()}}
          button("Add one")
            { action {kittens.add("cat.jpg")}}
          button("Print")
            { action {println(kittens)}}
        }

        root += bottom
        root += datagrid(kittens) {
              cellCache {
                   imageview(it) {
                     fitHeight = 160.0
                     fitWidth = 160.0
                   }
              }
            }
    }
}


class MyApp3: App(HelloWorld4::class)

object World3 {
    @JvmStatic fun main(args: Array<String>) {
    RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      Application.launch(MyApp3::class.java, *args)
    }

}
