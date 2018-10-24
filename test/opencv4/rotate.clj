(ns opencv4.rotasquare
  (:require
    [opencv4.core :refer :all]))

(->
  "resources/images/cat.jpg"
  imread
  clone
  (rotate! ROTATE_90_CLOCKWISE)
 (imwrite "output/rotated.png"))
