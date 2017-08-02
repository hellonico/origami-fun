(ns opencv3.shapes2
  (:import [org.opencv.core MatOfPoint MatOfPoint2f])
  (:require [opencv3.core :refer :all]))

; https://stackoverflow.com/questions/11424002/how-to-detect-simple-geometric-shapes-using-opencv
; https://stackoverflow.com/questions/11273588/how-to-convert-matofpoint-to-matofpoint2f-in-opencv-java-api
(def target-image  "resources/morph/shapes3.jpg")

; WORK SOME MORE
; (def target-image  "resources/morph/colourful-shapes.jpg")

(def img
  (imread target-image))
(def gray
  (imread target-image 0))

(def thresh (new-mat))
(threshold gray thresh 127 240 1)
(imwrite thresh "output/please.png")

(def contours (new-arraylist))
(find-contours thresh contours (new-mat) RETR_LIST CHAIN_APPROX_SIMPLE)

; draw filled contours
(let [cloned (clone img)]
(dotimes [ci (.size contours)]
 (draw-contours cloned contours ci (new-scalar 255 100 100) FILLED))
 (imwrite cloned "output/please.png"))

; draw around the shapes
(let [cloned (clone img)]
 (dotimes [ci (.size contours)]
 (draw-contours cloned contours ci (new-scalar 255 100 100) 3))
 (imwrite cloned "output/please.png"))

; http://docs.opencv.org/trunk/dd/d49/tutorial_py_contour_features.html
; CONTOUR FEATURES

; draw different color depending on the shape
(let [cloned (clone img)]
(dotimes [i (.size contours)]
 (let[
   c (.get contours i)
   m2f (new-matofpoint2f (.toArray c))
   len (arc-length m2f true)
   ret (new-matofpoint2f)
   approx (approx-poly-dp m2f ret (* 0.01 len) true)
   nb-sides (.size (.toList ret))
   ]
   (draw-contours cloned contours i
    (condp = nb-sides
     3 (new-scalar 255 0 255)
     4 (new-scalar 255 0 0)
     5 (new-scalar 0 0 255)
     9 (new-scalar 255 255 0)
     (new-scalar 0 255 255))
     -1)))
    (imwrite cloned "output/please.png"))
