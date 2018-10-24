(ns opencv4.video.short-stream5
  (:require
    [opencv4.core :refer :all]
    [opencv4.video :as v]
    [opencv4.utils :as u]))

(defn -main[ & args]
  (u/simple-cam-window (fn [buffer]
    (->
    buffer
    clone
    (cvt-color! COLOR_BGR2GRAY)
    (bilateral-filter! 9 9 7)
    ; (gaussian-blur! (new-size 1 1) 1 1)
    (canny! 50.0 250.0 3 true)
    ; (bitwise-not!)
    )))

    )

(comment

  (-main)
  )
