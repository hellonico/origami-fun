(ns opencv3.customfilter
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

; http://answers.opencv.org/question/30328/how-to-implement-filters/

(def img (-> "resources/nico.jpg" imread (u/resize-by 0.5)))

(def blue-sepia (u/matrix-to-mat [
  [0.393 0.769 0.189]
  [0.349 0.686 0.168]
  [0.272 0.534 0.131]
  ]))
(-> img
  clone
  (transform!  blue-sepia)
  (u/show  {:frame {:title "blue sepia"}}))

(def sepia-2 (u/matrix-to-mat [
  [0.131 0.534 0.272]
  [0.168 0.686 0.349]
  [0.189 0.769 0.393]
  ]))

(-> img
  clone
  (transform! sepia-2)
  (u/show  {:frame {:title "sepia"}}))

; my own sepia/red filter
(-> img
  clone
  (transform! (u/matrix-to-mat [
    [0.131 0.534 0.272]
    [0.168 0.386 0.349]
    [0.189 0.769 0.393]
    ]))
  (imwrite "output/filters.png"))
