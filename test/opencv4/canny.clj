(ns opencv4.canny
 (:require [opencv4.core :refer :all]))

; canny and reverse
(->
 "resources/images/cat.jpg"
 (imread)
 (cvt-color! COLOR_RGB2GRAY)
 (canny! 300.0 100.0 3 true)
 (bitwise-not!)
 (imwrite "output/canny-cat.jpg"))
