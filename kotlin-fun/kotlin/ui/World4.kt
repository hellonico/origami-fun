package ui;

import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import org.opencv.imgcodecs.Imgcodecs.*
import clojure.lang.RT
import tornadofx.*
import javafx.application.Application
import javafx.scene.layout.*
import javafx.scene.paint.Color

class HelloWorld4 : View() {
  var original = "cat.jpg"
  val originals = arrayListOf(original).observable()
  val kittens = arrayListOf(original).observable()
  var counter:Int = 0

  override val root = VBox()
  val textField = textfield(""+counter)

  fun updateField() {
    textField.text=""+counter
  }

  fun lastItem():String {
    return originals.last()
  }

  fun reset() {
    counter = 0
    updateField()
    kittens.clear()
    kittens.add(lastItem())
  }

  fun blurme() {
    val input = imread(lastItem().substring(7)) // remove file://
    var loopC:Int = counter*5;
    for (i in 0..loopC) {
      blur(input,input,Size(7.0,7.0))
    }
    val filename = "/tmp/cat_"+System.currentTimeMillis()+".jpg"
    imwrite(filename, input)
    kittens.add("file://"+filename)
  }
  // https://stackoverflow.com/questions/46182788/tornadofx-drag-and-drop-on-a-treeview
  init {
    with(root) {
      setOnDragDetected { event ->
        this.startDragAndDrop(javafx.scene.input.TransferMode.LINK)
        event.consume()
      }
      setOnDragOver { event ->
        event.consume()
      }
      setOnDragEntered { event ->
        val db = event.getDragboard()
        val file = db.getFiles().get(0)
        originals.add("file://"+file.getAbsolutePath())
        reset()
        event.consume()
      }
      setOnDragExited { event ->
        event.consume()
      }

      root += hbox {
        button("+") { action {
          counter=counter+1
          blurme()
          updateField()
          }}
          button("-") { action {
            counter=counter-1
            if(counter < 0)
            counter = 0
            blurme()
            updateField()
            }}
            button("reset") { action {
              reset()
              }}
            }
            root += hbox {

              datagrid(originals) {
                cellCache {
                 imageview(it) {
                   fitHeight = 160.0
                   fitWidth = 160.0
                 }
               }
             }

             datagrid(kittens) {
              cellCache {
               imageview(it) {
                 fitHeight = 160.0
                 fitWidth = 160.0
               }
             }
           }

         }
       }
     }

   }

   class MyApp4: App(HelloWorld4::class)

   object KotlinTest {
    @JvmStatic fun main(args: Array<String>) {
      RT.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      Application.launch(MyApp4::class.java, *args)
    }

  }
