(ns opencv3.video.cartoon
 (:require
  [opencv3.core :refer :all]
  [opencv3.video :as v]
  [opencv3.cartoon]
  [opencv3.utils :as u]))

(defn -main [& args]
 (u/simple-cam-window
   (fn[buffer]
    (-> buffer
      (opencv3.cartoon/cartoon-2)))))
