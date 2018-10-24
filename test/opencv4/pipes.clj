(ns opencv4.pipes
  (:require [opencv4.core :refer :all])
  (:require [opencv4.utils :as u])
  (:import
    [org.opencv.core Core]
    [org.opencv.imgproc Imgproc]))

; pipe operations on image
(def kernel
  (get-structuring-element MORPH_RECT (new-size 5 5)))
(->
  (imread "resources/images/cat.jpg")
  (blur! (new-size 20 20))
  (flip! -1)
  (erode! kernel)
  (dilate! kernel)
  (imwrite "output/eroded-cat.jpg"))

; turning to gray
(->
  (imread "resources/images/cat.jpg")
  (cvt-color! COLOR_BGRA2GRAY)
  (normalize! 0 255 NORM_MINMAX)
  (imwrite "output/cat1.png"))

; use clone in the pipeline
(def neko (imread "resources/images/cat.jpg"))
(-> neko
 (clone)
 (blur! (new-size 20 20))
 (flip! -1)
 (erode! kernel)
 (imwrite "output/cloned-cat.jpg"))

; shape detection again
(def bgr-image
  (imread "resources/detect/circles.jpg"))

(def ogr-image
  (-> bgr-image
   (clone)
   (median-blur! 3)
   (cvt-color! COLOR_BGR2HSV)
   (in-range! (new-scalar 0 100 100) (new-scalar 10 255 255))
   (gaussian-blur! (new-size 9 9) 2 2)))

(def circles (new-mat))
(hough-circles ogr-image circles CV_HOUGH_GRADIENT 1 (/ (.rows bgr-image) 8) 100 20 0 0)
(dotimes [i (.cols circles)]
  (let [ circle (.get circles 0 i) x (nth circle 0) y (nth circle 1) r (nth circle 2)  p (new-point x y)]
  (opencv4.core/circle bgr-image p (int r) (new-scalar 0 255 0) 5)))
(imwrite bgr-image "output/test.png")
