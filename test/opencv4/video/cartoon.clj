(ns opencv4.video.cartoon
 (:require
  [opencv4.core :refer :all]
  [opencv4.video :as v]
  [opencv4.cartoon]
  [opencv4.utils :as u]))

(defn -main [& args]
 (u/simple-cam-window
   (fn[buffer]
    (-> buffer
      (opencv4.cartoon/cartoon-2)))))
