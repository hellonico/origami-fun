(ns opencv4.matchingshapes
 (:require
   [opencv4.utils :as u]
   [opencv4.core :refer :all]))

(def t1 (->
  "resources/white-blue-triangle.png"
  (imread 0)
  (threshold! 210 240 1)))

(def t2
  (->
   "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Sweden_road_sign_A20.svg/1000px-Sweden_road_sign_A20.svg.png"
   u/mat-from-url
   (cvt-color! COLOR_RGB2GRAY)
   (threshold! 210 240 1)))

(match-shapes (.t t1) (.t t2) CV_CONTOURS_MATCH_I1 0)
