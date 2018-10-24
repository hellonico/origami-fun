(ns opencv4.bgdiff
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]))

; https://stackoverflow.com/questions/27035672/cv-extract-differences-between-two-images
(def bg (imread "resources/images/bgdiff/header.png"))
(def fg (imread "resources/images/bgdiff/front.png"))

(def output (new-mat))
(absdiff bg fg output)

; diff in color
(def fg-1
  (-> output
  clone
  (threshold! 10 255 1)
  (imwrite "output/bgdiff.png")))

; diff in gray
(def fg-2
 (-> output
  clone
  (cvt-color! COLOR_RGB2GRAY)
  (threshold! 10 255 1)
  (imwrite "output/bgdiff.png")))

(imwrite
  (vconcat! [bg fg output fg-1 (cvt-color! fg-2 COLOR_GRAY2RGB)])
  "output/bgdiff.png")
