(ns opencv3.average-color
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

;
; find average color of a picture using opencv mean function
;

(defn show-average[img]
  (let[
  target (new-mat)
  source
    (-> img imread (u/resize-by 0.25))
  avg-mat
    (new-mat (.rows source)  (.cols source)  CV_8UC3 (mean source))]
  (vconcat [source avg-mat] target)
  (u/show target {:frame {:title "cat" :width 500 :height 700}})  ))

(show-average "resources/images/cat.jpg")
