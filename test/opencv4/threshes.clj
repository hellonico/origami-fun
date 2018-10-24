(ns opencv4.threshes
  (:require [opencv4.core :refer :all]))

; https://stackoverflow.com/questions/11424002/how-to-detect-simple-geometric-shapes-using-opencv
; https://stackoverflow.com/questions/11273588/how-to-convert-matofpoint-to-matofpoint2f-in-opencv-java-api
; (def target-image  "resources/morph/shapes3.jpg")

(def target-image
  "resources/morph/colourful-shapes.jpg")

(def gray
  (imread target-image 0))
(imwrite gray "output/please.png")

; the first value finds the shape depending on their hsv color i guess ?
; the higher the threshold value the more shapes you find
(def thresh (new-mat))
(threshold gray thresh 210 250 1)
(imwrite thresh "output/please.png")

(def levels
 (into []
   (map
    #(-> gray
      clone
      (threshold! % 250 1)
      (copy-make-border! 10 10 10 10 BORDER_CONSTANT (new-scalar 255 255 0)))
     [50 100 150 200 210])))

(def target (new-mat))
(vconcat levels target)
(imwrite target "output/please.png")
