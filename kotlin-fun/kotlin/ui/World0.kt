package ui;

import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.imgcodecs.Imgcodecs.*
import clojure.lang.RT
import tornadofx.*
import javafx.application.Application
import javafx.scene.layout.*
import javafx.scene.paint.Color
/*
class HelloWorld0 : View() {
    override val root = VBox()
    init {
        with(root) {
            imageview("cat.jpg") {
              fitHeight = 160.0
              fitWidth = 200.0
            }
        }
    }
}

class MyApp0: App(HelloWorld0::class)

object World0 {
    @JvmStatic fun main(args: Array<String>) {
      RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      Application.launch(MyApp0::class.java, *args)
    }
}*/


/*import tornadofx.*
import javafx.application.Platform*/

class MyApp : App(MyView::class)

class MyView : View(){
  override val root = Form()

  init{
    with(root){
      title = "TornadoFX demo"
      fieldset{
        field("Hello tornadofx!")
      }

      button("Press me").action{
        println("Button Pressed!")
      }

      button("Close").action{
        Platform.exit()
      }

      button("Open").action{
          openInternalWindow(MyAnotherView::class)
      }
    }
  }
}

class MyAnotherView : View(){
  override val root = Form()

  init{
    with(root){
      title = "another view"
      fieldset("This is another view")
    }
  }
}

object World0 {
    @JvmStatic fun main(args: Array<String>) {
      //RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      Application.launch(MyApp::class.java, *args)
    }
}
