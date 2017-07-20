(ns opencv3.average-color
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

; find average color of picture
(defn show-average[img]
  (let[
  target (new-mat)
  source
    (resize! (imread img) (new-size 300 300))
  avg-mat
    (new-mat 300 300 CV_8UC3 (mean source))]
  (vconcat [source avg-mat] target)
  (u/show target)  ))

(show-average "resources/images/cat.jpg")
