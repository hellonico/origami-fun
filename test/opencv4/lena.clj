(ns opencv4.lena
  (:require [opencv4.core :refer :all]))

; open close image, do not do anything
(->
  (imread "resources/images/lena.png")
  (imwrite "output/lenacopy.png"))

; load, blur and save
(->
  (imread "resources/images/lena.png")
  (gaussian-blur! (new-size 9 9) 20 20)
  (imwrite "output/blurred.png"))

; turn to gray
(->
  (imread "resources/images/lena.png")
  (cvt-color! COLOR_BGR2GRAY)
  (normalize! 50 200 NORM_MINMAX)
  (imwrite "output/grey.png"))
