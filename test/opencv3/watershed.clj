(ns opencv3.watershed
  (:import
    [org.opencv.core CvType Core Mat]
    [org.opencv.imgproc Imgproc])
  (:require [opencv3.core :refer :all]))

; http://docs.opencv.org/3.1.0/d3/db4/tutorial_py_watershed.html
(->
  (imread "resources/morph/water_coins.jpg")
  (cvt-color! COLOR_BGR2GRAY)
  (threshold! 0 255 (+ THRESH_BINARY THRESH_OTSU))
  (imwrite "output/coins3.jpg"))
