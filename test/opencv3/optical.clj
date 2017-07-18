(ns opencv3.optical
  (:import
    [org.opencv.core CvType Core Mat]
    [org.opencv.imgproc Imgproc])
  (:require [opencv3.core :refer :all]))


; http://docs.opencv.org/3.1.0/d7/d8b/tutorial_py_lucas_kanade.html
; find movement of shapes

; https://stackoverflow.com/questions/32145348/why-doesnt-opencv-findcontour-method-always-find-closed-outer-contour?rq=1
(def img2 (imread "resources/images/cat.jpg"))
(def gray (new-mat))
(cvt-color img2 gray COLOR_RGB2GRAY)
(def bw (new-mat))
(adaptive-threshold gray bw 255 ADAPTIVE_THRESH_GAUSSIAN_C THRESH_BINARY_INV 105 1)
(def kernel
  (get-structuring-element MORPH_ERODE (new-size 3 3)))
(dilate! bw kernel)
(imwrite bw "output/newcat.png")

; readable japanese
; https://stackoverflow.com/questions/31289895/threshold-image-using-opencv-java
(def img2 (imread "resources/morph/japanese-text.jpg"))
(def gray (new-mat))
(cvt-color img2 gray COLOR_RGB2GRAY)
(adaptive-threshold! gray 255 ADAPTIVE_THRESH_MEAN_C THRESH_BINARY 15 40)
(imwrite gray "output/newjp.png")
