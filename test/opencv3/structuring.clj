(ns opencv3.structuring
  (:import
    [org.opencv.core CvType Core Mat]
    [org.opencv.imgproc Imgproc])
  (:require [opencv3.core :refer :all]))


; A structuring element is required to be passed to
; - createMorphologyFilter,
; - erode,
; - dilate or
; - morphologyEx()

; http://opencvexamples.blogspot.com/2013/10/create-structuring-element-for.html
(def kernel
  (get-structuring-element Imgproc/MORPH_ELLIPSE (new-size 3 3)))
