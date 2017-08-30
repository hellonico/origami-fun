(ns opencv3.video.short-stream5
  (:require
    [opencv3.core :refer :all]
    [opencv3.video :as v]
    [opencv3.utils :as u]))

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
