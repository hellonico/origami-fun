(ns opencv3.floodfill
  (:require
    [opencv3.utils :as u]
    [opencv3.colors.rgb :as rgb]
    [opencv3.core :refer :all]))

; http://docs.opencv.org/trunk/d5/d26/ffilldemo_8cpp-example.html#a11
(def rose
  (-> "resources/matching/rose_flower.jpg" imread (u/resize-by 0.5) (cvt-color! COLOR_RGB2GRAY)))

(def flooded (new-mat))
(def flood (new-point 1 1))
(def low-diff (new-scalar 10 10 10))
(def high-diff (new-scalar 10 15 15))

(flood-fill
  rose
  flooded
  flood
  rgb/coral-1
  (new-rect 20 20 20 20)
  low-diff
  high-diff 8)

(u/show rose)
