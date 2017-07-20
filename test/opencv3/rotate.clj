(ns opencv3.rotasquare
  (:require
    [opencv3.core :refer :all]))

(->
  "resources/images/cat.jpg"
  imread
  clone
  (rotate! ROTATE_90_CLOCKWISE)
 (imwrite "output/rotated.png"))
