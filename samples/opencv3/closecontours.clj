(ns opencv3.closecontours
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u])
  (:gen-class)
    )

(defn annotate![image text color]
  (let[ cl (clone image)]
  (put-text cl text (new-point 50 100) FONT_HERSHEY_PLAIN 4 color 1)
  cl))

; convert to gray, remove noise with blur, find threshold and then use canny
(defn close-contours![img]
(let [ work
    (-> img
      clone
      (cvt-color! COLOR_BGR2GRAY)
      (blur! (new-size 3 3) )
      (bitwise-not!)
      (threshold! 80 255 THRESH_BINARY)
      (canny! 300.0 100.0 3 true))
    contours (new-arraylist)
    drawing (new-mat (.rows img) (.cols img)  CV_8UC3 (new-scalar 255 255 255))
    output (new-mat)]

  ; find and draw contours on a new mat
  ; note that this is redrawing from the found points
  ; while canny just highlight the object
  (find-contours work contours (new-mat) RETR_EXTERNAL CHAIN_APPROX_SIMPLE)
  (dotimes [i (.size contours)]
   (draw-contours drawing contours i (new-scalar 0 0 0) 3))
  (cvt-color! work COLOR_GRAY2RGB)
  (vconcat [
    (annotate! img "original" (new-scalar 255 255 255))
    (annotate! work "preprocessing" (new-scalar 255 255 255))
    (annotate! drawing "contours" (new-scalar 0 0 0))] output )
  output))

(defn -main[ & args]
  (-> (first args) imread close-contours! (imwrite (second args)))
  )


(comment

  (->
    "resources/morph/pen.png"
    imread
    close-contours!
    (imwrite "output/pen.png"))

  (->
    "resources/morph/tape.png"
    imread
    close-contours!
    (imwrite "output/tape.png"))

  )
