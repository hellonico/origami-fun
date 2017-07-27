(ns opencv3.changesomecolors
(:require
  [opencv3.core :refer :all]
  [opencv3.colors.rgb :as rgb]
  [opencv3.utils :as u]))

;
; change RGB value of pixel based on threshold
; use two thresholds, one for min values, and one for max values
;

; https://stackoverflow.com/questions/45335691/change-rgb-value-of-pixel-based-on-threshold-opencv-c/45338692#45338692

(defn low-high!
  ([image t1 color1 color2 ]
    (low-high! image t1 255 THRESH_BINARY color1 t1 255 THRESH_BINARY_INV color2))
  ([image t1 color1 t2 color2 ]
    (low-high! image t1 255 THRESH_BINARY color1 t2 255 THRESH_BINARY_INV color2))
  ([image a1 a2 a3 color1 b1 b2 b3 color2 ]
  (let [_copy (-> image clone (cvt-color! COLOR_RGB2HSV))
        _work (clone image)
        _thresh-1 (new-mat)
        _thresh-2 (new-mat)]

    (threshold _copy _thresh-1 a1 a2 a3)
    (cvt-color! _thresh-1 COLOR_BGR2GRAY)
    ; (imwrite _thresh-1 "output/thresh21.png" )
    (.setTo _work color1 _thresh-1)

    (threshold _copy _thresh-2 b1 b2 b3)
    (cvt-color! _thresh-2 COLOR_BGR2GRAY)
    ; (imwrite _thresh-2 "output/thresh22.png" )
    (.setTo _work color2 _thresh-2)
    _work)))

(comment
(->
  (imread "resources/matching/rose_flower.jpg")
  (low-high! 150 rgb/crimson 105 rgb/lightblue-1)
  (imwrite "output/thresh2.png"))

(->
  (imread "resources/matching/rose_flower.jpg")
  (low-high! 150 rgb/violetred 105 rgb/greenyellow)
  (imwrite "output/thresh2.png"))

(->
  (imread "resources/nico.jpg")
  (low-high! 100 rgb/violetred 50 rgb/greenyellow)
  (imwrite "output/thresh3.png"))

(u/clean-up-namespace)

)
