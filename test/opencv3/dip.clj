(ns opencv3.dip
   (:require
     [opencvfun.utils :as u]
     [opencv3.core :refer :all]))

; ENHANCE CONTRAST

(->
 (imread "resources/images/cat.jpg" CV_8U)
 (equalize-hist!)
 (imwrite "output/cat.png"))

; ENHANCE BRIGHTNESS
(->
  (imread "resources/images/cat.jpg")
  ; rtype (-1 to match src) alpha (multiply by) beta (then add)
  (convert-to! -1 2 0)
  (imwrite "output/cat.png"))

; ENHANCE SHARPNESS
(def neko (imread "resources/images/cat.jpg"))
(def dest (u/mat-from neko))
(gaussian-blur neko dest (new-size 0 0) 10)
; this adds two mats, each with a weighted value,
; gamma(here 0) is then added
(add-weighted neko 1.5 dest -0.5 0 dest)
(imwrite dest "output/cat.png")
