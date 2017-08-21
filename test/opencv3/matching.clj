(ns opencv3.matching
 (:require
   [opencv3.utils :as u]
   [opencv3.colors.rgb :as color]
   [opencv3.core :refer :all]))

;
; Template matching in opencv does a pixel for pixel comparison
; so usually it is not very strong to image deformation.
; Still it is a technique worth to know to find template in an image
;

; load the image and the template
(def image (imread "resources/images/matching/lena.png"))
(def template (imread "resources/images/matching/lips.png"))

; find the match
(def result (new-mat))
(match-template image template result TM_CCOEFF)
(normalize! result 0 1 NORM_MINMAX -1 (new-mat))

; find the best match
(def mmr (min-max-loc result))
(def match-loc (.maxLoc mmr))

; draw the match
(rectangle
  image
  match-loc
  (new-point (+ (.x match-loc) (.cols template)) (+ (.y match-loc) (.rows template)))
  color/teal)

; show the image
(u/show
  image
  {:frame {:width 600 :height 600}}
  )
