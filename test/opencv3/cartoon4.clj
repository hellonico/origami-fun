(ns opencv3.cartoon4
 (:require
   [opencv3.utils :as u]
   [opencv3.core :refer :all]))

(def neko
  (->
  "resources/images/cat.jpg"
  (imread)
  (u/resize-by 0.3)
  (cvt-color! COLOR_BGR2GRAY)
  (gaussian-blur! (new-size 1 1) 1 1)
  ; (median-blur! 1)
  (canny! 100.0 220.0 3 true)
  (bitwise-not!)
  (u/show)
  ))

(u/show neko)
