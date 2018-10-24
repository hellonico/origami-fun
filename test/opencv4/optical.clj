(ns opencv4.optical
  (:import
    [org.opencv.core CvType Core Mat]
    [org.opencv.imgproc Imgproc])
  (:require [opencv4.core :refer :all]))

; https://stackoverflow.com/questions/32145348/why-doesnt-opencv-findcontour-method-always-find-closed-outer-contour?rq=1
(-> "resources/images/cat.jpg"
  (imread)
  (cvt-color! COLOR_RGB2GRAY)
  (adaptive-threshold! 255 ADAPTIVE_THRESH_GAUSSIAN_C THRESH_BINARY_INV 105 1)
  (dilate! (get-structuring-element MORPH_ERODE (new-size 3 3) ))
  (imwrite "output/newcat.png"))

; readable japanese
; https://stackoverflow.com/questions/31289895/threshold-image-using-opencv-java
(->
  "resources/morph/japanese-text.jpg"
  (imread)
  (cvt-color! COLOR_RGB2GRAY)
  (adaptive-threshold! 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 15 40)
  (imwrite "output/newjp.png"))
