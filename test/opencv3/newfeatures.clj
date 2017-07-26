(ns opencv3.newfeatures
  (:require
    [opencv3.core :refer :all]
    [opencv3.utils :as u]))

; www.buildinsider.net/small/opencv/002

; PENCIL SKETCH
(def original
   (-> "resources/images/lena.png" imread))

; (def original
;    (-> "resources/nico.jpg" imread))

(def sketch (-> original clone (pencil-sketch! (new-mat))))
(def edge (-> original clone edge-preserving-filter!))
(def both (-> edge clone (pencil-sketch! (new-mat))))
(def arrange (new-mat))

(vconcat [
  original
  (cvt-color! (clone sketch) COLOR_GRAY2RGB)
  edge
  (cvt-color! (clone both) COLOR_GRAY2RGB)
  ] arrange)
(imwrite arrange "output/sketch.png")
