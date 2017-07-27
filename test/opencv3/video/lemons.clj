(ns opencv3.video.lemons
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :refer :all]
    [opencv3.colors.rgb :as rgb]
    [opencv3.changesomecolors :refer [low-high!]]
    [opencv3.utils :as u]))

(defn high-low
  ([buffer]
    (high-low 0.5 90 rgb/violetred rgb/wheat rgb/violetred-4 rgb/papayawhip buffer))
  ([resize-factor color-limit c11 c12 c21 c22 buffer]
  (let [ buffer-1 (-> buffer (u/resize-by resize-factor))
         buffer-2 (clone buffer-1)
         output (new-mat)]
  (vconcat [
    (low-high! buffer-1 90 c11 c12)
    (low-high! buffer-2 90 c21 c22)
    ] output)
    output)))

(def high-low-1
  (partial high-low 0.5 90 rgb/gray rgb/blue rgb/orange rgb/green)
  )

(defn -main[& args]
  (u/simple-cam-window high-low-1))

(comment
  (u/simple-cam-window high-low-1)
  ; (u/simple-cam-window identity)

  )
