(ns opencv3.histo
  (:require
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as rgb]
    [opencv3.core :refer :all]))

; http://answers.opencv.org/question/8967/java-api-histogram-calculation/
; https://stackoverflow.com/questions/22464503/how-to-use-opencv-to-calculate-hsv-histogram-in-java-platform

(def image (imread "resources/images/lena.png"))
(def gray (new-mat (.height image) (.width image) CV_8UC2))
(cvt-color image gray COLOR_RGB2GRAY)

(def planes (new-arraylist))
(split gray planes)

(def hist-size (new-matofint (int-array [256])))
; (def hist-size (new-matofint (int-array [256 256 256])))
(def hist-range (new-matoffloat (float-array [0.0 255.0])))
; (def hist-range (new-matoffloat (float-array [0.0 255.0 0.0 255.0 0.0 255.0])))
(def b-hist (new-mat))

(calc-hist planes (new-matofint (int-array 0)) (new-mat) b-hist hist-size hist-range false)
; (.dump b-hist)


(def hist-w 300)
(def hist-h 600)
(def bin-w (Math/round (double (/ 256 hist-w))))
(def histImage (new-mat hist-h hist-w CV_8UC3))
(normalize! b-hist 3 (.rows histImage) NORM_MINMAX)
(set-to histImage (new-scalar 0 0 0))

(doseq [ i (range 1 256)]
 (line histImage
   (new-point (* bin-w (dec i))
    (- hist_h (Math/round (nth (.get b-hist (dec i) 0) 0))))
   (new-point (* bin-w i)
    (- hist_h (Math/round (nth (.get b-hist i 0) 0))))
rgb/yellow-2
 2
 8
 0))
 (u/show histImage)
