(ns opencv4.video.lemons
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :refer :all]
    [opencv4.colors.rgb :as rgb]
    [opencv4.changesomecolors :refer [low-high!]]
    [opencv4.utils :as u]))

(defn high-low
  ([resize-factor color-limit c11 c12 c21 c22 buffer]
  (let [ buffer-1 (-> buffer (u/resize-by resize-factor))
         buffer-2 (clone buffer-1)
         output (new-mat)]
  (vconcat [
    (low-high! buffer-1 90 c11 c12)
    (low-high! buffer-2 90 c21 c22)
    ] output)
    output)))

(def high-low-0
  (partial high-low 0.5 90 rgb/violetred rgb/wheat rgb/violetred-4 rgb/papayawhip))
(def high-low-1
  (partial high-low 0.5 90 rgb/gray rgb/blue rgb/orange rgb/green))
(def high-low-2
  (partial high-low 0.75 90 rgb/crimson rgb/skyblue-1 rgb/green rgb/orange))

(defn -main[& args]
  (u/simple-cam-window high-low-1)
  (u/simple-cam-window high-low-2)
  )

(comment
  (u/simple-cam-window high-low-1)
  ; (u/simple-cam-window identity)

  )
