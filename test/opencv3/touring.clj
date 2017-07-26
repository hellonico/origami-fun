(ns opencv3.touring
  (:require
    [opencv3.utils :as u]
    [opencv3.core :refer :all]))

(def img
  (-> "resources/morph/hammer2.png"
    imread
    pyr-down!
    ))

(def ret (-> img
  clone
  (cvt-color! COLOR_BGR2GRAY)
  (threshold! 127 255 THRESH_BINARY)
  ))

(u/show ret)

(def contours (new-arraylist))
(find-contours ret contours (new-mat) RETR_EXTERNAL CHAIN_APPROX_SIMPLE)

(doseq [c contours]
  (let [  b (bounding-rect c)]
  (rectangle img (.tl b) (.br b) (new-scalar 0 255 0) 2)))

(u/show img)
