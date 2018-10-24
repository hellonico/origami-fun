(ns opencv4.floodfill
  (:require
    [opencv4.utils :as u]
    [opencv4.colors.rgb :as rgb]
    [opencv4.core :refer :all]))

; http://docs.opencv.org/trunk/d5/d26/ffilldemo_8cpp-example.html#a11
(def rose
  (-> "resources/matching/rose_flower.jpg" imread (u/resize-by 0.5) ))

(def mask (new-mat))
(def flood (new-point 1 1))

(def low-diff (new-scalar 100 10 10))
(def high-diff (new-scalar 100 15 15))

; (threshold rose mask 1 128 THRESH_BINARY)
(def copy (clone rose))
(flood-fill
  copy
  mask
  flood
  rgb/coral-1
  (new-rect 120 120 220 220)
  low-diff
  high-diff
  0)

(u/show copy)
