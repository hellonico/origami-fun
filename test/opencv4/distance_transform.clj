(ns opencv4.distance-transform
  (:require
    [opencv4.utils :as u]
    [opencv4.core :refer :all]))

(->
  "resources/images/cat2.png"
  (imread CV_8UC1)
  (distance-transform! CV_DIST_L2 3)
  (convert-scale-abs!)
  (normalize! 0.0 255.0 NORM_MINMAX)
  (imwrite "output/cat3.png"))
