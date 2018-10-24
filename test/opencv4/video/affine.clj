(ns opencv4.video.affine
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.utils :as u]))


(def src
  (u/matrix-to-matofpoint2f [[0 0] [3 1] [5 6]]))
(def dst
  (u/matrix-to-matofpoint2f [[0 0] [5 1] [5 6]]))
(def transform-mat (get-affine-transform dst src))
(defn affine-stream [ buffer ]
  (warp-affine! buffer transform-mat (.size buffer)))

(u/simple-cam-window
  {:frame {:width 500 :height 300 :title "Affine"}}
  affine-stream)
