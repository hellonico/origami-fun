(ns opencv4.video.warping
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.utils :as u]))

(def points1
  [[0 0] [200 50] [30 300] [500 300]])

(def points2
  [[0 0] [200 0] [30 300] [500 300]]) ; no transformation

(def points2
  ; [[0 0] [200 0] [30 300] [500 300]])
  [[70 10] [200 52] [28 200] [389 390]])

(defn warp [ buffer ]
  (-> buffer
    (warp-perspective!
      (get-perspective-transform
        (u/matrix-to-matofpoint2f points1)
        (u/matrix-to-matofpoint2f points2))
        (size buffer ))))

(defn to-gray[ buffer ]
  (-> buffer (cvt-color! COLOR_RGB2GRAY) (cvt-color! COLOR_GRAY2RGB)))

(u/simple-cam-window
  {:video {:device 0} :frame {:width 650  :height 400 :title "Gray Cello"}}
  (comp to-gray warp))
