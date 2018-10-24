(ns opencv4.leftright
 (:require
   [opencv4.utils :as u]
   [opencv4.core :refer :all]))


(def left (imread "resources/images/blurred.png"))

(def output (new-mat (.rows left) (* 2 (.cols left))  CV_8UC3))
(.setTo output (new-scalar 255 255 255))

(def ol
  (.submat output (new-rect 0 0 (.cols left) (.rows left))))
(.copyTo left ol)

(def right (imread "resources/images/kidrunning.jpeg"))
(def or
  (.submat output (new-rect (.cols left) 0 (.cols left) (.rows left))))
(resize! right (.size or))
(.copyTo right or)

(u/show output {:frame {:width 1024 :height 550}})

;
; using a function
;

(defn render-two [ left right]
  (let [ output (new-mat (rows left) (* 2 (cols left))  CV_8UC3)
  ol (submat output (new-rect 0 0 (cols left) (rows left)))
  or (submat output (new-rect (cols left) 0 (cols left) (rows left)))
  ]
  (set-to output (new-scalar 255 255 255))
  (copy-to left ol)
  (resize! right (.size or))
  (copy-to right or)
  output))

(u/show (render-two
  (imread "resources/images/blurred.png")
  (imread "resources/images/kidrunning.jpeg"))
  {:frame {:width 1024 :height 550}})
