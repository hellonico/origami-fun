(ns opencv4.video.cartoon2
  (:import [origami.filters Vintage])
 (:require
  [opencv4.core :refer :all]
  [opencv4.video :as v]
  [opencv4.cartoon]
  [opencv4.utils :as u]))

(def vintage (Vintage.))
(defn -main [& args]
 (u/simple-cam-window
   (fn[buffer]
    (-> buffer vintage)
      )))

;(-main)