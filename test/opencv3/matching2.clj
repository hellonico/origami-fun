(ns opencv3.matching2
 (:require
   [opencv3.utils :as u]
   [opencv3.colors.rgb :as color]
   [opencv3.core :refer :all]))

;
; Same as matching.clj but trying to focus on transparency.
;
; https://stackoverflow.com/questions/4761940/opencv-template-matching-and-transparency?rq=1
;
(defn match-my-template [ image template ]
 (let [ result (new-mat) match-loc (atom nil) ]
  (match-template image template result TM_CCOEFF)
  (normalize! result 0 1 NORM_MINMAX -1 (new-mat))

  (reset! match-loc (.maxLoc (min-max-loc result)))

  (rectangle
    image
    @match-loc
    (new-point (+ (.x @match-loc) (.cols template)) (+ (.y @match-loc) (.rows template)))
    color/red-3)

  (u/show
    image
    {:frame {:width 600 :height 600}})))

;
; DOES NOT MATCH THE POO PROPERLY
;
(match-my-template
  (imread "resources/images/matching/pooonlight.png")
  (imread "resources/images/matching/poo.png"))

(match-my-template
  (imread "resources/images/matching/pooondark.png")
  (imread "resources/images/matching/poo.png"))


;
; MATCHES THE NOISY POO.. *but* gives really low matching results
; and probably will fail in any other sample
;
(match-my-template
  (imread "resources/images/matching/pooonlight.png")
  (imread "resources/images/matching/noisypoo.png"))

(match-my-template
  (imread "resources/images/matching/pooondark.png")
  (imread "resources/images/matching/noisypoo.png"))
