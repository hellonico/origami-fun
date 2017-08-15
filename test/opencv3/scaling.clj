(ns opencv3.scaling
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u])
  )


; https://stackoverflow.com/questions/45591502/simple-way-to-scale-channels-in-opencv
; I'd like to scale each channel by a different float factor.

; https://stackoverflow.com/questions/10168058/basic-matrix-multiplication-in-opencv-for-android

; this gives the wrong format
; (def mat1
;  (u/matrix-to-mat [ [1.0 0.0 0.0]]))

; filter by color
(->
  "resources/images/cat.jpg"
  imread
  (u/resize-by 0.2)
  (multiply! (u/matrix-to-mat-of-double [ [0.0 1.0 0.0]] ))
  (imwrite "output/scaling.png"))

; increase luminosity
(->
  "resources/images/cat.jpg"
  imread
  (u/resize-by 0.2)
  (cvt-color! COLOR_RGB2HLS)
  (multiply! (u/matrix-to-mat-of-double [ [1.0 1.2 1.2]] ))
  (cvt-color! COLOR_HLS2RGB)
  (imwrite "output/scaling.png"))

; decrease luminosity
(->
  "resources/images/cat.jpg"
  imread
  (u/resize-by 0.2)
  (cvt-color! COLOR_RGB2HLS)
  (multiply! (u/matrix-to-mat-of-double [ [1.0 0.8 0.8]] ))
  (cvt-color! COLOR_HLS2RGB)
  (imwrite "output/scaling.png"))
