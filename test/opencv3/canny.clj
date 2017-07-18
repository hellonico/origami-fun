(ns opencv3.canny
 (:require [opencv3.core :refer :all]))

; canny and reverse
(->
 (imread "resources/images/cat.jpg")
 (cvt-color! COLOR_RGB2GRAY)
 (canny! 300.0 100.0 3 true)
 (bitwise-not!)
 (imwrite "output/canny-cat.jpg"))
