(ns opencv3.colormatching
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

; read the original picture
(def rose
  (imread "resources/matching/rose_flower.jpg"))

; convert the color space to HSV
(def hsv
  (-> rose clone (cvt-color! COLOR_BGR2HSV)))

; filter on red and create a mask from that filtering
(def lower-red  (new-scalar 0 70 70))
(def upper-red (new-scalar 10 255 255))
(def mask (new-mat))
(in-range hsv lower-red upper-red mask)

; pick up pixels from the original pictures that are selected by the mask
(def res (new-mat))
(bitwise-and! rose res mask)

; change the color by using convert-to and
; apply  original*alpha  + beta on each pixel
(def res2 (new-mat))
(convert-to res res2 -1 1 100)

; copy the updated picture (which is the masked portion)
; to the original (a clone of)
(def cl (clone rose))
(copy-to res2 cl mask)

; copy something completely different
(def cl2 (imread "resources/inside-a-hot-air-balloon.jpg"))
(def cl3 (clone rose))
(resize! cl2 (new-size (.cols mask) (.rows mask)))
(copy-to cl2 cl3 mask)

; concat all the steps

(imwrite
  (vconcat! [rose (-> mask clone (cvt-color! COLOR_GRAY2RGB)) res res2 cl cl3])
  "output/colormatching.png")
